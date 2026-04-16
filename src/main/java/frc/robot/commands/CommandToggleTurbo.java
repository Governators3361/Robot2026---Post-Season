package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.drive.DriveSubsystem;

public class CommandToggleTurbo extends Command {
    private static boolean turboActivated = false;

    @Override
    public void execute() {
        if (turboActivated) {
            turboActivated = false;
            DriveConstants.kMaxAngularSpeed = 2*Math.PI;
        }
    }

}
