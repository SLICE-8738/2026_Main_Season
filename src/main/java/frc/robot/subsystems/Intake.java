// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private TalonFX extenderMotor;
  private TalonFX rotationMotor;

  private double targetPosition;

  /** Creates a new Intake. */
  public Intake() {

    extenderMotor = new TalonFX(0);
    rotationMotor = new TalonFX(0);

  }

  public void moveExtendMotor(double speed) {
    extenderMotor.set(speed);
  }

  public void moveRotationMotor(double speed) {
    rotationMotor.set(speed);
  }

  public void moveIntakeToPosition(double position) {
    targetPosition = position;

    PositionVoltage request = new PositionVoltage(0).withSlot(0);

    extenderMotor.setControl(request.withPosition(targetPosition));

  }

  public void speedMotorUp(double speed){
      
    VelocityVoltage request = new VelocityVoltage(0).withSlot(0);

    rotationMotor.setControl(request.withVelocity(speed));
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
