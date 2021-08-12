package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import friarLib2.utility.PIDParameters;

/**
 * Represents the shooter subsystem
 * 
 * <p>The physical subsystem includes the flywheels and arm
 */
public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX flywheelMotor;
    private WPI_TalonFX armMotor;
    private DigitalInput limitSwitch;

    public ShooterSubsystem () {
        flywheelMotor = new WPI_TalonFX(Constants.Shooter.FLYWHEEL_MOTOR_ID);
        flywheelMotor.configFactoryDefault();
        flywheelMotor.setNeutralMode(NeutralMode.Coast);
        PIDParameters.configureMotorPID(flywheelMotor, Constants.Shooter.FLYWHEEL_PID);

        armMotor = new WPI_TalonFX(Constants.Shooter.ARM_MOTOR_ID);
        armMotor.configFactoryDefault();
        armMotor.setNeutralMode(NeutralMode.Brake);
        PIDParameters.configureMotorPID(armMotor, Constants.Shooter.ARM_PID);

        limitSwitch = new DigitalInput(Constants.Shooter.LIMIT_SWITCH_PORT);
        zeroArmPosition();
    }

    /**
     * Sets the speed of the flywheels in rotations per second
     * 
     * @param RPM
     */
    public void setFlywheelSpeed (double RPM) {
        flywheelMotor.set(ControlMode.Velocity, UnitConversions.Shooter.flywheelEncoderTicksPer100msToRPM(RPM));
    }

    public boolean isFlywheelUpToSpeed () {
        return flywheelMotor.getClosedLoopError() <= Constants.Shooter.FLYWHEEL_SPEED_TOLERANCE;
    }

    public void stopFlywheel () {
        flywheelMotor.stopMotor();
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
        armMotor.set(ControlMode.Position, UnitConversions.Shooter.armDegreesToEncoderTicks(degrees));
    }

    /**
     * Zero the arm's encoder
     * 
     * <p>Assumes that the arm is at the limit switch
     */
    public void zeroArmPosition () {
        armMotor.setSelectedSensorPosition(UnitConversions.Shooter.armDegreesToEncoderTicks(Constants.Shooter.LIMIT_SWITCH_ANGLE));
    }

    public double getArmPosition () {
        return UnitConversions.Shooter.armEncoderTicksToDegrees(armMotor.getSelectedSensorPosition());
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
