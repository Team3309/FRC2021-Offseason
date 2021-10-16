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

    private WPI_TalonFX mainFlywheelMaster;
    private WPI_TalonFX mainFlywheelSlave;
    private WPI_TalonFX outerFlywheelMaster;
    private WPI_TalonFX outerFlywheelSlave;

    private double lastMainFlywheelSpeed = 0;
    private double lastOuterFlywheelSpeed = 0;
    private double mainFlywheelSpeedROC = 0; // Rate of change of the flywheel speed
    private double outerFlywheelSpeedROC = 0;
    private Timer t;

    public ShooterSubsystem () {
        // Configure main flywheel motors
        mainFlywheelMaster = new WPI_TalonFX(Constants.Shooter.MAIN_FLYWHEEL_MASTER_ID);
        mainFlywheelMaster.configFactoryDefault();
        mainFlywheelMaster.setNeutralMode(NeutralMode.Coast);
        PIDParameters.configureMotorPID(mainFlywheelMaster, Constants.Shooter.MAIN_FLYWHEEL_PID);

        mainFlywheelSlave = new WPI_TalonFX(Constants.Shooter.MAIN_FLYWHEEL_SLAVE_ID);
        mainFlywheelSlave.follow(mainFlywheelMaster);

        // Configure outer flywheel motors
        outerFlywheelMaster = new WPI_TalonFX(Constants.Shooter.OUTER_FLYWHEEL_MASTER_ID);
        outerFlywheelMaster.configFactoryDefault();
        outerFlywheelMaster.setNeutralMode(NeutralMode.Coast);
        PIDParameters.configureMotorPID(outerFlywheelMaster, Constants.Shooter.OUTER_FLYWHEEL_PID);

        outerFlywheelSlave = new WPI_TalonFX(Constants.Shooter.OUTER_FLYWHEEL_SLAVE_ID);
        outerFlywheelSlave.follow(outerFlywheelMaster);

        t = new Timer();
        t.start();
    }

    /**
     * Sets the speed of the flywheels in rotations per second
     * 
     * @param RPM
     */
    public void setFlywheelSpeeds (double mainRPM, double outerRPM) {
        mainFlywheelMaster.set(ControlMode.Velocity, UnitConversions.Shooter.mainFlywheelEncoderTicksPer100msToRPM(mainRPM));
        outerFlywheelMaster.set(ControlMode.Velocity, UnitConversions.Shooter.outerFlywheelEncoderTicksPer100msToRPM(outerRPM));
    }

    public double getMainFlywheelSpeed () {
        return UnitConversions.Shooter.mainFlywheelEncoderTicksPer100msToRPM(mainFlywheelMaster.getSelectedSensorVelocity());
    }

    public double getOuterFlywheelSpeed () {
        return UnitConversions.Shooter.outerFlywheelEncoderTicksPer100msToRPM(outerFlywheelMaster.getSelectedSensorVelocity());
    }

    /**
     * @return if the closed loop error and flywheel speed rate of change
     * are below their tuned values
     */
    public boolean isFlywheelUpToSpeed () {
        boolean mainFlywheelUpToSpeed = (mainFlywheelMaster.getClosedLoopError() <= Constants.Shooter.MAIN_FLYWHEEL_SPEED_TOLERANCE) && 
            (mainFlywheelSpeedROC <= Constants.Shooter.MAIN_FLYWHEEL_ROC_TOLERANCE);

        boolean outerFlywheelUpToSpeed = (outerFlywheelSlave.getClosedLoopError() <= Constants.Shooter.OUTER_FLYWHEEL_SPEED_TOLERANCE) && 
            (outerFlywheelSpeedROC <= Constants.Shooter.OUTER_FLYWHEEL_ROC_TOLERANCE);

        return mainFlywheelUpToSpeed && outerFlywheelUpToSpeed;
    }

    public void stopFlywheels () {
        mainFlywheelMaster.stopMotor();
        outerFlywheelMaster.stopMotor();
    }

    @Override
    public void periodic() {
        // Compute flywheel speed rate of change
        double mainFlywheelSpeed = getMainFlywheelSpeed();
        double outerFlywheelSpeed = getOuterFlywheelSpeed();

        mainFlywheelSpeedROC = (mainFlywheelSpeed - lastMainFlywheelSpeed) / t.get();
        outerFlywheelSpeedROC = (outerFlywheelSpeed - lastOuterFlywheelSpeed) / t.get();
        t.reset();
    }
}
