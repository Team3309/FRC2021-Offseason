package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.Vision;
import frc.robot.subsystems.DriveSubsystem;

public class LineUpWithTarget extends CommandBase {

    DriveSubsystem drive;

    public LineUpWithTarget (DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double targetx = Vision.mainCamera.hasTargets() ? Vision.mainCamera.getBestTarget().getX() : 0;

        // Move the robot to align with the target, while allowing the drivers to control throttle
        drive.setDrivePowerArcade(
            -OperatorInterface.DriverLeft.getYWithDeadband(), 
            -Constants.Drive.ROTATION_PID_CONTROLLER.calculate(targetx, 0));
    }

    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}
