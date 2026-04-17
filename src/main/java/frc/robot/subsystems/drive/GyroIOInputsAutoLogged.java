package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class GyroIOInputsAutoLogged extends GyroIO.GyroIOInputs implements LoggableInputs, Cloneable {
    @Override
    public void toLog(LogTable table) {
        table.put("Connected", connected);
        table.put("YawPosition", yawPosition);
        table.put("YawVelocityRadPerSec", yawVelocityRadPerSec);
        table.put("OdometryYawTimestamps", odometryYawTimestamps);
        table.put("OdometryYawPositions", odometryYawPositions);
    }

    @Override
    public void fromLog(LogTable table) {
        connected = table.get("Connected", connected);
        yawPosition = table.get("YawPosition", yawPosition);
        yawVelocityRadPerSec = table.get("YawVelocityRadPerSec", yawVelocityRadPerSec);
        odometryYawTimestamps = table.get("OdometryYawTimestamps", odometryYawTimestamps);
        odometryYawPositions = table.get("OdometryYawPositions", odometryYawPositions);
    }

    @Override
    public GyroIOInputsAutoLogged clone() {
        GyroIOInputsAutoLogged copy = new GyroIOInputsAutoLogged();
        copy.connected = connected;
        copy.yawPosition = yawPosition;
        copy.yawVelocityRadPerSec = yawVelocityRadPerSec;
        copy.odometryYawTimestamps = odometryYawTimestamps.clone();
        copy.odometryYawPositions = odometryYawPositions.clone();
        return copy;
    }
}
