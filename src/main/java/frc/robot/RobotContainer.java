package frc.robot;

import frc.robot.Constants.OIConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.Indexer.SpinStageOne;
import frc.robot.commands.Indexer.SpinStageTwo;
import frc.robot.commands.Intake.OscillateIntake;
import frc.robot.commands.Intake.RetractIntakeTimed;
import frc.robot.commands.Intake.RotateIntake;
import frc.robot.commands.Intake.RotateIntakeManual;
import frc.robot.commands.shooter.ManualShoot;
import frc.robot.commands.shooter.ReadyShooter;
import frc.robot.commands.shooter.Shoot;
import frc.robot.commands.shooter.ShootAndIndex;
import frc.robot.commands.shooter.ShootAtHub;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Drivetrain.Drivetrain;
import frc.robot.subsystems.Drivetrain.RealSwerveModuleIO;
import frc.robot.subsystems.Drivetrain.SimSwerveModuleIO;
import frc.robot.subsystems.Drivetrain.SwerveModuleIO;

public class RobotContainer {

  /*  Controllers */
  private final XboxController m_driverController = Buttons.controller1;
  //private final XboxController m_operatorController = Buttons.controller2;

  /* Drive Subsystem */
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  /*  Intake Subsystem & Commands */
  private final Intake m_Intake =  new Intake();

  private final MoveIntakeManual m_MoveIntakeManual = new MoveIntakeManual(m_Intake, m_driverController);
  private final RotateIntake m_RotateIntakeManual = new RotateIntake(m_Intake, 0.25);

  /* Indexer Subsystem & Commands */

  private final Indexer m_Indexer = new Indexer();
  
  // TODO : POSITIVE IS CORRECT WAY
  private final SpinStageOneManual m_IndexerStageOneManual = new SpinStageOneManual(m_Indexer, m_driverController);
  private final SpinStageTwoManual m_IndexerStageTwoManual = new SpinStageTwoManual(m_Indexer, m_driverController); // TODO change axis
  private final SpinStageOne m_spinStageOne = new SpinStageOne(m_Indexer, 0.2); // TODO: Set correct speed
  private final SpinStageTwo m_spinStageTwo = new SpinStageTwo(m_Indexer, 0.2); // TODO: Set correct speed

  /* Shooter Subsystem & Commands */

  private final Shooter m_Shooter = new Shooter();

  // TODO fix up shooter calculations; SIMPLIFY
  //private final ManualShoot m_ManualShoot = new ManualShoot(m_Shooter, m_driverController);
  //private final ReadyShooter m_ReadyShooter = new ReadyShooter(m_Shooter);
  //private final Shoot m_Shoot = new Shoot(m_Shooter);

  private final ShootAtHub m_ShootAtHub = new ShootAtHub(m_Shooter, m_Indexer);

  //private final SendableChooser<Command> m_chooser = new SendableChooser<>();


  /* Sequence & Parallel Commands */
  private final IntakeFuelTimed m_IntakeFuelTimed = new IntakeFuelTimed(m_Intake, m_Indexer);
  private final RetractIntakeTimed m_RetractIntakeTimed = new RetractIntakeTimed(m_Intake, m_Indexer);
  private final OscillateIntake m_OscillateIntake = new OscillateIntake(m_Intake, m_Indexer);
  // TODO get positional Intake stuff working
  //private final IntakeFuel m_IntakeFuel = new IntakeFuel(m_Intake, m_Indexer);

  //private final ShootAndIndex m_shootAndIndex = new ShootAndIndex(m_Shooter, m_Indexer);

  /* Autos */
  // TODO improve auto
  //Command autoCommand = new ShootAtHub(m_Shooter, m_Indexer);
    
  //new ParallelCommandGroup(m_ShootAtHub);



  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    /*
    * TODO figure out how to ready shooter 4 secs before active shift
    Trigger preFire = new Trigger(() -> m_Shooter.isHubAlmostActive());
    preFire.onTrue(m_ReadyShooter);
    */
   // m_chooser.setDefaultOption("Simple Auto", autoCommand);
   // m_ResetGyro.onTrue(new RunCommand(() -> m_robotDrive.resetGyro(), m_robotDrive));

    

    // Configure the trigger bindings
    configureBindings();

    // Configure default commands
   m_robotDrive.setDefaultCommand(
    //     // The left stick controls translation of the robot.
    //     // Turning is controlled by the X axis of the right stick.
          new RunCommand(  
            () -> m_robotDrive.drive(
              MathUtil.applyDeadband(m_driverController.getRawAxis(1), OIConstants.kDriveDeadband), //drive
              MathUtil.applyDeadband(m_driverController.getRawAxis(0), OIConstants.kDriveDeadband),
              MathUtil.applyDeadband(m_driverController.getRawAxis(4), OIConstants.kDriveDeadband), //rotation
              true),
            m_robotDrive)
  );

      
          


            

  //new RunCommand( () -> m_robotDrive.setDutyCycle(0,.1),
  //    m_robotDrive));

  //m_Shooter.setDefaultCommand(m_Shoot);

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    //Buttons.controller1_leftBumper.whileTrue(m_IntakeFuelTimed); TODO uncomment if needed
    Buttons.controller1_RightTrigger.whileTrue(m_ShootAtHub);
    Buttons.controller1_rightBumper.whileTrue(m_ShootAtHub);

    Buttons.controller1_AButton.whileTrue(m_IntakeFuelTimed);
    Buttons.controller1_BButton.whileTrue(m_RetractIntakeTimed);

    Buttons.controller1_minusButton.onTrue(new InstantCommand( ()->m_robotDrive.resetGyro(), m_robotDrive) );

    //Buttons.controller1_XButton.whileTrue(m_OscillateIntake); TODO reimplement
    //m_chooser.getSelected();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // TODO: improve autos
    return null; //autoCommand;
  }
}