package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {

    private WPI_TalonFX winchMotor;
    private Solenoid climberPiston1;
    private Solenoid climberPiston2;

    /** 
     * True if the climber has been extended at any point after the
     * robot has been powered on 
     */
    public boolean hasBeenExtended = false;

    public ClimberSubsystem () {
        winchMotor = new WPI_TalonFX(Constants.Climber.WINCH_MOTOR_ID);
        winchMotor.configFactoryDefault();
        winchMotor.setNeutralMode(NeutralMode.Brake);
        winchMotor.setInverted(true);

        climberPiston1 = new Solenoid(Constants.Climber.PCM_PORT_1);
        climberPiston2 = new Solenoid(Constants.Climber.PCM_PORT_2);
    }

    /**
     * Extend the climber
     */
    public void deploy () {
        climberPiston1.set(true);
        climberPiston2.set(true);
        hasBeenExtended = true;
    }

    /**
     * Retract the climber
     */
    public void retract () {
        climberPiston1.set(false);
        climberPiston2.set(false);
    }

    /**
     * Turn on the winch if the climber has been extended
     */
    public void activateWinch () {
        if (hasBeenExtended) {
            winchMotor.set(ControlMode.PercentOutput, Constants.Climber.WINCH_POWER);
        }
    }

    /**
     * Set the power of the winch motor
     * 
     * @param power
     */
    public void activateWinch (double power) {
        if (hasBeenExtended) {
            winchMotor.set(ControlMode.PercentOutput, Math.abs(power));
        }
    }

    public void stopWinch () {
        winchMotor.stopMotor();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
