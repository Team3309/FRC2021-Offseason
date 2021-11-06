package friarLib2.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Run the command in the constructor for the given amount of seconds
 */
public class RunForTime extends CommandBase {

    Timer t;

    Command command;
    double seconds;

    public RunForTime (Command command, double seconds) {
        this.command = command;
        this.seconds = seconds;

        t = new Timer();
    }

    @Override
    public void initialize() {
        t.reset();
        t.start();
        command.initialize();
    }

    @Override
    public void execute() {
        command.execute();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        command.end(interrupted);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return t.get() >= seconds || command.isFinished();
    }
}
