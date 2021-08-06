package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;

public class Intake extends CommandBase {

    IntakeSubsystem intake;
    SerializerSubsystem serializer;

    public Intake (IntakeSubsystem intake, SerializerSubsystem serializer) {
        this.intake = intake;
        this.serializer = serializer;

        addRequirements(intake, serializer);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        intake.deploy();
        serializer.activateBrushes();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.retract();
        serializer.disableBrushes();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}