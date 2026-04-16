package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WiggleSubsystem extends SubsystemBase {
    private boolean wiggle;
    private int count;

    public WiggleSubsystem() {
        wiggle = false;
        count = 0;
    }
    
    public void enableWiggle() {
        wiggle = true;
    }

    public void disableWiggle() {
        wiggle = false;
    }

    @Override
    public void periodic() {
        count++;
        if (wiggle) {
            if ((count/6)%2==1) {
                SubsystemRegistry.m_robotDrive.drive(0,0,1,true,true);
            } else {
                SubsystemRegistry.m_robotDrive.drive(0,0,-1,true,true);
            }
        }
    }

    public Command getCommand() {
        return new Command() {
            @Override
            public void execute() {
                SubsystemRegistry.wiggleSubsystem.enableWiggle();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public void end(boolean isInterrupted) {
                SubsystemRegistry.wiggleSubsystem.disableWiggle();
            }
        };
    }
    
}
