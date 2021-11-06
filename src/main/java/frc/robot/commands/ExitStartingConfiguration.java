package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

/**
 * Unclip the arm from its starrting position
 */
public class ExitStartingConfiguration extends CommandBase {

    ArmSubsystem arm;

    public ExitStartingConfiguration (ArmSubsystem arm) {
        this.arm = arm;

        addRequirements(arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm.setArmPower(-.2);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.stopArm();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return arm.getArmPosition() <= 50;
    }
}