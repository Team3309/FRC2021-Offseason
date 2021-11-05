package frc.robot.commands.autos;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Power the wheels forward for some amount of seconds
 */
public class DriveForward extends CommandBase {

    DriveSubsystem drive;

    double drivePower;
    double seconds;

    Timer t;

    public DriveForward (double drivePower, double seconds, DriveSubsystem drive) {
        this.drivePower = drivePower;
        this.seconds = seconds;
        this.drive = drive;

        t = new Timer();

        addRequirements(drive);
    }

    @Override
    public void execute() {
        t.reset();
        t.start();

        drive.setDrivePower(drivePower, drivePower);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return t.get() >= seconds;
    }
}
