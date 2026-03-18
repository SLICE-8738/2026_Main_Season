// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static edu.wpi.first.units.Units.Rotation;

import java.util.concurrent.BlockingDeque;

import com.ctre.phoenix6.controls.PositionVoltage;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.LimelightHelpers;
import edu.wpi.first.math.MathUtil;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.drivetrain.Drivetrain;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class AutoAlign extends Command {

  private final Drivetrain m_drivesubsystem;
  private final XboxController m_controller;
  
  private PIDController rotationDriveController;
  private PIDController rotationAlignController; 


  public AutoAlign(Drivetrain drivetrain, XboxController controller) {

    m_drivesubsystem = drivetrain;
    m_controller = controller;

    rotationDriveController = new PIDController(Constants.AutoConstants.AUTO_ALIGN_KP, 
      Constants.AutoConstants.AUTO_ALIGN_KI, Constants.AutoConstants.AUTO_ALIGN_KD);

    rotationDriveController.enableContinuousInput(0, 360);

    rotationAlignController = new PIDController(Constants.AutoConstants.AUTO_ALIGN_HIDDEN_KP, Constants.AutoConstants.AUTO_ALIGN_HIDDEN_KI, Constants.AutoConstants.AUTO_ALIGN_HIDDEN_KD);

    rotationAlignController.enableContinuousInput(0, 360);
    rotationAlignController.setTolerance(2);

  }

  private boolean isInFrame() {
//    LimelightHelpers.setAlignIDs(10,25);
    return LimelightHelpers.getTV("limelight-shooter");
  }

  private double getError() {
  //  System.out.println(LimelightHelpers.getTX("limelight-shooter"));
    return LimelightHelpers.getTX("limelight-shooter");
  }

  public double getOutputDriving() {
    return rotationDriveController.calculate(getError(),0);
  }

  public double getOutputRotation() {
    return rotationAlignController.calculate(getError(), 0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  public boolean isOfftarget() {
    return Math.abs(getError()) > .5;
  }

  public boolean isOntarget() {
    return Math.abs(getError()) < .5;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {    
    SmartDashboard.putBoolean("Target", isInFrame()); 
    SmartDashboard.putNumber("TX", getError());
   // System.out.println("TV: " + n);

    if (isInFrame()) {
      if (isOfftarget()) {
        m_drivesubsystem.drive(new Transform2d(m_controller.getRawAxis(1),0,Rotation2d.fromRadians(getOutputDriving())), false, true);
      } else {
        m_drivesubsystem.drive(new Transform2d(), false, true);
      }
    }
     else {
       m_drivesubsystem.drive(new Transform2d(0,0, Rotation2d.fromRadians(getOutputRotation())), false, true);
     }

  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) { 
    m_drivesubsystem.drive(new Transform2d(), false, false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
 // LimelightHelpers.resetLimelightIDs();
  if (isInFrame()) {
    if (isOntarget()) {
      return true;
    }
    else {
      return false;
    }
  }
  else {
    return false;
  }
  }


  
}
