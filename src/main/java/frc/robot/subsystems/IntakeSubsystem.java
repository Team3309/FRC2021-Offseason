package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Represents the intake subsystem
 * 
 * <p>The physical subsystem includes the four-bar-linkage (controlled
 * by pneumatics) and intake rollers
 */
public class IntakeSubsystem extends SubsystemBase {

    private WPI_TalonSRX intakeMotor;
    private DoubleSolenoid intakePiston;

    public IntakeSubsystem () {
        intakeMotor = new WPI_TalonSRX(Constants.Intake.MOTOR_ID);
        intakeMotor.configFactoryDefault();
        intakeMotor.setNeutralMode(NeutralMode.Coast);

        intakePiston = new DoubleSolenoid(Constants.PCM_CAN_ID, Constants.Intake.FIRST_PCM_PORT, Constants.Intake.SECOND_PCM_PORT);
    }

    /**
     * Extend intake and activate motor
     */
    public void deploy () {
        intakePiston.set(Value.kReverse);
        intakeMotor.set(ControlMode.PercentOutput, Constants.Intake.MOTOR_POWER);
    }

    /**
     * Retract intake and stop motor
     */
    public void retract () {
        intakePiston.set(Value.kForward);
        intakeMotor.stopMotor();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
