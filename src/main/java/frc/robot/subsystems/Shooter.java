// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import frc.robot.ShooterCalculations;
import frc.robot.LimelightHelpers.PoseEstimate;

public class Shooter extends SubsystemBase {

  private TalonFX pivotMotor, leftShooterMotor, rightShooterMotor;
  //private Encoder pivotEncoder, leftEncoder, rightEncoder;

  private double targetSpeed, targetPosition;

  /** Creates a new Shooter. */
  public Shooter() {
    // Define the motors for pivoting the shooter and the flywheels.
    pivotMotor = new TalonFX(Constants.ShooterConstants.PIVOT_MOTOR_ID);
    leftShooterMotor = new TalonFX(Constants.ShooterConstants.LEFT_SHOOTER_MOTOR_ID);
    rightShooterMotor = new TalonFX(Constants.ShooterConstants.RIGHT_SHOOTER_MOTOR_ID);

    // Set the motor configs.
    leftShooterMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.shooterConfigs);
    rightShooterMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.shooterConfigs);
  }

  /**
   * Sets the flywheels to spin at a certain speed.
   * @param speed The speed to set (a value between -1.0 and 1.0)
   */
  public void spinFlywheels(double speed){
    leftShooterMotor.set(speed);
    rightShooterMotor.set(speed);
  }

  /**
   * Sets the pivot to a certain speed.
   * @param speed The speed to set (a value between -1.0 and 1.0)
   */
  public void pivotShooter(double speed){
    pivotMotor.set(speed);
  }

  /**
   * Accelerates the flywheels to a certain speed.
   * @param speed The speed to spin at in rotations per second.
   */
  public void speedUpFlywheels(double speed){
    targetSpeed = speed;
    
    VelocityVoltage request = new VelocityVoltage(0).withSlot(0);

    leftShooterMotor.setControl(request.withVelocity(speed));
    rightShooterMotor.setControl(request.withAcceleration(speed));

  }

  /**
   * Set the shooter's pivot motor to a specific angle.
   * @param position The position to set in rotations.
   */
  public void pivotShooterToPosition(double position){
    targetPosition = position;

    PositionVoltage request = new PositionVoltage(position); 

    pivotMotor.setControl(request.withPosition(position)); 

  }

  /**
   * Check if the motor is at a specific speed.
   * @param error How much error is allowed to be considered "at speed."
   * @return Returns whether or not the motor is at speed.
   */
  public boolean atTargetSpeed(double error){
    double currentSpeed = leftShooterMotor.getVelocity().getValueAsDouble();
    currentSpeed += rightShooterMotor.getVelocity().getValueAsDouble() / 2;
    if(Math.abs(targetSpeed - currentSpeed) >= error){
      return true;
    }
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
