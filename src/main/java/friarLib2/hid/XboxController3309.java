package friarLib2.hid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Represents an Xbox controller, but with built-in deadband application
 */
public class XboxController3309 extends XboxController {

    private double deadband;

    public XboxController3309 (final int port, double deadband) {
        super(port);

        this.deadband = deadband;
    }

    @Override
    public double getX (GenericHID.Hand hand) {
        return applyDeadband(super.getX(hand), deadband);
    }

    @Override
    public double getY (GenericHID.Hand hand) {
        return applyDeadband(super.getY(hand), deadband);
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
