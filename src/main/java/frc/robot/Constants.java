// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import friarLib2.utility.PIDParameters;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    /*
     * Global constants that do not belong to a subsystem
     */
    public static final int PCM_CAN_ID = 0;
    public static final double JOYSTICK_DEADBAND = 0.05;
    public static final double XBOX_DEADBAND = 0.05;

    /**
     * Constants of the climber
     */
    public static final class Climber {
        /******** Motor CAN ID's ********/
        public static final int WINCH_MOTOR_ID = 13;

        /******** Pneumatics ********/
        public static final int PCM_PORT = 3;

        /******** Tuning Constatnts ********/
        public static final double WINCH_POWER = .4;
    }

    /**
     * Constants for the arm
     */
    public final static class Arm {
        /******** Motor CAN ID's ********/
        public static final int ARM_MOTOR_ID = 3;

        /******** PID Constants ********/
        public static final PIDParameters ARM_PID = new PIDParameters(0.2, 2.54972071e-05, 10.0); // Gains taken from 2020 robot

        /******** Physical Constants ********/
        public static final int LIMIT_SWITCH_PORT = 0; // Digital input port on the RoboRIO that the arm's limit switch is connected too
        public static final double LIMIT_SWITCH_ANGLE = -10; // The angle that the arm is at when the limit switch is triggered

        public static final double ARM_GEAR_RATIO = 45.0 / 1;

        /******** Arm Location Presets ********/
        public static final double START_CONFIG_ANGLE = 30; // Angle where robot fits in starting config
        public static final double STOWED_ANGLE = -5; // Angle where robot fits under the Wheel of Fortune (WOF)
        public static final double INTAKE_ANGLE = -10; // Angle required for intaking power cells
    }

    /**
     * Constants for the drivetrain
     */
    public final static class Drive {
        /******** Motor CAN ID's ********/
        public static final int LEFT_MASTER_ID = 4;
        public static final int LEFT_SLAVE_ID = 16;

        public static final int RIGHT_MASTER_ID = 15;
        public static final int RIGHT_SLAVE_ID = 1;

        /******** PID Constants ********/
        public static final PIDParameters PID_CONSTANTS = new PIDParameters(0.02, 0.00015, 0.002);

        /******** Physical Constants ********/
        public static final double GEAR_RATIO = 10.7;
        public static final double WHEEL_DIAMETER = 6; // Inches
    }

    /**
     * Constants for the intake
     */
    public final static class Intake {
        /******** Motor CAN ID's ********/
        public static final int MOTOR_ID = 20;

        /******** Pneumatics ********/
        public static final int FIRST_PCM_PORT = 1;
        public static final int SECOND_PCM_PORT = 2;

        /******** Tuning Constants ********/
        public static final double MOTOR_POWER = 1.0;
    }

    /**
     * Constants for the Serializer
     */
    public final static class Serializer {
        /******** Motor CAN ID's ********/
        public static final int BRUSH_MOTOR_ID = 5;
        public static final int ACCLERATOR_MOTOR_ID = 6;

        /******** Tuning Constants ********/
        public static final double BRUSH_POWER = 1.0;
        public static final double ACCLERATOR_POWER = 1.0;
    }

    /**
     * Constants for the Shooter
     */
    public final static class Shooter {
        /******** Motor CAN ID's ********/
        public static final int FLYWHEEL_MOTOR_ID = 7;

        /******** PID Constants ********/
        public static final PIDParameters FLYWHEEL_PID = new PIDParameters(0.1, 0, 0);

        /******** Tuning Constants ********/
        public static final int FLYWHEEL_SPEED_TOLERANCE = 50; // Only shoot powercells if flywheels are within this range of their target speed
    }
}
