package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SerializerSubsystem;

public class Intake extends CommandBase {

    IntakeSubsystem intake;
    SerializerSubsystem serializer;
    ArmSubsystem arm;

    public Intake (IntakeSubsystem intake, SerializerSubsystem serializer, ArmSubsystem arm) {
        this.intake = intake;
        this.serializer = serializer;
        this.arm = arm;

        addRequirements(intake, serializer, arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        arm.setArmPosition(Constants.Arm.INTAKE_ANGLE);
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