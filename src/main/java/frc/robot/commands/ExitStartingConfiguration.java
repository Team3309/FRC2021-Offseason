package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

/**
 * Unclip the arm from its starting position
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
        arm.setArmPower(Constants.Arm.EXIT_STARTING_CONFIG_POWER);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.stopArm();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return arm.getArmPosition() <= Constants.Arm.EXIT_STARTInG_CONFIG_ANGLE;
    }
}