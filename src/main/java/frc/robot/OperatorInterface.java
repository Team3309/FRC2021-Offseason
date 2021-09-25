package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import friarLib2.hid.Joystick3309;
import friarLib2.hid.XboxController3309;

/**
 * Contains static references to controllers and joysticks
 */
public class OperatorInterface
{
    // Joysticks
    public static Joystick3309 DriverLeft = new Joystick3309(0, Constants.JOYSTICK_DEADBAND);
    public static Joystick3309 DriverRight = new Joystick3309(1, Constants.JOYSTICK_DEADBAND);

    public static ClusterGroup leftStickLeftCluster = new ClusterGroup(DriverLeft, GenericHID.Hand.kLeft);
    public static ClusterGroup leftStickRightCluster = new ClusterGroup(DriverLeft, GenericHID.Hand.kLeft);
    public static ClusterGroup rightStickLeftCluster = new ClusterGroup(DriverRight, GenericHID.Hand.kRight);
    public static ClusterGroup rightStickRightCluster = new ClusterGroup(DriverRight, GenericHID.Hand.kRight);

    // Xbox controller
    public static XboxController3309 OperatorController = new XboxController3309(2, Constants.XBOX_DEADBAND);


    /**
     * Represents one of the six-button clusters on either side of a Joystick
     * 
     * Triggers when any of the buttons in that cluster are pressed
     */
    public static class ClusterGroup extends Trigger {

        Joystick stick;
        GenericHID.Hand side;

        public static final int LEFT_CLUSTER_1_ID = 11;
        public static final int LEFT_CLUSTER_2_ID = 12;
        public static final int LEFT_CLUSTER_3_ID = 13;
        public static final int LEFT_CLUSTER_4_ID = 14;
        public static final int LEFT_CLUSTER_5_ID = 15;
        public static final int LEFT_CLUSTER_6_ID = 16;
        public static final int RIGHT_CLUSTER_1_ID = 5;
        public static final int RIGHT_CLUSTER_2_ID = 6;
        public static final int RIGHT_CLUSTER_3_ID = 7;
        public static final int RIGHT_CLUSTER_4_ID = 8;
        public static final int RIGHT_CLUSTER_5_ID = 9;
        public static final int RIGHT_CLUSTER_6_ID = 10;

        ClusterGroup(Joystick stick, GenericHID.Hand side) {
            this.stick = stick;
            this.side = side;
        }

        @Override
        public boolean get() {
            if (side == GenericHID.Hand.kLeft) {
                return stick.getRawButton(LEFT_CLUSTER_1_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_2_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_3_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_4_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_5_ID) ||
                        stick.getRawButton(LEFT_CLUSTER_6_ID);
            } else {
                return stick.getRawButton(RIGHT_CLUSTER_1_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_2_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_3_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_4_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_5_ID) ||
                        stick.getRawButton(RIGHT_CLUSTER_6_ID);
            }
        }
    }
}