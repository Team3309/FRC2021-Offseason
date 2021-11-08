package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class BrakeFlywheels extends CommandBase {

    ShooterSubsystem shooter;

    public BrakeFlywheels (ShooterSubsystem shooter) {
        this.shooter = shooter;

        addRequirements(shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize () {
        shooter.brakeFlywheels();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        shooter.stopFlywheels();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}