package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.RawFiducial;

public class LimelightSubsystem extends SubsystemBase {

    private static final String LIMELIGHT_NAME = "limelight";

    public LimelightSubsystem() {}

    public boolean hasTarget() {
        RawFiducial[] fiducials = LimelightHelpers.getRawFiducials(LIMELIGHT_NAME);
        return fiducials != null && fiducials.length > 0;
    }

    public double getDistanceMeters() {
        RawFiducial[] fiducials = LimelightHelpers.getRawFiducials(LIMELIGHT_NAME);

        if (fiducials == null || fiducials.length == 0) {
            return -1;
        }

        return fiducials[0].distToCamera;
    }

    public int getTagID() {
        RawFiducial[] fiducials = LimelightHelpers.getRawFiducials(LIMELIGHT_NAME);

        if (fiducials == null || fiducials.length == 0) {
            return -1;
        }

        return fiducials[0].id;
    }
    

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Tag Distance", getDistanceMeters());
        SmartDashboard.putNumber("Tag ID", getTagID());
        SmartDashboard.putNumber("TX", LimelightHelpers.getTX(LIMELIGHT_NAME));
        SmartDashboard.putBoolean("Has Target", hasTarget());
    }
}