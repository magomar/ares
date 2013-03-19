package ares.engine.command.operational.operations;

import ares.engine.command.operational.plans.OperationalStance;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum OperationForm {

    MEETING_ENGAGEMENT(OperationType.MOVE_TO_CONTACT, OperationalStance.OFFENSIVE),
    SEARCH_AND_ATTACK(OperationType.MOVE_TO_CONTACT, OperationalStance.OFFENSIVE),
    AMBUSH(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    ASSAULT(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    COUNTERATTACK(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    DEMONSTRATION(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    FEINT(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    RAID(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    SPOILING_ATTACK(OperationType.ATTACK, OperationalStance.OFFENSIVE),
    EXPLOITATION(OperationType.EXPLOITATION, OperationalStance.OFFENSIVE),
    PURSUIT(OperationType.PURSUIT, OperationalStance.OFFENSIVE),
    DEFEND_BATTLE_POSITION(OperationType.AREA_DEFENSE, OperationalStance.DEFENSIVE),
    DEFEND_AREA(OperationType.AREA_DEFENSE, OperationalStance.DEFENSIVE),
    MOBILE_DEFENSE(OperationType.MOBILE_DEFENSE, OperationalStance.DEFENSIVE),
    DELAY(OperationType.RETROGRADE, OperationalStance.DEFENSIVE),
    WITHDRAWAL(OperationType.RETROGRADE, OperationalStance.DEFENSIVE),
    RETIREMENT(OperationType.RETROGRADE, OperationalStance.DEFENSIVE),
    DENIAL(OperationType.RETROGRADE, OperationalStance.DEFENSIVE),
    STAYBEHIND(OperationType.RETROGRADE, OperationalStance.DEFENSIVE),
    SCREEN(OperationType.SCREEN, OperationalStance.SECURITY),
    ADVANCE_GUARD(OperationType.ADVANCE_GUARD, OperationalStance.SECURITY),
    FLANK_REAR_GUARD(OperationType.FLANK_REAR_GUARD, OperationalStance.SECURITY),
    COVER(OperationType.COVER, OperationalStance.SECURITY),
    AREA_SECURITY(OperationType.AREA_SECURITY, OperationalStance.SECURITY),
    FORCE_SECURITY(OperationType.FORCE_SECURITY, OperationalStance.SECURITY),
    ROUTE_RECONNAISSANCE(OperationType.ROUTE_RECONNAISSANCE, OperationalStance.RECONNAISSANCE),
    ZONE_RECONNAISSANCE(OperationType.ZONE_RECONNAISSANCE, OperationalStance.RECONNAISSANCE),
    AREA_RECONNAISSANCE(OperationType.AREA_RECONNAISSANCE, OperationalStance.RECONNAISSANCE),
    RECON_IN_FORCE(OperationType.RECON_IN_FORCE, OperationalStance.RECONNAISSANCE);
    private final OperationType type;
    private final OperationalStance stance;

    private OperationForm(final OperationType type, final OperationalStance stance) {
        this.type = type;
        this.stance = stance;
    }

    public OperationType getType() {
        return type;
    }

    public OperationalStance getStance() {
        return stance;
    }
    
    
}
