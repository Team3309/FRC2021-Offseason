package friarLib2.vision.utility;

import friarLib2.vision.VisionTarget;

public class PixelToAngle {

    double resolutionX, resolutionY;
    double horizontalFOV, verticalFOV;
    double viewplaneWidth, viewplaneHeight;

    /**
     * Calculates the angle of a target to the camera.
     * 
     * <p>Y is up/down and X is right/left
     * 
     * <p>See https://docs.limelightvision.io/en/latest/theory.html#from-pixels-to-angles
     * for theory and equations
     */
    public PixelToAngle (double resolutionX, double resolutionY, double horizontalFOV, double verticalFOV) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.horizontalFOV = horizontalFOV;
        this.verticalFOV = verticalFOV;

        // Compute viewplane size
        viewplaneWidth = 2.0 * Math.tan(Math.toRadians(horizontalFOV) / 2);
        viewplaneHeight = 2.0 * Math.tan(Math.toRadians(verticalFOV) / 2);
    }

    public double calculateXAngle (VisionTarget target) {
        // Normalize pixel coordinates so that they range from -1 to 1, with (0, 0) being the center of the image
        double normalizedX = (target.getX() - (resolutionX / 2.0)) / resolutionX;

        // Compute viewport coordinates
        double x = (viewplaneWidth / 2.0) * normalizedX;

        // Compute angles
        return Math.atan2(1, x);
    }

    public double calculateYAngle (VisionTarget target) {
        // Normalize pixel coordinates so that they range from -1 to 1, with (0, 0) being the center of the image
        double normalizedY = ((resolutionY / 2.0) - target.getY()) / resolutionY;

        // Compute viewport coordinates
        double y = (viewplaneHeight / 2.0) * normalizedY;

        // Compute angles
        return Math.atan2(1, y);
    }
}
