package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.UnitConversions;
import frc.robot.Vision;
import friarLib2.utility.PIDParameters;

public class DriveSubsystem extends SubsystemBase {

    // Drive motors
    private WPI_TalonFX leftMaster = new WPI_TalonFX(Constants.Drive.LEFT_MASTER_ID);
    private WPI_TalonFX leftSlave = new WPI_TalonFX(Constants.Drive.LEFT_SLAVE_ID);

    private WPI_TalonFX rightMaster = new WPI_TalonFX(Constants.Drive.RIGHT_MASTER_ID);
    private WPI_TalonFX rightSlave = new WPI_TalonFX(Constants.Drive.RIGHT_SLAVE_ID);

    private ADIS16470_IMU imu;
    private DifferentialDriveOdometry odometry;
    private DifferentialDriveKinematics kinematics;
    private Pose2d currentRobotPose = new Pose2d();

    private final Field2d f2d = new Field2d();
    
    public DriveSubsystem () {
        configureMotors(leftMaster, leftSlave, true);
        configureMotors(rightMaster, rightSlave, false);

        imu = new ADIS16470_IMU();
        kinematics = new DifferentialDriveKinematics(Constants.Drive.DRIVE_BASE_WIDTH);
        odometry = new DifferentialDriveOdometry(getRobotRotation());

        SmartDashboard.putData("Field", f2d);
    }

    public void setDrivePower (double left, double right) {
        leftMaster.set(ControlMode.PercentOutput, left);
        rightMaster.set(ControlMode.PercentOutput, right);
    }

    /**
     * Set the speeds of each side of the drivetrain in meters per
     * second
     * 
     * @param left
     * @param right
     */
    public void setDriveSpeeds (double left, double right) {
        leftMaster.set(ControlMode.Velocity, UnitConversions.Drive.MPSToEncoderTicksPer100ms(left));
        rightMaster.set(ControlMode.Velocity, UnitConversions.Drive.MPSToEncoderTicksPer100ms(right));
    }

    /**
     * Use the "arcade" control scheme to set drive power
     * 
     * @param throttle positive is forward
     * @param turn positive is right
     */
    public void setDrivePowerArcade (double throttle, double turn) {
        setDrivePower(throttle + turn, throttle - turn);
    }
    
    /**
     * Perform kinematics to set the forward and angular
     * speeds of the robot
     * 
     * @param speeds
     */
    public void setChassisSpeeds(ChassisSpeeds speeds) {
        DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speeds);
        setDriveSpeeds(
            wheelSpeeds.leftMetersPerSecond, 
            wheelSpeeds.rightMetersPerSecond);
	}

    /**
     * Stop the drivetrain
     */
    public void stop () {
        leftMaster.stopMotor();
        rightMaster.stopMotor();
    }

    /**
     * Initialize motors on half of the drivetrain
     * 
     * @param master other motor follows this one
     * @param slave follows the master
     */
    private void configureMotors (WPI_TalonFX master, WPI_TalonFX slave, boolean inverted) {
        master.configFactoryDefault();
        master.setInverted(inverted);
        master.setNeutralMode(NeutralMode.Brake);
        Constants.Drive.WHEEL_PID_CONSTANTS.configureMotorPID(master);

        slave.configFactoryDefault();
        slave.setInverted(inverted);
        slave.setNeutralMode(NeutralMode.Brake);
        slave.follow(master);
    }

    /**
     * Get the current robot pose according to dead-reckoning odometry
     * See https://docs.wpilib.org/en/stable/docs/software/kinematics-and-odometry/swerve-drive-odometry.html for more details
     * 
     * @return Current robot pose
     */
    public Pose2d getRobotPose () {
        return currentRobotPose;
    }

    /**
     * Use the IMU to read the robot's yaw
     * 
     * @return Rotation2d representing IMU's measured angle
     */
    public Rotation2d getRobotRotation () {
        double angle = imu.getAngle();
        return Rotation2d.fromDegrees(angle);
    }

    /**
     * Set the odometry readings
     * 
     * @param pose Pose to be written to odometry
     * @param rotation Roatation to be written to odometry
     */
    public void resetOdometry (Pose2d pose, Rotation2d rotation) {
        odometry.resetPosition(pose, rotation);
        leftMaster.setSelectedSensorPosition(0);
        rightMaster.setSelectedSensorPosition(0);
    }

    @Override
    public void periodic() {
        currentRobotPose = odometry.update(
            getRobotRotation(), 
            UnitConversions.Drive.encoderTicksToMeters(leftMaster.getSelectedSensorPosition()), 
            UnitConversions.Drive.encoderTicksToMeters(rightMaster.getSelectedSensorPosition())
        );

        SmartDashboard.putNumber("Left target", UnitConversions.Drive.EncoderTicksPer100mstoMPS(leftMaster.getClosedLoopTarget()));
        SmartDashboard.putNumber("Left speed", UnitConversions.Drive.EncoderTicksPer100mstoMPS(leftMaster.getSelectedSensorVelocity()));

        if (Vision.mainCamera.hasTargets()) {
            //SmartDashboard.putNumber("VX", Vision.mainCamera.getBestTarget().getX());
        }

        f2d.setRobotPose(currentRobotPose);
    }
}
