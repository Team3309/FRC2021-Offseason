package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import friarLib2.utility.PIDParameters;

public class DriveSubsystem extends SubsystemBase {

    // Drive motors
    private WPI_TalonFX leftMaster = new WPI_TalonFX(Constants.Drive.LEFT_MASTER_ID);
    private WPI_TalonFX leftSlave = new WPI_TalonFX(Constants.Drive.LEFT_SLAVE_ID);

    private WPI_TalonFX rightMaster = new WPI_TalonFX(Constants.Drive.RIGHT_MASTER_ID);
    private WPI_TalonFX rightSlave = new WPI_TalonFX(Constants.Drive.RIGHT_SLAVE_ID);
    
    public DriveSubsystem () {
        configureMotors(leftMaster, leftSlave);
        configureMotors(rightMaster, rightSlave);
    }

    public void setDrivePower (double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    public void setDriveSpeeds (double left, double right) {
        leftMaster.set(ControlMode.Velocity, UnitConversions.Drive.MPSToEncoderTicksPer100ms(left));
        rightMaster.set(ControlMode.Velocity, UnitConversions.Drive.MPSToEncoderTicksPer100ms(right));
    }

    /**
     * Initialize motors on half of the drivetrain
     * 
     * @param master other motor follows this one
     * @param slave follows the master
     */
    private void configureMotors (WPI_TalonFX master, WPI_TalonFX slave) {
        master.configFactoryDefault();
        PIDParameters.configureMotorPID(master, Constants.Drive.PID_CONSTANTS);

        slave.configFactoryDefault();
        slave.follow(master);
        slave.setInverted(true);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
