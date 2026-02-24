// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ShooterCalculations;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shoot extends SequentialCommandGroup {
  private Shooter mainShooter;
  /** Creates a new Shoot. */
  public Shoot(Shooter m_shooter) {
    mainShooter = m_shooter;
    double distance = ShooterCalculations.distanceToHub();
    double[] result = ShooterCalculations.calculateShooterTrajectory(distance);
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new PivotShooter(m_shooter, result[0]), new SpinFlywheels(m_shooter, result[1]));
  }
}
