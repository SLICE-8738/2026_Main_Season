package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.Constants;

public class SwerveModule {
 
    private int driveCanID;
    private int turnCanId;

    private int module_number;

    private TalonFX drivingMotor;
    private TalonFX turningMotor;

    public SwerveModule(int modNum, int driveCanID,int turnCanId ){
        module_number = modNum;
        this.driveCanID = driveCanID;
        this.turnCanId = turnCanId;

        drivingMotor = new TalonFX(driveCanID);
        turningMotor = new TalonFX(turnCanId);

        drivingMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.m_swerveDriveConfigs);
        turningMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.m_swerveTurnConfigs);

    }
    
}
