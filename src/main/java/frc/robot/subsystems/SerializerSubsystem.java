package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Represents the serializer subsystem
 * 
 * <p>The physical subsystem includes the roller brushes and indexer
 * wheel
 */
public class SerializerSubsystem extends SubsystemBase {

    WPI_VictorSPX brushMotor;
    WPI_VictorSPX accleratorMotor;
    WPI_TalonSRX rollerMotor;

    public SerializerSubsystem () {
        brushMotor = new WPI_VictorSPX(Constants.Serializer.BRUSH_MOTOR_ID);
        brushMotor.configFactoryDefault();
        brushMotor.setNeutralMode(NeutralMode.Brake);
        brushMotor.setInverted(true);

        accleratorMotor = new WPI_VictorSPX(Constants.Serializer.ACCLERATOR_MOTOR_ID);
        accleratorMotor.configFactoryDefault();
        accleratorMotor.setNeutralMode(NeutralMode.Brake);

        rollerMotor = new WPI_TalonSRX(Constants.Serializer.ROLLER_MOTOR_ID);
        rollerMotor.configFactoryDefault();
        rollerMotor.setNeutralMode(NeutralMode.Coast);
    }

    public void activateBrushes () {
        brushMotor.set(ControlMode.PercentOutput, Constants.Serializer.BRUSH_POWER);
    }

    public void disableBrushes () {
        brushMotor.stopMotor();
    }

    public void reverseBrushes () {
        brushMotor.set(ControlMode.PercentOutput, -Constants.Serializer.BRUSH_REVERSE_POWER);
    }

    public void activateAcclerator () {
        accleratorMotor.set(ControlMode.PercentOutput, Constants.Serializer.ACCLERATOR_POWER);
    }

    public void disableAcclerator () {
        accleratorMotor.stopMotor();
    }

    public void activateRoller () {
        rollerMotor.set(ControlMode.PercentOutput, Constants.Serializer.ROLLER_POWER);
    }

    public void disableRoller () {
        rollerMotor.stopMotor();
    }

    public void reverseRoller () {
        rollerMotor.set(ControlMode.PercentOutput, -Constants.Serializer.ROLLER_REVERSE_POWER);
    }

    public void activateBrushesAndRoller () {
        activateBrushes();
        activateRoller();
    }

    public void disableBrushesAndRoller () {
        disableBrushes();
        disableRoller();
    }

    public void reverseBrushesAndRoller () {
        reverseBrushes();
        reverseRoller();
    }

    /**
     * Turn the acclerator on or off
     * 
     * @param on
     */
    public void setAcclerator (boolean on) {
        if (on) {
            activateAcclerator();
        } else {
            disableAcclerator();
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }
}
