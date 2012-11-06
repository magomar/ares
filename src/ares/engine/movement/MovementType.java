package ares.engine.movement;

import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum MovementType {

    AIRCRAFT,
    FIXED(0, 0),
    NAVAL,
    RIVERINE,
    RAIL,
    AMPHIBIOUS(2, 6),
    MOTORIZED(2, 6),
    MIXED(1, 3),
    FOOT(1, 3);
    private final int minCost;
    private final int maxCost;
    public static final Set<MovementType> MOBILE_LAND_UNIT = EnumSet.of(AMPHIBIOUS, MOTORIZED, MIXED, FOOT);

    private MovementType(final int minCost, final int maxCost) {
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    private MovementType() {
        minCost = maxCost = 1;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public int getMinCost() {
        return minCost;
    }
    
    public boolean isMobileLandUnit() {
        return MovementType.MOBILE_LAND_UNIT.contains(this);
    }
}
