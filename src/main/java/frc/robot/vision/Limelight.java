package frc.robot.vision;

import java.io.PrintWriter;
import java.io.StringWriter;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.LimelightHelpers;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.subsystems.SubsystemRegistry;

public class Limelight {
    public static final Limelight mInstance = new Limelight();

    private static final String LIMELIGHT_NAME = "limelight";

    private Pose2d lastPose = new Pose2d();
    private PoseEstimate lastEstimate = null;
    private long numPoseStableUpdates = 0;

    private Limelight() {}

    public void periodic() {
        try {
            // Required for MegaTag2
            double yawDeg = SubsystemRegistry.m_robotDrive.getPose().getRotation().getDegrees();
            LimelightHelpers.SetRobotOrientation(
                LIMELIGHT_NAME,
                yawDeg,
                0.0,
                0.0,
                0.0,
                0.0,
                0.0
            );

            PoseEstimate estimate =
                LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LIMELIGHT_NAME);

            boolean validEstimate =
                estimate != null &&
                estimate.pose != null &&
                estimate.tagCount > 0;

            if (validEstimate) {
                Pose2d newPose = estimate.pose;

                // compare against drive pose, not object identity bullshit
                double translationAgreement =
                    SubsystemRegistry.m_robotDrive.getPose()
                        .getTranslation()
                        .getDistance(newPose.getTranslation());

                if (translationAgreement
                        < 1) {
                    numPoseStableUpdates++;
                } else {
                    numPoseStableUpdates = 0;
                }

                lastPose = newPose;
                lastEstimate = estimate;
            } else {
                numPoseStableUpdates = 0;
            }

            SmartDashboard.putBoolean("Vision/Has Estimate", validEstimate);
            SmartDashboard.putNumber("Vision/Num Agreed Stable Updates", numPoseStableUpdates);
            SmartDashboard.putBoolean("Vision/Pose Stable", getPoseStable());
            SmartDashboard.putNumber("Vision/X", lastPose.getX());
            SmartDashboard.putNumber("Vision/Y", lastPose.getY());
            SmartDashboard.putNumber("Vision/Heading Deg", lastPose.getRotation().getDegrees());

            if (lastEstimate != null) {
                SmartDashboard.putNumber("Vision/Tag Count", lastEstimate.tagCount);
                SmartDashboard.putNumber("Vision/Avg Tag Dist", lastEstimate.avgTagDist);
                SmartDashboard.putNumber("Vision/Latency Ms", lastEstimate.latency);
                SmartDashboard.putNumber("Vision/Timestamp Sec", lastEstimate.timestampSeconds);
            }

        } catch (Exception e) {
            SmartDashboard.putNumber("Limelight/Crash", Timer.getFPGATimestamp());
            SmartDashboard.putString("Limelight/Crash Exception", String.valueOf(e.getMessage()));

            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            SmartDashboard.putString("Limelight/Crash Stacktrace", sw.toString());
        }
    }

    public Time getLastUpdateTime() {
        if (lastEstimate == null) {
            return Units.Seconds.of(0.0);
        }
        return Units.Seconds.of(lastEstimate.timestampSeconds);
    }

    public Pose2d getLatestUpdate() {
        return lastPose;
    }

    public PoseEstimate getLatestEstimate() {
        return lastEstimate;
    }

    public boolean hasVisionEstimate() {
        return lastEstimate != null;
    }

    public boolean getPoseStable() {
        return numPoseStableUpdates >
            5;
    }
}