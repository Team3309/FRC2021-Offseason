package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Manual control of the robot
 */
public class DriveTeleop extends CommandBase {
    DriveSubsystem drive;

    public DriveTeleop (DriveSubsystem drive) {
        this.drive = drive;

        addRequirements(drive);
    }

    @Override
    public void execute() {
      // Read the joystick values and apply deadband
      double throttle = -OperatorInterface.DriverLeft.getYWithDeadband();
      double turn = OperatorInterface.DriverRight.getXWithDeadband();

      drive.setDrivePowerArcade(throttle, turn);
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
