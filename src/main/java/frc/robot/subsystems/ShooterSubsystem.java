package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
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

    private double lastFlywheelSpeed = 0;
    private double flywheelSpeedROC = 0; // Rate of change of the flywheel speed
    private Timer t;

    public ShooterSubsystem () {
        flywheelMotor = new WPI_TalonFX(Constants.Shooter.FLYWHEEL_MOTOR_ID);
        flywheelMotor.configFactoryDefault();
        flywheelMotor.setNeutralMode(NeutralMode.Coast);
        PIDParameters.configureMotorPID(flywheelMotor, Constants.Shooter.FLYWHEEL_PID);

        t = new Timer();
        t.start();
    }

    /**
     * Sets the speed of the flywheels in rotations per second
     * 
     * @param RPM
     */
    public void setFlywheelSpeed (double RPM) {
        flywheelMotor.set(ControlMode.Velocity, UnitConversions.Shooter.flywheelEncoderTicksPer100msToRPM(RPM));
    }

    public double getFlywheelSpeed () {
        return UnitConversions.Shooter.flywheelEncoderTicksPer100msToRPM(flywheelMotor.getSelectedSensorVelocity());
    }

    /**
     * @return if the closed loop error and flywheel speed rate of change
     * are below their tuned values
     */
    public boolean isFlywheelUpToSpeed () {
        return (flywheelMotor.getClosedLoopError() <= Constants.Shooter.FLYWHEEL_SPEED_TOLERANCE) && 
            (flywheelSpeedROC <= Constants.Shooter.FLYWHEEL_ROC_TOLERANCE);
    }

    public void stopFlywheels () {
        flywheelMotor.stopMotor();
    }

    @Override
    public void periodic() {
        // Compute flywheel speed rate of change
        double currentFlywheelSpeed = getFlywheelSpeed();
        flywheelSpeedROC = (currentFlywheelSpeed - lastFlywheelSpeed) / t.get();
        t.reset();
    }
}
