package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OperatorInterface;
import frc.robot.subsystems.ClimberSubsystem;

/**
 * When 
 */
public class Climb extends CommandBase {

    ClimberSubsystem climber;

    public Climb (ClimberSubsystem climber) {
        this.climber = climber;

        addRequirements(climber);
    }

    @Override
    public void initialize() {
        climber.retract();
    }

    @Override
    public void execute() {
        double winchPower = OperatorInterface.applyDeadband(OperatorInterface.OperatorController.getTriggerAxis(Hand.kLeft), Constants.XBOX_DEADBAND);
        climber.activateWinch(winchPower);
    }

    @Override
    public void end(boolean interrupted) {
        climber.stopWinch();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return !climber.hasBeenExtended;
    }
}
