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

    public static class Shooter {
        public static double flywheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return encoderTicksPer100ms / 2048.0 * 10 / 60;
        }

        public static double flywheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / flywheelEncoderTicksPer100msToRPM(1);
        }

        public static double armDegreesToEncoderTicks (double degrees) {
            return degrees / 360 / Constants.Shooter.ARM_GEAR_RATIO * 2048;
        }

        public static double armEncoderTicksToDegrees (double encoderTicks) {
            return encoderTicks / armDegreesToEncoderTicks(1);
        }
    }
}
