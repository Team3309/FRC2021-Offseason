package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTest extends CommandBase {
    DriveSubsystem drive;

    public DriveTest (DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
      drive.setDriveSpeeds(1.5, -1.5);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      drive.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
