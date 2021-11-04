package frc.robot.util;

import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * Represents a combined state for the shooter and arm that constitute
 * a single shooting position
 */
public class FiringSolution {
    public double mainFlywheelRPM;
    public double outerFlywheelRPM;
    public double armAngle;
    
    public FiringSolution (double mainFlywheelRPM, double outerFlywheelRPM, double armAngle) {
        this.mainFlywheelRPM = mainFlywheelRPM;
        this.outerFlywheelRPM = outerFlywheelRPM;
        this.armAngle = armAngle;
    }

    public FiringSolution (double armAngle) {
        mainFlywheelRPM = Constants.Shooter.MAIN_FLYWHEEL_SHOOTING_RPM;
        outerFlywheelRPM = Constants.Shooter.OUTER_FLYWHEEL_SHOOTING_RPM;
        this.armAngle = armAngle;
    }

    /**
     * Set the arm and shooter speeds to 
     * 
     * @param arm
     * @param shooter
     */
    public void goToSolution (ArmSubsystem arm, ShooterSubsystem shooter) {
        arm.setArmPosition(armAngle);
        shooter.setFlywheelSpeeds(mainFlywheelRPM, outerFlywheelRPM);
    }
}
