// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.slicelibs.configs;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

import frc.robot.Constants;

import frc.robot.Constants;

/** Add your docs here. */
public class CTREConfigs {

    /* Initiating the different TalonFX configurators */
    public final TalonFXConfiguration m_swerveDriveConfigs = new TalonFXConfiguration();
    public final TalonFXConfiguration m_swerveTurnConfigs = new TalonFXConfiguration();

    public CTREConfigs(){


        //////////////////////
        /// Swerve Configs ///
        //////////////////////
        
        var swervePIDs = m_swerveDriveConfigs.Slot0;
        swervePIDs.kP = Constants.DriveConstants.DRIVE_KP;
        swervePIDs.kI = Constants.DriveConstants.DRIVE_KI;
        swervePIDs.kD = Constants.DriveConstants.DRIVE_KD;

    }
    

}
