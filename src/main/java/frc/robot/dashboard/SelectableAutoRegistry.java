package frc.robot.dashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubsystemRegistry;

// a class to store all of ur autos, should only need to throw new ones in the map
// and everything will work
public class SelectableAutoRegistry {
    @FunctionalInterface
    interface SelectableAuto {
        Command getCommand();
    }

    private static HashMap<String, SelectableAuto> map;

    private static void initMap() {
        map = new HashMap<>();
    }

    public static List<String> getAutoTitles() {
        if (map == null) {
            initMap();
        }
        ArrayList<String> result = new ArrayList<>();
        for (String title : map.keySet()) {
            result.add(title);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * @param title the title of the auto
     * @return respecting Command for use in autos
     */
    public static Command getCommandByTitle(String title) {
        if (map == null) {
            initMap();
        }
        for (String autoTitle : map.keySet()) {
            if (autoTitle.equals(title)) {
                return map.get(autoTitle).getCommand();
            }
        }

        throw new IllegalArgumentException("unregistered command, title unknown");
    }
}
