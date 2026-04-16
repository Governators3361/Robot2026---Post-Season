package frc.robot.subsystems.shoot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.SubsystemRegistry;

public class ShootControlSubsystem extends SubsystemBase {
    private final BallFondlerSubsystem subsystem;
    private boolean shooterEnabled;
    private double lastEnableMs;
    private double customRpm;

    public ShootControlSubsystem(BallFondlerSubsystem subsystem) {
        this.subsystem = subsystem;
        customRpm = ShooterConstants.kShortRpm;
    }

    public double getCustomRpm() {
        return customRpm;
    }

    public void setCustomRpm(double customRpm) {
        this.customRpm = customRpm;
    }

    public Command shootRoutine() {
        return new Command() {
            @Override 
            public void execute() {
            shooterEnabled = !shooterEnabled;
            lastEnableMs = System.currentTimeMillis();
            }
            @Override
            public boolean isFinished() {
                return true;
            }
        };
    }

    public Command spoolCustom() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.rpmShoot(customRpm);
            }
            @Override
            public boolean isFinished() {
                return true;
            }
        };
    }

    public Command intake() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.intakeForward();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public void end(boolean isInterrupted) {
                subsystem.stopIntake();
            }
        };
    }

    public Command feed() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.feed();
            }
            @Override
            public void end(boolean isInterrupted) {
                subsystem.stopFeed();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
        };
    }
    
    public Command stopShooter() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.shooterOff();
            }
            @Override
            public boolean isFinished() {
                return true;
            }
            @Override
            public void end(boolean isInterrupted) {
                
            }
        };
    }

    public Command spoolShooter() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.rpmShoot(3750);
            }

            @Override
            public boolean isFinished() {
                return true;
            }
        };
    }

    public Command zoinks() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.yoink();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public void end(boolean isInterrupted) {
                subsystem.stopYoink();
            }
        };
    }

    public Command shootFeed() {
        return new Command() {
            @Override
            public void execute() {
                subsystem.rpmShootFeed(ShooterConstants.kShortRpm);
            }

            @Override
            public boolean isFinished() {
                return false;
            }
        };    
    }
}
