package friarLib2.hid;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Represents a joystick, but with built in deadband calculation
 */
public class Joystick3309 extends Joystick {

    public double deadband;

    public Joystick3309(int port, double deadband) {
        super(port);
        
        this.deadband = deadband;
    }   
    
    public double getXWithDeadband () {
        return applyDeadband(super.getX(), deadband);
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
