package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class Intake extends CommandBase {

    IntakeSubsystem intake;
    SerializerSubsystem serializer;
    ShooterSubsystem shooter;

    public Intake (IntakeSubsystem intake, SerializerSubsystem serializer, ShooterSubsystem shooter) {
        this.intake = intake;
        this.serializer = serializer;
        this.shooter = shooter;

        addRequirements(intake, serializer, shooter);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        shooter.setArmPosition(Constants.Shooter.INTAKE_ANGLE);
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