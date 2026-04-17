package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class VisionIOInputsAutoLogged extends VisionIO.VisionIOInputs implements LoggableInputs, Cloneable {
    @Override
    public void toLog(LogTable table) {
        table.put("Connected", connected);
        table.put("LatestTargetObservation", latestTargetObservation);
        table.put("PoseObservations", poseObservations);
        table.put("TagIds", tagIds);
    }

    @Override
    public void fromLog(LogTable table) {
        connected = table.get("Connected", connected);
        latestTargetObservation = table.get("LatestTargetObservation", latestTargetObservation);
        poseObservations = table.get("PoseObservations", poseObservations);
        tagIds = table.get("TagIds", tagIds);
    }

    @Override
    public VisionIOInputsAutoLogged clone() {
        VisionIOInputsAutoLogged copy = new VisionIOInputsAutoLogged();
        copy.connected = connected;
        copy.latestTargetObservation = latestTargetObservation;
        copy.poseObservations = poseObservations.clone();
        copy.tagIds = tagIds.clone();
        return copy;
    }
}
