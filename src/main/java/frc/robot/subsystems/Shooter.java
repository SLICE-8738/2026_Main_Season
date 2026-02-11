// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

  private TalonFX pivotMotor, leftShooterMotor, rightShooterMotor;
  //private Encoder pivotEncoder, leftEncoder, rightEncoder;

  /** Creates a new Shooter. */
  public Shooter() {
    pivotMotor = new TalonFX(Constants.ShooterConstants.PIVOT_MOTOR_ID);
    leftShooterMotor = new TalonFX(Constants.ShooterConstants.LEFT_SHOOTER_MOTOR_ID);
    rightShooterMotor = new TalonFX(Constants.ShooterConstants.RIGHT_SHOOTER_MOTOR_ID);

  }

  public void spinFlywheels(double speed){
    leftShooterMotor.set(speed);
    rightShooterMotor.set(speed);
  }

  public void pivotShooter(double speed){
    pivotMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
