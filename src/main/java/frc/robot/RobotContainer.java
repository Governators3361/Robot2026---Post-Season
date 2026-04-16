// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.NetworkingSubsystem;
import frc.robot.subsystems.SubsystemRegistry;
import frc.robot.subsystems.TurboSubsystem;
import frc.robot.subsystems.WiggleSubsystem;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.shoot.BallFondlerSubsystem;
import frc.robot.subsystems.shoot.ShootControlSubsystem;
import frc.robot.commands.CommandXStop;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.CommandDecrement;
import frc.robot.commands.CommandIncrement;
import frc.robot.commands.CommandIntake;
import frc.robot.commands.CommandReverseIntake;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final BallFondlerSubsystem ballFondlerSubsystem = new BallFondlerSubsystem();
  public final NetworkingSubsystem networkingSubsystem;
  public final TurboSubsystem turboSubsystem;
  public final ShootControlSubsystem shootControlSubsystem;
  public final WiggleSubsystem wiggleSubsystem;

  public final CommandXboxController m_driverController = new CommandXboxController(
      OIConstants.kDriverControllerPort); // kDriverControllerPort is int = 0

  public DriveSubsystem m_robotDrive;

  public final CommandXboxController m_shooterController = new CommandXboxController(
      OIConstants.kShootControllerPort);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public void configureAutoCommands() {
    // create named commands.
    // must be done before anything else, or pathplanner breaks

    NamedCommands.registerCommand("spoolShooter", shootControlSubsystem.spoolShooter());
    NamedCommands.registerCommand("stopShoot", shootControlSubsystem.stopShooter());
    NamedCommands.registerCommand("intake", new CommandIntake(ballFondlerSubsystem));
    NamedCommands.registerCommand("feed", new Command() {
      @Override
      public void execute() {
        ballFondlerSubsystem.feed();
      }
      @Override
      public boolean isFinished() {
        return false;
      }
      @Override
      public void end(boolean isInterrupted) {
        ballFondlerSubsystem.stopFeed();
      } 
    });
  }

  public final LimelightSubsystem limelightSubsystem = new LimelightSubsystem();

  private void initializeSubsystems() {
    
  }

  public RobotContainer() {
    SubsystemRegistry.ballFondlerSubsystem = ballFondlerSubsystem;
    m_robotDrive = new DriveSubsystem();
    SubsystemRegistry.m_robotDrive = m_robotDrive;
    this.wiggleSubsystem = new WiggleSubsystem();
    SubsystemRegistry.wiggleSubsystem = this.wiggleSubsystem;
    turboSubsystem = new TurboSubsystem();
    shootControlSubsystem = new ShootControlSubsystem(ballFondlerSubsystem);
    SubsystemRegistry.shootControlSubsystem = shootControlSubsystem;
    SubsystemRegistry.turboSubsystem = turboSubsystem;
    networkingSubsystem = new NetworkingSubsystem(ballFondlerSubsystem, m_robotDrive);
    SubsystemRegistry.networkingSubsystem = networkingSubsystem;
    networkingSubsystem.initDashboards();

    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getLeftY(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getLeftX(), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRightX(), OIConstants.kDriveDeadband),
                true, true),
            m_robotDrive));

    configureBindings();
    configureAutoCommands();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers
   */
  private void configureBindings() {
    m_driverController.x().whileTrue(new CommandXStop(m_robotDrive));
    m_driverController.a().whileTrue(turboSubsystem.getTurboCommand());
    m_shooterController.rightBumper()
        .onTrue(shootControlSubsystem.spoolShooter());
    m_shooterController.leftBumper()
        .whileTrue(new CommandIntake(ballFondlerSubsystem));

    m_shooterController.povUp().onTrue(new CommandIncrement());
    m_shooterController.povDown().onTrue(new CommandDecrement());

    m_shooterController.povRight().onTrue(shootControlSubsystem.spoolCustom());

    m_shooterController.a()
        .whileTrue(new CommandReverseIntake(ballFondlerSubsystem));
    m_shooterController.b()
        .whileTrue(shootControlSubsystem.feed());
    m_shooterController.x()
        .whileTrue(shootControlSubsystem.zoinks());
    m_shooterController.y()
        .onChange(shootControlSubsystem.stopShooter());
    m_driverController.b().whileTrue(wiggleSubsystem.getCommand());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return Autos.exampleAuto(ballFondlerSubsystem);
    // return getAutoFondler();
    return new PathPlannerAuto("AutoMiddleShoot");
  }

  public Command getAutoFondler() {
    return new PathPlannerAuto("AutoFondler");
  }

  public Command getAutoSit() {
    return new PathPlannerAuto("AutoSit");
  }
}
