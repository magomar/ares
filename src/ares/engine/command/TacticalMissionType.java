package ares.engine.command;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum TacticalMissionType {

    NULL(null);
//    ASSAULT(TacticalMissionAssault.class),
//    ATTACK_BY_FIRE(TacticalMissionAttackByFire.class),
//    BREACH(TacticalMissionExample.class),
//    BYPASS(TacticalMissionBypass.class),
//    CLEAR(TacticalMissionExample.class),
//    COMBAT_SEARCH_AND_RESCUE(TacticalMissionExample.class),
//    REORGANIZATION(TacticalMissionExample.class),
//    CONTROL(TacticalMissionExample.class),
//    COUNTERRECONNAISSANCE(TacticalMissionExample.class),
//    DISENGAGE(TacticalMissionExample.class),
//    EXFILTRATE(TacticalMissionExample.class),
//    FOLLOW_AND_ASSUME(TacticalMissionExample.class),
//    FOLLOW_AND_SUPPORT(TacticalMissionFollowAndSupport.class),
//    LINKUP(TacticalMissionExample.class),
//    OCCUPY(TacticalMissionExample.class),
//    RECONSTITUTION(TacticalMissionExample.class),
//    REDUCE(TacticalMissionExample.class),
//    RETAIN(TacticalMissionExample.class),
//    SECURE(TacticalMissionExample.class),
//    SEIZE(TacticalMissionExample.class),
//    SUPPORT_BY_FIRE(TacticalMissionSupportByFire.class),
//    SUPPRESS(TacticalMissionExample.class),
//    BLOCK(TacticalMissionExample.class),
//    CANALIZE(TacticalMissionExample.class),
//    CONTAIN(TacticalMissionExample.class),
//    DEFEAT(TacticalMissionExample.class),
//    DESTROY(TacticalMissionExample.class),
//    DISRUPT(TacticalMissionExample.class),
//    FIX(TacticalMissionFix.class),
//    INTERDICT(TacticalMissionExample.class),
//    ISOLATE(TacticalMissionExample.class),
//    NEUTRALIZE(TacticalMissionExample.class),
//    SUPRESS(TacticalMissionExample.class),
//    TURN(TacticalMissionExample.class);
    private final Class tacticalMissionClass;

    TacticalMissionType(final Class tacticalMissionClass) {
        this.tacticalMissionClass = tacticalMissionClass;
    }

    public Class getTacticalMissionClass() {
        return tacticalMissionClass;
    }
}
