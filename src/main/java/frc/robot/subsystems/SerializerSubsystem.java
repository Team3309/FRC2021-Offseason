package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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

    public SerializerSubsystem () {
        brushMotor = new WPI_VictorSPX(Constants.Serializer.BRUSH_MOTOR_ID);
        brushMotor.configFactoryDefault();
        brushMotor.setNeutralMode(NeutralMode.Brake);

        accleratorMotor = new WPI_VictorSPX(Constants.Serializer.ACCLERATOR_MOTOR_ID);
        accleratorMotor.configFactoryDefault();
        accleratorMotor.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Enable the brushes
     */
    public void activateBrushes () {
        brushMotor.set(ControlMode.PercentOutput, Constants.Serializer.BRUSH_POWER);
    }

    /**
     * Disable the brushes
     */
    public void disableBrushes () {
        brushMotor.stopMotor();
    }

    /**
     * Enable the brushes
     */
    public void activateAcclerator () {
        accleratorMotor.set(ControlMode.PercentOutput, Constants.Serializer.ACCLERATOR_POWER);
    }

    /**
     * Disable the brushes
     */
    public void disableAcclerator () {
        accleratorMotor.stopMotor();
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
