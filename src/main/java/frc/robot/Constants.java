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
    /**
     * Constants for the drivetrain
     */
    public final static class Drive {
        /******** Motor CAN ID's ********/
        public static final int LEFT_MASTER_ID = 0;
        public static final int LEFT_SLAVE_ID = 1;

        public static final int RIGHT_MASTER_ID = 2;
        public static final int RIGHT_SLAVE_ID = 3;

        /******** PID Constants ********/
        public static final PIDParameters PIDConstants = new PIDParameters(0.1, 0, 0);
    }
}
