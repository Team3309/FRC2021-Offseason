package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.Vision;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class AimAndShoot extends CommandBase {

    DriveSubsystem drive;
    ArmSubsystem arm;
    SerializerSubsystem serializer;
    ShooterSubsystem shooter;

    public AimAndShoot (DriveSubsystem drive, ArmSubsystem arm, SerializerSubsystem serializer, ShooterSubsystem shooter) {
        this.drive = drive;
        this.arm = arm;
        this.serializer = serializer;
        this.shooter = shooter;

        addRequirements(drive, arm, serializer, shooter);
    }

    @Override
    public void initialize() {
        // Look for targets if none are curretly seen
        if (!Vision.mainCamera.hasTargets()) {
            arm.setArmPosition(Constants.Arm.VISION_SEEK_ANGLE);
        }

        // Activate shooter
        shooter.setFlywheelSpeed(Constants.Shooter.SHOOTING_RPM);
    }

    @Override
    public void execute() {
        // Move the arm to aim at the target
        arm.aim();

        // Move the robot to align with the target, while allowing the drivers to control throttle
        drive.setDrivePowerArcade(
            OperatorInterface.applyDeadband(OperatorInterface.DriverLeft.getY(), Constants.JOYSTICK_DEADBAND), 
            Constants.Drive.ROTATION_PID_CONTROLLER.calculate(Vision.mainCamera.getBestTarget().getX(), 0));

        // Shoot powercells
        if (OperatorInterface.OperatorController.getAButton() && shooter.isFlywheelUpToSpeed()) {
            serializer.activateBrushes();
            serializer.activateAcclerator();
        } else {
            serializer.disableBrushes();
            serializer.disableAcclerator();
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopFlywheels();
        arm.setArmPosition(Constants.Arm.STOWED_ANGLE);
        serializer.disableBrushes();
        serializer.disableAcclerator();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
