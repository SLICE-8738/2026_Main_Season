// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/** Add your docs here. */
public class LUTShooter {
    // Look up tables for angle, velocity, and distance 
    // Degrees, ft/s, ft
    public static final double[] ANGLE_LUT = {76.4806, 74.1888, 72.4699, 70.751, 69.6051, 67.8862, 66.1673, 65.0214, 63.3026, 62.1566, 61.0107, 59.8648, 58.1459, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573, 57.573};
    public static final double[] VELOCITY_LUT = {21.33, 21.78, 21.43, 21.3, 20.76, 20.92, 21.14, 21, 21.34, 21.34, 21.41, 21.53, 22.03, 21.9, 21.56, 21.34, 21.21, 21.14, 21.12, 21.14, 21.19, 21.26, 21.35, 21.46, 21.58, 21.71, 21.84, 21.99, 22.14, 22.29, 22.45, 22.61, 22.78, 22.95, 23.11, 23.28, 23.46, 23.63, 23.8, 23.98, 24.15, 24.33, 24.5, 24.68, 24.85, 25.03, 25.2, 25.38, 25.55, 25.72, 25.9, 26.07, 26.24, 26.41, 26.59, 26.76, 26.93, 27.1, 27.27, 27.44, 27.6, 27.77, 27.94, 28.1, 28.27, 28.43, 28.6, 28.76, 28.93, 29.09, 29.25, 29.41, 29.57, 29.73, 29.89, 30.05, 30.21, 30.37};
    public static final double[] DISTANCE_LUT = {1.5, 1.75, 2, 2.25, 2.5, 2.75, 3, 3.25, 3.5, 3.75, 4, 4.25, 4.5, 4.75, 5, 5.25, 5.5, 5.75, 6, 6.25, 6.5, 6.75, 7, 7.25, 7.5, 7.75, 8, 8.25, 8.5, 8.75, 9, 9.25, 9.5, 9.75, 10, 10.25, 10.5, 10.75, 11, 11.25, 11.5, 11.75, 12, 12.25, 12.5, 12.75, 13, 13.25, 13.5, 13.75, 14, 14.25, 14.5, 14.75, 15, 15.25, 15.5, 15.75, 16, 16.25, 16.5, 16.75, 17, 17.25, 17.5, 17.75, 18, 18.25, 18.5, 18.75, 19, 19.25, 19.5, 19.75, 20, 20.25, 20.5, 20.75};

    /**
     * Calculates the linear interpolation value at x
     * @param x_array The x values
     * @param y_array The y values
     * @param x The value to calculate at
     * @return The approximate y value
     */
    public static double linearInterpolate(double[] x_array, double[] y_array, double x){
        int count = x_array.length; // Get the length of an array (ideally should be the same)
        int i = 0;

        if (x < x_array[0]) return y_array[0]; // If the requested x value is smaller than the lowest x-value, return the lowest y-value.
        if (x > x_array[count - 1]) return y_array[count - 1]; // If the requested y value is larger than the highest x-value, return the highest y-value.

        for (i = 0; i < count - 1; i++){
            if (x_array[i + 1] > x) break; // Find where the requested x value is between
        }

        double dx = x_array[i + 1] - x_array[i]; // Get the change in x around the x value
        double dy = y_array[i + 1] - y_array[i]; // Get the change in y around the y value
        
        return y_array[i] + (x - x_array[i]) * (dy / dx); // Approximates the x-value using linear interpolation
    }

    /**
     * Finds the distance to the hub as a double. Returns -1 if not found.
     * @return Distance to the hub. 
     */
    public static double distanceFromHub(){
        double distance = -1; // Default value if limelight is not found.
        if (!LimelightHelpers.getTV("limelight-shooter")){ // If the limelight is not found...
            System.out.println("Couldn't find distance");
            return distance; // Return -1.
        }

        double offsetAngleVertical = LimelightHelpers.getTY("limelight-shooter"); // Otherwise, get the vertical offset.
        double angleToGoal = Math.toRadians(Constants.ShooterConstants.LIMELIGHT_ANGLE + offsetAngleVertical); // Add this to the limelight's angle.

        distance = (Constants.FieldConstants.HUB_APRILTAG_HEIGHT - Constants.ShooterConstants.LIMELIGHT_HEIGHT) / Math.tan(angleToGoal); // Find the distance using trig
        
        System.out.println("Distance is " + Units.metersToFeet(distance));
        return Math.abs(distance);
    }

    /**
     * Finds a valid (angle, velocity) pair for a certain distance from the hub.
     * @param hub_distance The distance from the hub.
     * @return An array of (degrees, rot/s)
     */
    public static double[] calculateShooter(double hub_distance){
        double[] result = {linearInterpolate(DISTANCE_LUT, ANGLE_LUT, Units.metersToFeet(hub_distance)), linearInterpolate(DISTANCE_LUT, VELOCITY_LUT, Units.metersToFeet(hub_distance))};
        result[1] = (result[1] * 0.3048) / (2 * Math.PI * Constants.ShooterConstants.FLYWHEEL_RADIUS); // Convert ft/s to m/s then rot/s
        System.out.println("Velocity is " + result[1]);
        return result;
    }
}
