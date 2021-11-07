package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
        mainFlywheelMaster = new WPI_TalonFX(Constants.Shooter.MAIN_FLYWHEEL_MASTER_ID);
        mainFlywheelSlave = new WPI_TalonFX(Constants.Shooter.MAIN_FLYWHEEL_SLAVE_ID);
        outerFlywheelMaster = new WPI_TalonFX(Constants.Shooter.OUTER_FLYWHEEL_MASTER_ID);
        outerFlywheelSlave = new WPI_TalonFX(Constants.Shooter.OUTER_FLYWHEEL_SLAVE_ID);

        // Configure main flywheel motors
        configureMotorPair(
            mainFlywheelMaster,
            mainFlywheelSlave,
            Constants.Shooter.MAIN_FLYWHEEL_PID,
            false);

        // Configure outer flywheel motors
        configureMotorPair(
            outerFlywheelMaster, 
            outerFlywheelSlave,
            Constants.Shooter.OUTER_FLYWHEEL_PID,
            true);

        t = new Timer();
        t.start();
    }

    /**
     * Sets the speed of the flywheels in rotations per second
     * 
     * @param RPM
     */
    public void setFlywheelSpeeds (double mainRPM, double outerRPM) {
        System.out.println(UnitConversions.Shooter.mainFlywheelRPMToEncoderTicksPer100ms(mainRPM));
        mainFlywheelMaster.set(ControlMode.Velocity, UnitConversions.Shooter.mainFlywheelRPMToEncoderTicksPer100ms(mainRPM));
        outerFlywheelMaster.set(ControlMode.Velocity, UnitConversions.Shooter.outerFlywheelRPMToEncoderTicksPer100ms(outerRPM));
    }

    /**
     * Set the flywheels to shooting speed (as defined in Constants.java)
     */
    public void activateFlywheels () {
        setFlywheelSpeeds(Constants.Shooter.MAIN_FLYWHEEL_SHOOTING_RPM, Constants.Shooter.OUTER_FLYWHEEL_SHOOTING_RPM);
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
        boolean isRunning = getMainFlywheelSpeed() >= 50 && getOuterFlywheelSpeed() >= 50;

        boolean mainFlywheelUpToSpeed = (mainFlywheelMaster.getClosedLoopError() <= Constants.Shooter.MAIN_FLYWHEEL_SPEED_TOLERANCE) && 
            (mainFlywheelSpeedROC <= Constants.Shooter.MAIN_FLYWHEEL_ROC_TOLERANCE);

        boolean outerFlywheelUpToSpeed = (outerFlywheelSlave.getClosedLoopError() <= Constants.Shooter.OUTER_FLYWHEEL_SPEED_TOLERANCE) && 
            (outerFlywheelSpeedROC <= Constants.Shooter.OUTER_FLYWHEEL_ROC_TOLERANCE);

        return isRunning && mainFlywheelUpToSpeed && outerFlywheelUpToSpeed;
    }

    public void stopFlywheels () {
        mainFlywheelMaster.stopMotor();
        outerFlywheelMaster.stopMotor();
    }

    private void configureMotorPair (TalonFX master, TalonFX slave, PIDParameters pid, boolean inverted) {
        master.configFactoryDefault();
        master.setNeutralMode(NeutralMode.Coast);
        master.setInverted(inverted);
        pid.configureMotorPID(master);
        //master.config_IntegralZone(0, UnitConversions.Shooter.mainFlywheelRPMToEncoderTicksPer100ms(RPM))

        slave.follow(master);
        slave.setInverted(!inverted);
    }

    @Override
    public void periodic() {
        // Compute flywheel speed rate of change
        double mainFlywheelSpeed = getMainFlywheelSpeed();
        double outerFlywheelSpeed = getOuterFlywheelSpeed();

        if (t.get() >= 0.100) {
            mainFlywheelSpeedROC = (mainFlywheelSpeed - lastMainFlywheelSpeed) / t.get();
            outerFlywheelSpeedROC = (outerFlywheelSpeed - lastOuterFlywheelSpeed) / t.get();
            t.reset();

            lastMainFlywheelSpeed = mainFlywheelSpeed;
            lastOuterFlywheelSpeed = outerFlywheelSpeed;
        }

        SmartDashboard.putNumber("Main flywheel speed", getMainFlywheelSpeed());
        SmartDashboard.putNumber("Outer flywheel speed", getOuterFlywheelSpeed());
        SmartDashboard.putNumber("Main flywheel target", UnitConversions.Shooter.mainFlywheelEncoderTicksPer100msToRPM(mainFlywheelMaster.getClosedLoopTarget()));
        SmartDashboard.putNumber("Outer flywheel target", UnitConversions.Shooter.outerFlywheelEncoderTicksPer100msToRPM(outerFlywheelMaster.getClosedLoopTarget()));
        SmartDashboard.putNumber("Main flywheel power", mainFlywheelMaster.getMotorOutputPercent());
        SmartDashboard.putNumber("Outer flywheel power", outerFlywheelMaster.getMotorOutputPercent());
        SmartDashboard.putNumber("Main flywheel ROC", mainFlywheelSpeedROC);
        SmartDashboard.putNumber("Outer flywheel ROC", outerFlywheelSpeedROC);
        SmartDashboard.putBoolean("Shooter up to speed?", isFlywheelUpToSpeed());
    }
}
