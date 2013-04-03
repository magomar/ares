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
    // Minimum cost to expend when moving offroad
    private final int minOffRoadCost;
    // Maximuum cost to expend when moving offroad
    private final int maxOffRoadCost;
    private final int minOnRoadCost;
    private final int maxOnRoadCost;
    public static final Set<MovementType> ANY_LAND_OR_AMPH_MOVEMENT = EnumSet.of(AMPHIBIOUS, MOTORIZED, MIXED, FOOT);
    public static final Set<MovementType> ANY_LAND_OR_RIVERINE_RAIL_AMPHIBIOUS_MOVEMENT = EnumSet.range(RIVERINE, FOOT);
    public static final Set<MovementType> ANY_LAND_MOVEMENT = EnumSet.range(MovementType.MOTORIZED, MovementType.FOOT);

    private MovementType(final int minCost, final int maxCost) {
        this.minOffRoadCost = minCost;
        this.maxOffRoadCost = maxCost;
        if (minCost > 1) {
            minOnRoadCost = minCost / 2;
            maxOnRoadCost = maxCost / 2;
        } else {
            minOnRoadCost = minCost;
            maxOnRoadCost = maxCost;
        }
    }

    private MovementType() {
        minOffRoadCost = maxOffRoadCost = minOnRoadCost = maxOnRoadCost = 1;
    }

    public int getMaxOffRoadCost() {
        return maxOffRoadCost;
    }

    public int getMinOffRoadCost() {
        return minOffRoadCost;
    }

    public int getMaxOnRoadCost() {
        return maxOnRoadCost;
    }

    public int getMinOnRoadCost() {
        return minOnRoadCost;
    }

    public boolean isMobileLandUnit() {
        return MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(this);
    }
}
