package friarLib2.utility;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Represents a set of PID constants
 */
public class PIDParameters implements Sendable {
    private double kP = 0;
    private double kI = 0;
    private double kD = 0;
    private double kF = 0;

    private BaseTalon linkedMotor;
    
    public PIDParameters (double kP, double kI, double kD, double kF, String name) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.kF = kF;
        SmartDashboard.putData(name, this);
    }

    public PIDParameters (double kP, double kI, double kD, String name) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        SmartDashboard.putData(name, this);
    }

    /**
     * Helper method to initialize a Talon's PID parameters
     * 
     * @param motor motor to configure
     * @param PID PID parameters
     */
    public void configureMotorPID (BaseTalon motor) {
        linkedMotor = motor;
        updateMotorPID();
    }

    /**
     * Set the PID gains of the linked motor
     */
    public void updateMotorPID () {
        linkedMotor.config_kP(0, kP);
        linkedMotor.config_kI(0, kI);
        linkedMotor.config_kD(0, kD);
        linkedMotor.config_kF(0, kF);
    }

	@Override
	public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("PIDController");
        builder.addDoubleProperty("p", this::getP, this::setP);
        builder.addDoubleProperty("i", this::getI, this::setI);
        builder.addDoubleProperty("d", this::getD, this::setD);
        builder.addDoubleProperty("setpoint", this::dummyGetter, this::dummySetter);
    }
    
    /**
     * @return the proportional gain
     */
    public double getP () {
        return kP;
    }

    /**
     * @return the integral gain
     */
    public double getI () {
        return kI;
    }

    /**
     * @return the derivitave gain
     */
    public double getD () {
        return kD;
    }

    /**
     * @return the feedforward gain
     */
    public double getF () {
        return kF;
    }

    /**
     * @param kP new proportional gain
     */
    public void setP (double kP) {
        this.kP = kP;
        updateMotorPID();
    }

    /**
     * @param kI new integral gain
     */
    public void setI (double kI) {
        this.kI = kI;
        updateMotorPID();
    }

    /**
     * @param kD new integral gain
     */
    public void setD (double kD) {
        this.kD = kD;
        updateMotorPID();
    }

    /**
     * @param kF new feedforward gain
     */
    public void setF (double kF) {
        this.kF = kF;
        updateMotorPID();
    }

    public double dummyGetter () { return 0; }
    public void dummySetter (double d) {}
}