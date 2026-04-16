package frc.robot.subsystems;


import org.littletonrobotics.junction.Logger;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CameraServerJNI;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.dashboard.SelectableAutoRegistry;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.shoot.BallFondlerSubsystem;

public class NetworkingSubsystem extends SubsystemBase {
    private final BallFondlerSubsystem ballFondlerSubsystem;
    private final DriveSubsystem m_robotDrive;
    private final SendableChooser<Command> autoSelector;
    private final PowerDistribution pdu;
    private final Field2d dashField;
    private double shootRpm;

    public NetworkingSubsystem(BallFondlerSubsystem ballFondlerSubsystem, DriveSubsystem m_robotDrive) {
        this.ballFondlerSubsystem = ballFondlerSubsystem;
        this.m_robotDrive = m_robotDrive;
        autoSelector = new SendableChooser<>();
        autoSelector.setDefaultOption("AutoFondler", new PathPlannerAuto("AutoFondler"));
        pdu = new PowerDistribution(1, ModuleType.kRev);
        dashField = new Field2d();
        shootRpm = 0;
    }

    public void initDashboards() {
        addCameras();
        addSwerveDrive();
        addSelectableAutos();
        addDashField();
        addPdu();
        addPidFields();
        addTurbo();
        addCommands();
    }

    public void addCameras() {
        CameraServer.startAutomaticCapture();
    }

    public void addCommands() {
        SmartDashboard.putData("Shooter RPM control", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("rpm", SubsystemRegistry.networkingSubsystem::getShootRpm, SubsystemRegistry.networkingSubsystem::setShootRpm);
            }
        });
    }

    public void addTurbo() {
        SmartDashboard.putData("Turbo", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                TurboSubsystem subsystem = SubsystemRegistry.turboSubsystem;
                builder.addDoubleProperty("Current Max Speed (m-s)", subsystem::getCurrentMaxVelocity, null);
                builder.addDoubleProperty("Current Max Angular Speed (rad-s)", subsystem::getCurrentMaxAngularSpeed, null);
                builder.addDoubleProperty("Turbo Max Speed (m-s)", subsystem::getTurboMaxVelocity, subsystem::setTurboMaxVelocity);
                builder.addDoubleProperty("Turbo Max Angular Speed (rad-s)", subsystem::getTurboMaxAngularSpeed, subsystem::setTurboMaxAngularSpeed);
                builder.addBooleanProperty("Turbo Enabled", subsystem::getTurbo, subsystem::setTurbo);
            }
        });
    }

    public Double getShootRpm() {
        return shootRpm;
    }

    public void setShootRpm(Double rpm) {
        shootRpm = rpm;
    }

    private void addSwerveDrive() {
        SmartDashboard.putData("Swerve Drive", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.setSmartDashboardType("SwerveDrive");

                builder.addDoubleProperty("Front Left Angle", () -> m_robotDrive.getFrontLeft().getPositionTurning(),
                        null);
                builder.addDoubleProperty("Front Left Velocity", () -> m_robotDrive.getFrontLeft().getVelocityDrive(),
                        null);

                builder.addDoubleProperty("Front Right Angle", () -> m_robotDrive.getFrontRight().getPositionTurning(),
                        null);
                builder.addDoubleProperty("Front Right Velocity", () -> m_robotDrive.getFrontRight().getVelocityDrive(),
                        null);

                builder.addDoubleProperty("Back Left Angle", () -> m_robotDrive.getRearLeft().getPositionTurning(),
                        null);
                builder.addDoubleProperty("Back Left Velocity", () -> m_robotDrive.getRearLeft().getVelocityDrive(),
                        null);

                builder.addDoubleProperty("Back Right Angle", () -> m_robotDrive.getRearRight().getPositionTurning(),
                        null);
                builder.addDoubleProperty("Back Right Velocity", () -> m_robotDrive.getRearRight().getVelocityDrive(),
                        null);

                builder.addDoubleProperty("Robot Angle", () -> m_robotDrive.getCurrentRotation(), null);
            }
        });
        SmartDashboard.putData("Shooter data", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("Shooter RPM", () -> ballFondlerSubsystem.getShooterVelocity(), null);
            }
        });
    }

    private void addSelectableAutos() {
        SmartDashboard.putData("Auto Selector", autoSelector);
    }

    private void addDashField() {
        SmartDashboard.putData("Field2d", dashField);
    }

    private void addPidFields() {
        SmartDashboard.putData("Shooter Pid", new Sendable() {
            @Override
            public void initSendable(SendableBuilder builder) {
                builder.addDoubleProperty("kP", ballFondlerSubsystem::getKP, ballFondlerSubsystem::setKP);
                builder.addDoubleProperty("kI", ballFondlerSubsystem::getKI, ballFondlerSubsystem::setKI);
                builder.addDoubleProperty("kD", ballFondlerSubsystem::getKD, ballFondlerSubsystem::setKD);
            }
        });
    }

    private void addPdu() {
        SmartDashboard.putData("Pdu", pdu);
    }

    public Command getSelectedAutoCommand() {
        return autoSelector.getSelected();
    }

    @Override
    public void periodic() {
        dashField.setRobotPose(m_robotDrive.getPose());
    }
}
