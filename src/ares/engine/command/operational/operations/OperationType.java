package ares.engine.command.operational.operations;

import ares.engine.command.operational.plans.OperationalStance;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum OperationType  {

    MOVE_TO_CONTACT(OperationalStance.OFFENSIVE),
    ATTACK(OperationalStance.OFFENSIVE),
    EXPLOITATION(OperationalStance.OFFENSIVE),
    PURSUIT(OperationalStance.OFFENSIVE),
    AREA_DEFENSE(OperationalStance.DEFENSIVE),
    MOBILE_DEFENSE(OperationalStance.DEFENSIVE),
    RETROGRADE(OperationalStance.DEFENSIVE),
    SCREEN(OperationalStance.SECURITY),
    ADVANCE_GUARD(OperationalStance.SECURITY),
    FLANK_REAR_GUARD(OperationalStance.SECURITY),
    COVER(OperationalStance.SECURITY),
    AREA_SECURITY(OperationalStance.SECURITY),
    FORCE_SECURITY(OperationalStance.SECURITY),
    ROUTE_RECONNAISSANCE(OperationalStance.RECONNAISSANCE),
    ZONE_RECONNAISSANCE(OperationalStance.RECONNAISSANCE),
    AREA_RECONNAISSANCE(OperationalStance.RECONNAISSANCE),
    RECON_IN_FORCE(OperationalStance.RECONNAISSANCE);
    private final OperationalStance stance;

    private OperationType(final OperationalStance stance) {
        this.stance = stance;
    }

    public OperationalStance getStance() {
        return stance;
    }
}
