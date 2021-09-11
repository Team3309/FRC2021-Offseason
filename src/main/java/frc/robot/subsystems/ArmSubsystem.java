package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
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

    public ArmSubsystem () {
        armMotor = new WPI_TalonFX(Constants.Arm.ARM_MOTOR_ID);
        armMotor.configFactoryDefault();
        armMotor.setNeutralMode(NeutralMode.Brake);
        PIDParameters.configureMotorPID(armMotor, Constants.Arm.ARM_PID);

        limitSwitch = new DigitalInput(Constants.Arm.LIMIT_SWITCH_PORT);
        zeroArmPosition();

        regression = new LinearRegression(Constants.Shooter.AIM_REGRESSION_DATA);
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
        armMotor.set(ControlMode.Position, UnitConversions.Arm.armDegreesToEncoderTicks(degrees));
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

    /**
     * Use the pre-tuned regression to set the position of arm based
     * on the data from the limelight
     */
    public void aim () {
        setArmPosition(regression.evaluate(Vision.getDistanceFromTarget(this)));
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
