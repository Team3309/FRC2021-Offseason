package frc.robot;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.util.Units;
import frc.robot.subsystems.ArmSubsystem;
import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.VisionCamera;
import friarLib2.vision.utility.PixelToAngle;

/**
 * Container for the vision systems
 */
public class Vision {
    public static VisionCamera mainCamera = new PhotonCameraWrapper("gloworm");

    /**
     * @return the distance in meters from the target
     */
    public static double getDistanceFromTarget (ArmSubsystem arm) {
        if (mainCamera instanceof PhotonCamera) {
            return mainCamera.getBestTarget().getPose().getX();
        } else {
            // Constants for Limelight
            PixelToAngle p = new PixelToAngle(320, 240, 54, 41);

            // Magic equations are based on the physical dimensions of the robot
            double heightOfCamera = 14.93 + (21.297 * Math.toRadians(Math.sin(32.90)));
            double angleOfCamera = (90 - 15.47) - arm.getArmPosition();

            // From dimensions of field
            final double HEIGHT_OF_TARGET = 97.5 - (30.0 / 4.0);

            // See https://docs.limelightvision.io/en/latest/cs_estimating_distance.html
            return Units.inchesToMeters(
                (HEIGHT_OF_TARGET - heightOfCamera) / Math.tan(Math.toRadians(angleOfCamera) + Math.toRadians(p.calculateYAngle(mainCamera.getBestTarget())))
            );
        }
    }
}