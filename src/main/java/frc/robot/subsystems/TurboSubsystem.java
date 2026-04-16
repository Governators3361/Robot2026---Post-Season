package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class TurboSubsystem extends SubsystemBase {
    private static final double defaultMaxVelocity = 3;
    private static final double defaultMaxAngularSpeed = 2*Math.PI;

    private static double turboMaxVelocity = 4.5;
    private static double turboMaxAngularSpeed = 4*Math.PI;
    private boolean turboActivated;

    public TurboSubsystem() {
        turboActivated = false;
    }

    public boolean getTurbo() {
        return turboActivated;
    }

    public void setTurbo(boolean b) {
        turboActivated = b;
        if (b) {
            enableTurbo();
        } else {
            disableTurbo();
        }
    }

    public void enableTurbo() {
        turboActivated = true;
        DriveConstants.kMaxAngularSpeed = turboMaxAngularSpeed;
        DriveConstants.kMaxSpeedMetersPerSecond = turboMaxVelocity;
    }

    public void disableTurbo() {
        turboActivated = false;
        DriveConstants.kMaxAngularSpeed = defaultMaxAngularSpeed;
        DriveConstants.kMaxSpeedMetersPerSecond = defaultMaxVelocity;
    }

    public double getCurrentMaxVelocity() {
        return DriveConstants.kMaxSpeedMetersPerSecond;
    }

    public double getCurrentMaxAngularSpeed() {
        return DriveConstants.kMaxAngularSpeed;
    }

    public double getTurboMaxVelocity() {
        return turboMaxVelocity;
    }

    public void setTurboMaxVelocity(double d) {
        turboMaxVelocity = d;
    }

    public double getTurboMaxAngularSpeed() {
        return turboMaxAngularSpeed;
    }
    public void setTurboMaxAngularSpeed(double d) {
        turboMaxAngularSpeed = d;
    }

    public Command getTurboCommand() {
        return new Command() {
            @Override
            public void execute() {
                enableTurbo();
            }

            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public void end(boolean isInterrupted) {
                disableTurbo();
            }
        };
    }

    
}
