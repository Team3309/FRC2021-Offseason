package frc.robot;

import friarLib2.vision.PhotonCameraWrapper;
import friarLib2.vision.VisionCamera;

/**
 * Container for the vision systems
 */
public class Vision {
    public static VisionCamera mainCamera = new PhotonCameraWrapper("");

    /**
     * @return the distance in meters from the target
     */
    public static double getDistanceFromTarget () {
        return mainCamera.getBestTarget().getPose().getX();
    }
}