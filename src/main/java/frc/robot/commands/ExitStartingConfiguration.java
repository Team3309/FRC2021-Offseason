package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

public class ExitStartingConfiguration extends CommandBase {

    ArmSubsystem arm;

    public ExitStartingConfiguration (ArmSubsystem arm) {
        this.arm = arm;

        addRequirements(arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm.setArmPosition(Constants.Arm.INTAKE_ANGLE);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return arm.isArmAtPosition();
    }
}