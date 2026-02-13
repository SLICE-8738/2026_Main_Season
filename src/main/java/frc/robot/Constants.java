// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import frc.slicelibs.configs.CTREConfigs;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static final CTREConfigs CTRE_CONFIGS = new CTREConfigs();

  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }
  
  public static class ShooterConstants {
    
    //TODO: get actual motor ids
    public static final int PIVOT_MOTOR_ID = 0;
    public static final int LEFT_SHOOTER_MOTOR_ID = 0;
    public static final int RIGHT_SHOOTER_MOTOR_ID = 0;

    //TODO: tune PIDs
    public static final double FLYWHEEL_KP = 0.0;
    public static final double FLYWHEEL_KI = 0.0;
    public static final double FLYWHEEL_KD = 0.0;

    public static final double SHOOTER_STOW = 0.0; // The angle at which the shooter is considered stowed


    public static final double FLYWHEEL_RPM_ACCEPTABLE_ERROR = 2; // The maximum error allowed in the flywheel RPM
    public static final double VERTICAL_AIM_ACCEPTABLE_ERROR = 2; // The maximum error allowed in the shooter angle vertically, in degrees
    public static final double HORIZONTAL_AIM_ACCEPTABLE_ERROR = 2; // The maximum error allowed in the shooter angle horizontally (controlled by drivetrain). in degrees
    public static final double MAXIMUM_SHOOTING_DRIVETRAIN_SPEED = 0.1; // The maximum speed that the drivetrain can move at and shoot

  }

  public static class FieldConstants {

    public static final Pose2d  CENTER_HUB = new Pose2d();

  }

}
