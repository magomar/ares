package ares.platform.engine.messages;

import java.util.logging.Level;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum EngineMessageType {

    MISSION_COMPLETED("Mission has been completed", Level.INFO.intValue() + 1),
    MISSION_ABANDONED("Mission has been abandoned", Level.INFO.intValue() + 2),
    MISSION_STARTED("Mission has started", Level.INFO.intValue() + 3),
    UNIT_DESTROYED("Unit has been destroyed", Level.INFO.intValue() + 4),
    UNIT_ATTACKED("Unit attacked", Level.INFO.intValue() + 5),
    UNIT_MOVED("Unit moved", Level.INFO.intValue() + 6),
    UNIT_BEGINS_ATTACK("Unit begins attack", Level.INFO.intValue() + 7),
    UNIT_ATTACK_SUCCESSFUL("Unit attack has been successful", Level.INFO.intValue() + 8),
    UNIT_ABORTED_ATTACK("Unit has aborted attack", Level.INFO.intValue() + 9),
    UNIT_RETIRED("Unit retired", Level.INFO.intValue() + 10),
    UNIT_ROUTED("Unit had to reroute", Level.INFO.intValue() + 11);
    // Message description
    private final String description;
    // Level value
    private final Integer value;

    private EngineMessageType(String name, Integer value) {
        this.description = name;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public Integer intValue() {
        return value;
    }
}
