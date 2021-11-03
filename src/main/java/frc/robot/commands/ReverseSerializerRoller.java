package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SerializerSubsystem;

public class ReverseSerializerRoller extends CommandBase {

    SerializerSubsystem serializer;

    public ReverseSerializerRoller (SerializerSubsystem serializer) {
        this.serializer = serializer;

        addRequirements(serializer);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        serializer.reverseRoller();
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        serializer.disableRoller();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}