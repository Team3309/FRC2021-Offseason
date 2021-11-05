package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.SerializerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.util.FiringSolution;

public class AimAndShoot extends CommandBase {

    TriggerCondition shootCondiditon;
    FiringSolution firingSolution = null;

    ArmSubsystem arm;
    SerializerSubsystem serializer;
    ShooterSubsystem shooter;

    /**
     * Initialize an AimAndShoot that uses the firingSolution as its shooting
     * position
     * 
     * @param firingSolution
     * @param shootCondiditon
     * @param drive
     * @param arm
     * @param serializer
     * @param shooter
     */
    public AimAndShoot (FiringSolution firingSolution, TriggerCondition shootCondiditon, ArmSubsystem arm, SerializerSubsystem serializer, ShooterSubsystem shooter) {
        this.firingSolution = firingSolution;
        this.shootCondiditon = shootCondiditon;
        this.arm = arm;
        this.serializer = serializer;
        this.shooter = shooter;

        addRequirements(arm, serializer, shooter);
    }

    /**
     * Initialize an AimAndShoot that automatically aims
     * 
     * @param shootCondiditon
     * @param arm
     * @param serializer
     * @param shooter
     */
    public AimAndShoot (TriggerCondition shootCondiditon, ArmSubsystem arm, SerializerSubsystem serializer, ShooterSubsystem shooter) {
        this.shootCondiditon = shootCondiditon;
        this.arm = arm;
        this.serializer = serializer;
        this.shooter = shooter;

        addRequirements(arm, serializer, shooter);
    }

    @Override
    public void initialize() {
        // Activate shooter
        if (firingSolution == null) {
            shooter.activateFlywheels();
        } else {
            firingSolution.goToSolution(arm, shooter);
        }
    }

    @Override
    public void execute() {
        // Move the arm to aim at the target
        if (firingSolution == null) {
            arm.aim();
        }        
        
        // Shoot powercells
        if (shootCondiditon.get() && shooter.isFlywheelUpToSpeed()) {
            serializer.activateBrushes();
            serializer.activateAcclerator();
        } else {
            serializer.disableBrushes();
            serializer.disableAcclerator();
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopFlywheels();
        arm.setArmPosition(Constants.Arm.STOWED_ANGLE);
        serializer.disableBrushes();
        serializer.disableAcclerator();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    public interface TriggerCondition {
        boolean get();
    }
}
