package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import frc.robot.Vision;
import friarLib2.math.LinearRegression;
import friarLib2.utility.PIDParameters;

/**
 * Represents the arm subsystem
 * 
 * <p>The physical subsystem includes a single Falcon 500 and a limit
 * switch that control the arm
 */
public class ArmSubsystem extends SubsystemBase {

    private WPI_TalonFX armMotor;
    private DigitalInput limitSwitch;

    private LinearRegression regression;

    private TrapezoidProfile profile;
    private Timer timer = new Timer();
    private TrapezoidProfile.State targetState;
    private double currentSetDegrees = 0;

    public ArmSubsystem () {
        // Set up motor
        armMotor = new WPI_TalonFX(Constants.Arm.ARM_MOTOR_ID);
        armMotor.configFactoryDefault();
        armMotor.setNeutralMode(NeutralMode.Brake);
        armMotor.setInverted(true);
        armMotor.setSelectedSensorPosition(UnitConversions.Arm.armDegreesToEncoderTicks(Constants.Arm.START_CONFIG_ANGLE));
        PIDParameters.configureMotorPID(armMotor, Constants.Arm.ARM_PID);

        limitSwitch = new DigitalInput(Constants.Arm.LIMIT_SWITCH_PORT);
        zeroArmPosition();

        // Initialize regression with values from Constants.java
        regression = new LinearRegression(Constants.Shooter.AIM_REGRESSION_DATA);

        targetState = new TrapezoidProfile.State();
        profile = new TrapezoidProfile (
            new TrapezoidProfile.Constraints(
                Constants.Arm.MAX_VELOCITY_DEGREES_PER_SEC,
                Constants.Arm.MAX_ACCLERATION_DEGREES_PER_SEC_SQUARED
            ),
            new TrapezoidProfile.State(0, 0),
            targetState
        );

        timer.reset();
        timer.start();
    }

    /**
     * Set the position of the arm
     * 
     * <p>When the aluminum structure of the arm is horizontal to the
     * ground, the arm is at zero degrees.
     * 
     * <p>The degrees increase as the arm moves closer to vertical.
     * 
     * @param degrees
     */
    public void setArmPosition (double degrees) {
        if(degrees == currentSetDegrees){
            return;
        }
        currentSetDegrees = degrees;
        profile = new TrapezoidProfile (
            new TrapezoidProfile.Constraints(
                Constants.Arm.MAX_VELOCITY_DEGREES_PER_SEC,
                Constants.Arm.MAX_ACCLERATION_DEGREES_PER_SEC_SQUARED
            ),
            new TrapezoidProfile.State(degrees, 0), // Goal state
            new TrapezoidProfile.State(getArmPosition(), getArmSpeed())
        );

        timer.reset();
    }

    /**
     * Zero the arm's encoder
     * 
     * <p>Assumes that the arm is at the limit switch
     */
    public void zeroArmPosition () {
        armMotor.setSelectedSensorPosition(UnitConversions.Arm.armDegreesToEncoderTicks(Constants.Arm.LIMIT_SWITCH_ANGLE));
    }

    public double getArmPosition () {
        return UnitConversions.Arm.armEncoderTicksToDegrees(armMotor.getSelectedSensorPosition());
    }

    public double getArmSpeed () {
        return UnitConversions.Arm.armEncoderTicksToDegrees(armMotor.getSelectedSensorVelocity()) /10.0;
    }

    /**
     * Used to prevent jerking the arm when the robot's been disabled and
     * the arm has fallen/changed positons
     */
    public void setArmPositionToCurrentPosition () {
        setArmPosition(getArmPosition());
    }

    /** 
     * Use the pre-tuned regression to set the position of arm based
     * on the data from the limelight
     */
    public void aim () {
        if (Vision.mainCamera.hasTargets()) {
            setArmPosition(regression.evaluate(Vision.getDistanceFromTarget(this)));
        } else {
            setArmPosition(Constants.Arm.VISION_SEEK_ANGLE);
        }
    }

    @Override
    public void periodic() {
        targetState = profile.calculate(timer.get());
        
        //if(isArmPositionSynced){
            // Set motor closed loop target to the TrapezoidProfile's calculated position
            armMotor.set(
                ControlMode.Position, 
                UnitConversions.Arm.armDegreesToEncoderTicks(targetState.position)
            );
        //}
        
        SmartDashboard.putNumber("Arm setpoint", armMotor.getClosedLoopTarget());
        SmartDashboard.putNumber("Arm setpoint degrees", UnitConversions.Arm.armEncoderTicksToDegrees(armMotor.getClosedLoopTarget()));
        SmartDashboard.putNumber("Arm position degrees", UnitConversions.Arm.armEncoderTicksToDegrees(armMotor.getSelectedSensorPosition()));
        SmartDashboard.putNumber("Distance", Vision.getDistanceFromTarget(this));
        SmartDashboard.putNumber("Aim angle", regression.evaluate(Vision.getDistanceFromTarget(this)));
        SmartDashboard.putNumber("Arm power", armMotor.getMotorOutputPercent());
    }
}
