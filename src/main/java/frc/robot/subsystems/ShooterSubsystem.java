package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import friarLib2.utility.PIDParameters;

/**
 * Represents the shooter subsystem
 * 
 * <p>The physical subsystem includes the flywheels
 */
public class ShooterSubsystem extends SubsystemBase {

    private WPI_TalonFX flywheelMotor;

    public ShooterSubsystem () {
        flywheelMotor = new WPI_TalonFX(Constants.Shooter.FLYWHEEL_MOTOR_ID);
        flywheelMotor.configFactoryDefault();
        flywheelMotor.setNeutralMode(NeutralMode.Coast);
        PIDParameters.configureMotorPID(flywheelMotor, Constants.Shooter.FLYWHEEL_PID);
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

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
