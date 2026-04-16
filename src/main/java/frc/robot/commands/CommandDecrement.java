package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.SubsystemRegistry;

public class CommandDecrement extends Command {
    private static long lastTouchMs = 0;

    public CommandDecrement() {}

    @Override
    public void execute() {
        if (System.currentTimeMillis()-lastTouchMs < 30) {
            return;
        }
        double curr = SubsystemRegistry.shootControlSubsystem.getCustomRpm();
        double next = Math.max((curr-ShooterConstants.kCustomRpmStep), 0);
        SubsystemRegistry.shootControlSubsystem.setCustomRpm(next);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
