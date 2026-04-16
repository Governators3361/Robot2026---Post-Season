package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shoot.BallFondlerSubsystem;

public class CommandIntake extends Command {

  private final BallFondlerSubsystem subsystem;

  public CommandIntake(BallFondlerSubsystem subsystem) {
    this.subsystem = subsystem;
    addRequirements(subsystem);
  }

  @Override
  public void execute() {
    subsystem.intakeForward();
  }

  @Override
  public void end(boolean interrupted) {
    subsystem.stopIntake();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}