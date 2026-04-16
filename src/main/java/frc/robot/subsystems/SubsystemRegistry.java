package frc.robot.subsystems;

import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.shoot.BallFondlerSubsystem;
import frc.robot.subsystems.shoot.ShootControlSubsystem;

public class SubsystemRegistry {
    public static BallFondlerSubsystem ballFondlerSubsystem;
    public static DriveSubsystem m_robotDrive;
    public static NetworkingSubsystem networkingSubsystem;
    public static TurboSubsystem turboSubsystem;
    public static ShootControlSubsystem shootControlSubsystem;
    public static WiggleSubsystem wiggleSubsystem;
}
