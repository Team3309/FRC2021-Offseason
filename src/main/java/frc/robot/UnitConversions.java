package frc.robot;

import edu.wpi.first.wpilibj.util.Units;

/** 
 * Helper class that contains conversions from real life units into encoder
 * counts and vice-versa
 */
public class UnitConversions {
    public static class Drive {
        private static double wheelCircumferenceMeters = Units.inchesToMeters(Constants.Drive.WHEEL_DIAMETER) * Math.PI;

        public static double metersToEncoderTicks (double meters) {
            return (meters / wheelCircumferenceMeters) / (Constants.Drive.GEAR_RATIO) * 2048;
        }

        public static double MPSToEncoderTicksPer100ms (double metersPerSecond) {
            return metersPerSecond * metersToEncoderTicks(metersPerSecond) * (1.0/10.0);
        }
    }
}
