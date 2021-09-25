package friarLib2.hid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class Joystick3309 extends Joystick {

    private double deadband;

    public Joystick3309 (final int port, double deadband) {
        super(port);

        this.deadband = deadband;
    }
    
    public double getXWithDeadband (GenericHID.Hand hand) {
        return applyDeadband(super.getX(hand), deadband);
    }

    public double getXWithDeadband () {
        return applyDeadband(super.getX(), deadband);
    }

    public double getYWithDeadband (GenericHID.Hand hand) {
        return applyDeadband(super.getY(hand), deadband);
    }

    public double getYWithDeadband () {
        return applyDeadband(super.getY(), deadband);
    }

    /**
     * Return zero if the absolute value of joystickValue is less than deadband, else, return joystickValue.
     * Ueseful for ensuring a zero value when a joystick is released.
     * 
     * @param joystickValue the value of the joystick axis
     * @param deadband the deadband to apply
     * @return the adjusted joystickValue
     */
    public static double applyDeadband (double joystickValue, double deadband) {
        return (Math.abs(joystickValue) > deadband) ? joystickValue : 0;
    }
}