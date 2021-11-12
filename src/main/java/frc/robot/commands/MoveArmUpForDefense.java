package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

/**
 * Move the arm all the way up to block other robots' shots
 */
public class MoveArmUpForDefense extends CommandBase {

    ArmSubsystem arm;

    public MoveArmUpForDefense (ArmSubsystem arm) {
        this.arm = arm;

        addRequirements(arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm.setArmPosition(180);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        arm.setArmPosition(Constants.Arm.STOWED_ANGLE);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
}