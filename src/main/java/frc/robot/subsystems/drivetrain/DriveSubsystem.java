// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSubsystem extends SubsystemBase {

  //TODO Find Chassis Offsets and Angular Offsets(offset)
  private final SwerveModule m_frontLeftModule = new SwerveModule(
   0,
   Constants.DriveConstants.FRONT_LEFT_DRIVE_ID, 
   Constants.DriveConstants.FRONT_LEFT_TURN_ID, 
   0, 
   null);

  //TODO Find Chassis Offsets and Angular Offsets(offset)
  private final SwerveModule m_frontRightModule = new SwerveModule(
    1,
    Constants.DriveConstants.FRONT_RIGHT_DRIVE_ID,
    Constants.DriveConstants.FRONT_RIGHT_TURN_ID,
    0, 
    null);

  //TODO Find Chassis Offsets and Angular Offsets(offset)
  private final SwerveModule m_backLeftModule = new SwerveModule(
    2, 
    Constants.DriveConstants.BACK_LEFT_DRIVE_ID, 
    Constants.DriveConstants.BACK_LEFT_TURN_ID, 
    0, 
    null);

  //TODO Find Chassis Offsets and Angular Offsets(offset)
  private final SwerveModule m_backRightModule = new SwerveModule(3, 
  Constants.DriveConstants.BACK_RIGHT_DRIVE_ID, 
  Constants.DriveConstants.BACK_RIGHT_TURN_ID, 
  0, 
  null);

  //TODO add/fix Gyro
  private Pigeon2 m_gyro = new Pigeon2(0); //TODO find gyro Device ID

  //TODO add odometry method after adding drivetrain kinematics to the Constants file


  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
