package ares.platform.engine.movement;

import java.util.EnumSet;
import java.util.Set;

/**
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
    /**
     * Minimum cost to expend when moving off-road
     */
    private final int minOffRoadCost;
    /**
     * Maximum cost to expend when moving off-road
     */
    private final int maxOffRoadCost;
    /**
     * Minimum cost to expend when moving on-road
     */
    private final int minOnRoadCost;
    /**
     * Maximum cost to expend when moving on-road
     */
    private final int maxOnRoadCost;
    public static final Set<MovementType> NON_AIRCRAFT = EnumSet.range(FIXED, FOOT);
    public static final Set<MovementType> LAND_OR_AMPHIBIOUS = EnumSet.of(AMPHIBIOUS, MOTORIZED, MIXED, FOOT);
//    public static final Set<MovementType> LAND = EnumSet.of(MOTORIZED, MIXED, FOOT);

    /**
     * @param minOffRoadCost the minimum movement cost when moving off-road
     * @param maxOffRoadCost the maximum movement cost when moving off-road
     */
    private MovementType(final int minOffRoadCost, final int maxOffRoadCost) {
        this.minOffRoadCost = minOffRoadCost;
        this.maxOffRoadCost = maxOffRoadCost;
        if (minOffRoadCost > 1) {
            minOnRoadCost = minOffRoadCost / 2;
            maxOnRoadCost = maxOffRoadCost / 2;
        } else {
            minOnRoadCost = minOffRoadCost;
            maxOnRoadCost = maxOffRoadCost;
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
        return MovementType.LAND_OR_AMPHIBIOUS.contains(this);
    }
}
