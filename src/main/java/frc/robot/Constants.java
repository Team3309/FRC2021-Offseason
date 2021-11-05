// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.util.FiringSolution;
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
    public static final double JOYSTICK_DEADBAND = 0.1;
    public static final double XBOX_DEADBAND = 0.05;

    /**
     * Constants of the climber
     */
    public static final class Climber {
        /******** Motor CAN ID's ********/
        public static final int WINCH_MOTOR_ID = 2;

        /******** Pneumatics ********/
        public static final int PCM_PORT_1 = 7;
        public static final int PCM_PORT_2 = 1;

        /******** Tuning Constatnts ********/
        public static final double WINCH_POWER = .4;
    }

    /**
     * Constants for the arm
     */
    public final static class Arm {
        /******** Motor CAN ID's ********/
        public static final int ARM_MOTOR_ID = 1;

        /******** PID Constants ********/
        public static final PIDParameters ARM_PID = new PIDParameters(0.2, 2.54972071e-05, 10.0, "Arm PID");

        /******** Physical Constants ********/
        public static final int LIMIT_SWITCH_PORT = 0; // Digital input port on the RoboRIO that the arm's limit switch is connected too
        public static final double LIMIT_SWITCH_ANGLE = 0; // The angle that the arm is at when the limit switch is triggered

        public static final double ARM_GEAR_RATIO = 45.0 / 1.0;

        /******** Arm Location Presets ********/
        public static final double START_CONFIG_ANGLE = 0; // Angle that arm is in when robot is turned on
        public static final double STOWED_ANGLE = 0; // Angle where robot fits under the Wheel of Fortune (WOF)
        public static final double INTAKE_ANGLE = 0; // Angle required for intaking power cells
        public static final double VISION_SEEK_ANGLE = 100; // Angle for finding targets

        /******** Arm Motion Constraints ********/
        public static final double MAX_VELOCITY_DEGREES_PER_SEC = 700;
        public static final double MAX_ACCLERATION_DEGREES_PER_SEC_SQUARED = 400;
    }

    /**
     * Constants for the drivetrain
     */
    public final static class Drive {
        /******** Motor CAN ID's ********/
        public static final int LEFT_MASTER_ID = 4;
        public static final int LEFT_SLAVE_ID = 16;

        public static final int RIGHT_MASTER_ID = 5;
        public static final int RIGHT_SLAVE_ID = 19;

        /******** PID Constants ********/
        public static final PIDParameters WHEEL_PID_CONSTANTS = new PIDParameters(0.3, 0.0001, .6, "Drive wheel PID");
        public static final PIDController ROTATION_PID_CONTROLLER = new PIDController(0.02, 0, 0.0005);

        /******** Physical Constants ********/
        public static final double GEAR_RATIO = 10.7;
        public static final double WHEEL_DIAMETER = 6; // Inches
        public static final double DRIVE_BASE_WIDTH = 0.5969; // Meters; 25.5 inches
    }

    /**
     * Constants for the intake
     */
    public final static class Intake {
        /******** Motor CAN ID's ********/
        public static final int MOTOR_ID = 3;

        /******** Pneumatics ********/
        public static final int FIRST_PCM_PORT = 4;
        public static final int SECOND_PCM_PORT = 0;

        /******** Tuning Constants ********/
        public static final double MOTOR_POWER = 1;
        public static final double OUTTAKE_POWER = .75;
    }

    /**
     * Constants for the Serializer
     */
    public final static class Serializer {
        /******** Motor CAN ID's ********/
        public static final int BRUSH_MOTOR_ID = 8;
        public static final int ROLLER_MOTOR_ID = 9;
        public static final int ACCLERATOR_MOTOR_ID = 15;

        /******** Tuning Constants ********/
        public static final double BRUSH_POWER = .75;
        public static final double ACCLERATOR_POWER = 1.0;
        public static final double ROLLER_POWER = 1.0;
        public static final double BRUSH_REVERSE_POWER = .75;
        public static final double ROLLER_REVERSE_POWER = 1.0;
    }

    /**
     * Constants for the Shooter
     */
    public final static class Shooter {
        /******** Motor CAN ID's ********/
        public static final int MAIN_FLYWHEEL_MASTER_ID = 6;
        public static final int MAIN_FLYWHEEL_SLAVE_ID = 18;
        public static final int OUTER_FLYWHEEL_MASTER_ID = 7;
        public static final int OUTER_FLYWHEEL_SLAVE_ID = 17;

        /******** PID Constants ********/
        public static final PIDParameters MAIN_FLYWHEEL_PID = new PIDParameters(1.3, 0.00005, 5, "Main flywheel PID");
        public static final PIDParameters OUTER_FLYWHEEL_PID = new PIDParameters(0.5, 0.00015, 6, "Outer flywheel PID");

        /******** PID Constants ********/
        public static final double MAIN_FLYWHEEL_GEAR_RATIO = 6.0 / 5.0;
        public static final double OUTER_FLYWHEEL_GEAR_RATIO = 1.0 / 1.0;

        /******** Tuning Constants ********/
        public static final int MAIN_FLYWHEEL_SPEED_TOLERANCE = 250; // Only shoot powercells if flywheels are within this range of their target speed
        public static final double MAIN_FLYWHEEL_ROC_TOLERANCE = 5000; // Only shoot powercells if the flywheels' speeds' rate of change is below this value
        public static final int OUTER_FLYWHEEL_SPEED_TOLERANCE = 250;
        public static final double OUTER_FLYWHEEL_ROC_TOLERANCE = 5000;

        public static final double MAIN_FLYWHEEL_SHOOTING_RPM = 5500;
        public static final double OUTER_FLYWHEEL_SHOOTING_RPM = 3000;

        /******** Firing soulutions ********/
        public static final FiringSolution LOB_INTO_GOAL = new FiringSolution(5500, 3000, 20);
        public static final FiringSolution SHOOT_FROM_STARTING_LINE = new FiringSolution(100);

        /**
         * A 2D array for tuning the shooter.
         * 
         * <p>The first value in each sub-array is the distance in meters from the goal.
         * The second one is the angle of the shooter, found through manual tuning, to
         * shoot into the goal at that distance.
         * 
         * <p>ShooterSubsystem computes a linear regression to fill in the relationship
         * between distance and angle.
         */
        public static double[][] AIM_REGRESSION_DATA = {
            {1.5, 200},
            //{2, 11},
            {3, 140}
        };
    }
}
