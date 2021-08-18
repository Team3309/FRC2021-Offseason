package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class FollowTrajectory extends CommandBase {

    DriveSubsystem drive;

    RamseteController ramseteController;

    public FollowTrajectory (DriveSubsystem drive) {
        this.drive = drive;
        addRequirements(drive);

        ramseteController = new RamseteController();
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {}

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
