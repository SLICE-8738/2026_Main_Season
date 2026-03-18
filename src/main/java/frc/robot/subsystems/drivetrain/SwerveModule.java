package frc.robot.subsystems.Drivetrain;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.SwerveModuleIOInputsAutoLogged;
import frc.slicelibs.math.OnboardModuleState;

public class SwerveModule {

<<<<<<< HEAD
    private int module_number;

    private TalonFX drivingMotor;
    private TalonFX turningMotor;

    private CANcoder rotationAnalogEncoder;
  //  private AnalogInput rotation;
    private Encoder integratedEncoder;

    private double chassisAngularOffset;
    private Rotation2d angularOffset;


    private SwerveModuleState desiredState = new SwerveModuleState(0, new Rotation2d());

    public SwerveModule(int modNum, int driveCanID, int turnCanId,int encoderId, double chassisOffset, Rotation2d offset){
        module_number = modNum;
        this.driveCanID = driveCanID;
        this.turnCanId = turnCanId;
        angularOffset = offset;
        drivingMotor = new TalonFX(driveCanID);
        turningMotor = new TalonFX(turnCanId);
        
        //rotation  = new AnalogInput(encoderId);

        drivingMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.m_swerveDriveConfigs);
        turningMotor.getConfigurator().apply(Constants.CTRE_CONFIGS.m_swerveTurnConfigs);

        rotationAnalogEncoder = new CANcoder(encoderId);
        // rotationAnalogEncoder = new AnalogEncoder(encoderId);
        //turningMotor.setPosition(Math.abs(rotationAnalogEncoder.get() - (angularOffset.getDegrees())));
        //turningMotor.setPosition(turningMotor.getPosition().getValueAsDouble() - angularOffset.getDegrees());

        

        turningMotor.setPosition(rotationAnalogEncoder.getAbsolutePosition().getValueAsDouble()  - angularOffset.getRotations() /*Constants.DriveConstants.ANGLE_GEAR_RATIO*/ );
        //PositionVoltage initial = new PositionVoltage(rotationAnalogEncoder.get() * 360 - angularOffset.getDegrees());

        //turningMotor.setControl(initial.withPosition(Math.abs(rotationAnalogEncoder.get() * 360 - angularOffset.getDegrees())));
    
      //  desiredState.angle = Rotation2d.fromRotations(rotationAnalogEncoder.getAbsolutePosition().getValueAsDouble() /* Constants.DriveConstants.ANGLE_GEAR_RATIO */);

      //  System.out.println("Instantiating " + module_number + " swerve module");
=======
    private final SwerveModuleIO io;
    private SwerveModuleIOInputsAutoLogged inputs = new SwerveModuleIOInputsAutoLogged();
    public final int moduleNumber;
    private Rotation2d lastAngle;
    private SwerveModuleState targetState = new SwerveModuleState();
>>>>>>> Code-V2

    public SwerveModule(SwerveModuleIO io, int moduleNumber) {
        this.io = io;
        this.moduleNumber = moduleNumber;
        lastAngle = getState().angle;
    }

    public void updateInputs() {
        io.updateInputs(inputs);
        Logger.processInputs("Drivetrain/Module" + moduleNumber, inputs);
    }

    public void runSetpoint(SwerveModuleState state, boolean isOpenLoop) {
        state = OnboardModuleState.optimize(state, getState().angle);
        targetState = state;
        setAngle(state);
        setSpeed(state, isOpenLoop);
    }

    public void runCharacterization(double volts) {
        io.setDriveVoltage(volts);
    }

    public void runDutyCycle(double drivePercentOutput, double anglePercentOutput) {
        io.runDriveDutyCycle(drivePercentOutput);
        io.runAngleDutyCycle(anglePercentOutput);
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            io.runDriveDutyCycle(desiredState.speedMetersPerSecond / Constants.DriveConstants.MAX_LINEAR_VELOCITY);
        } else {
            io.setDriveVelocity(desiredState.speedMetersPerSecond);
        }
    }

    private void setAngle(SwerveModuleState desiredState) {
        // Suppress jitter when nearly stopped
        Rotation2d angle = (Math
                .abs(desiredState.speedMetersPerSecond) <= (Constants.DriveConstants.MAX_LINEAR_VELOCITY * 0.01))
                        ? lastAngle
                        : desiredState.angle;
        io.setAnglePosition(angle.getDegrees());
        lastAngle = angle;
    }

    private Rotation2d getIntegratedAngle() {
        return inputs.integratedAnglePosition;
    }

    public Rotation2d getAbsoluteAngle() {
        return inputs.absoluteAnglePosition;
    }

    public void resetToAbsolute() {
        io.resetToAbsolute();
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(inputs.driveVelocityMetersPerSec, getIntegratedAngle());
    }

    public SwerveModuleState getTargetState() {
        return targetState;
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(inputs.drivePositionMeters, getIntegratedAngle());
    }

    public double getDriveAcceleration() {
        return inputs.driveAccelerationMetersPerSecSquared;
    }

    public double getDriveVoltage() {
        return inputs.driveAppliedVolts;
    }

    public double getDriveOutputCurrent() {
        return inputs.driveCurrentAmps;
    }
}