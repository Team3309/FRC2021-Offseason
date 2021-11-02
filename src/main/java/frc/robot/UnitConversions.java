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
            return (meters / wheelCircumferenceMeters) * (Constants.Drive.GEAR_RATIO) * 2048.0;
        }

        public static double encoderTicksToMeters (double encoderTicks) {
            return encoderTicks / metersToEncoderTicks(1);
        }

        public static double MPSToEncoderTicksPer100ms (double metersPerSecond) {
            return metersToEncoderTicks(metersPerSecond) * (1.0/10.0);
        }

        public static double EncoderTicksPer100mstoMPS (double encoderTicksPer100ms) {
            return encoderTicksPer100ms / MPSToEncoderTicksPer100ms(1);
        }
    }

    public static class Shooter {
        public static double mainFlywheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return (encoderTicksPer100ms / 2048.0 * Constants.Shooter.MAIN_FLYWHEEL_GEAR_RATIO) * 10 / 60;
        }

        public static double mainFlywheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / mainFlywheelEncoderTicksPer100msToRPM(1);
        }

        public static double outerFlywheelEncoderTicksPer100msToRPM (double encoderTicksPer100ms) {
            return (encoderTicksPer100ms / 2048.0 * Constants.Shooter.OUTER_FLYWHEEL_GEAR_RATIO) * 10 / 60;
        }

        public static double outerFlywheelRPMToEncoderTicksPer100ms (double RPM) {
            return RPM / outerFlywheelEncoderTicksPer100msToRPM(1);
        }
    }

    public static class Arm {
        public static double armDegreesToEncoderTicks (double degrees) {
            return degrees / 360.0 * Constants.Arm.ARM_GEAR_RATIO * 2048.0;
        }

        public static double armEncoderTicksToDegrees (double encoderTicks) {
            return encoderTicks / armDegreesToEncoderTicks(1);
        }
    }
}
