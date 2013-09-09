package ares.platform.engine.movement;

import ares.data.wrappers.scenario.TerrainType;
import ares.platform.scenario.Scale;
import ares.platform.scenario.board.*;
import ares.platform.scenario.forces.Force;
import ares.platform.scenario.forces.LandUnit;
import ares.platform.scenario.forces.SurfaceUnit;
import ares.platform.scenario.forces.Unit;
import ares.platform.util.EnumSetOperations;
import ares.platform.util.MathUtils;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * MovementCost objects contain the precomputed off-road movement costs for all movement types and a single tile and
 * direction. Each movement unitType has a cost to enter a {@link Tile}, which depends on the {@link TerrainType} and {@link Feature} it
 * contains. On-road movement cost is not precomputed, instead it is computed dynamically to take into account the
 * density of vehicle and horses in a tile at the moment.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @see Tile#enterCost
 */
public class MovementCost {

    public static final int IMPASSABLE = Integer.MAX_VALUE;
    public static final int UNITARY_MOVEMENT_COST = 1;
    private static final int ENEMY_OCCUPIED_PENALTY = 10;
    private static final int ENEMY_ZOC_PENALTY = 2;
    private static final int ENEMY_OWNED_PENALTY = 1;
    private static final int SNOW_PENALTY = 2;
    private static final int MUD_PENALTY = 2;
    /**
     * Pre-computed movement costs. This map links the different movement types to their costs for a given destination
     * (tile and direction). Movement speed is inversely proportional to the cost. A cost of 1 means moving at standard
     * speed, 2 means moving at half the standard speed, 3 means moving at a third of the standard speed and so on.
     */
    private final Map<MovementType, Integer> movementCost;
    private final Direction direction;
    private final Tile tile;
    /**
     * Whether there is a (non-destroyed) road
     */
    private boolean hasRoad;

    public MovementCost(Tile tile, Direction direction) {
        this.tile = tile;
        this.direction = direction;
        movementCost = new EnumMap<>(MovementType.class);
        hasRoad = false;
        computeConstantTerrainCost(tile, direction);
    }

    private void computeConstantTerrainCost(Tile tile, Direction direction) {
        Set<Feature> features = tile.getFeatures();

        //Set AIRCRAFT movement: can move across all tiles, including peaks and non-playable tiles
        movementCost.put(MovementType.AIRCRAFT, UNITARY_MOVEMENT_COST);
        // Set IMPASSABLE in case of peaks and non-playable tiles, for any non aircraft unit
        if (!tile.isPlayable() || features.contains(Feature.PEAK)) {
            for (MovementType moveType : MovementType.NON_AIRCRAFT) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        }


        // Set FIXED movement, impassable for any tile
        movementCost.put(MovementType.FIXED, IMPASSABLE);

        Map<Terrain, Directions> terrainMap = tile.getTerrain();
        Set<Terrain> terrains = terrainMap.keySet();

        // Check whether on-road movement is possible (has road and no bridge destroyed)
        hasRoad = (containsSomeTerrainInDirection(terrainMap, Terrain.ANY_ROAD, direction) && !features.contains(Feature.BRIDGE_DESTROYED) ? true : false);

        // Set NAVAL movement, only allowed across water tiles (DEEP_WATER and SHALLOW water)
        // Water tiles are impassable for all movement types except NAVAL and AIRCRAFT
        if (EnumSetOperations.nonEmptyIntersection(terrains, Terrain.ANY_WATER)) {
            movementCost.put(MovementType.NAVAL, UNITARY_MOVEMENT_COST);
            for (MovementType moveType : EnumSet.range(MovementType.RIVERINE, MovementType.FOOT)) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        }
        // Set IMPASSABLE for naval movement
        movementCost.put(MovementType.NAVAL, IMPASSABLE);

        // Set RIVERINE movement, only allowed across RIVER, SUPER_RIVER,CANAL and SUPER_CANAL
        // River tiles are have a unitary cost for units capable of RIVERINE movement
        if (EnumSetOperations.nonEmptyIntersection(terrains, Terrain.ANY_RIVER)) {
            movementCost.put(MovementType.RIVERINE, UNITARY_MOVEMENT_COST);
        } else {
            movementCost.put(MovementType.RIVERINE, IMPASSABLE);
        }
        // Set RAIL movement, only allowed across non broken RAIL
        if (containsTerrainInDirection(terrainMap, Terrain.RAIL, direction)) {
            movementCost.put(MovementType.RAIL, UNITARY_MOVEMENT_COST);
            // Assuming RAIL and BROKEN_RAIL are mutually exclusive, if not, then check also !containsTerrainInDirection(terrainMap, Terrain.BROKEN_RAIL, fromDir)
        } else {
            movementCost.put(MovementType.RAIL, IMPASSABLE);
        }

        // Set remaining movement types (AMPHIBIOUS, MOTORIZED, MIXED, FOOT)
        int amphibiousCost = MovementType.AMPHIBIOUS.getMinOffRoadCost();
        int motorizedCost = MovementType.MOTORIZED.getMinOffRoadCost();
        int mixedCost = MovementType.MIXED.getMinOffRoadCost();
        int footCost = MovementType.FOOT.getMinOffRoadCost();
        for (Map.Entry<Terrain, Directions> entry : terrainMap.entrySet()) {
            Terrain terrain = entry.getKey();
            Directions directions = entry.getValue();
            if (!terrain.isDirectional() || directions.contains(direction)) {
                motorizedCost = addCost(motorizedCost, terrain.getMotorized());
                mixedCost = addCost(mixedCost, terrain.getMixed());
                footCost = addCost(footCost, terrain.getFoot());
                amphibiousCost = addCost(amphibiousCost, terrain.getAmphibious());
            }
        }

        if (amphibiousCost < IMPASSABLE) {
            movementCost.put(MovementType.AMPHIBIOUS, Math.min(amphibiousCost, MovementType.AMPHIBIOUS.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.AMPHIBIOUS, IMPASSABLE);
        }
        if (motorizedCost < IMPASSABLE) {
            movementCost.put(MovementType.MOTORIZED, Math.min(motorizedCost, MovementType.MOTORIZED.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.MOTORIZED, IMPASSABLE);
        }
        if (mixedCost < IMPASSABLE) {
            movementCost.put(MovementType.MIXED, Math.min(mixedCost, MovementType.MIXED.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.MIXED, IMPASSABLE);
        }
        if (footCost < IMPASSABLE) {
            movementCost.put(MovementType.FOOT, Math.min(footCost, MovementType.FOOT.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.FOOT, IMPASSABLE);
        }
    }

    /**
     * Gets the precomputed off-road movement cost for a single {@link MovementType}
     *
     * @param movementType the type of movement
     * @return the off-road movement cost
     */
    public int getOffRoadTerrainCost(MovementType movementType) {
        return movementCost.get(movementType);
    }

    public int getOnRoadTerrainCost(MovementType movementType) {
        // If road movement is possible then return the minimum off-road cost
        if (isOnRoadMovementAllowed(movementType)) return movementType.getMinOnRoadCost();
        else return IMPASSABLE;
    }

    public boolean isOnRoadMovementAllowed(MovementType movementType) {
        return hasRoad && MovementType.LAND_OR_AMPHIBIOUS.contains(movementType);
    }
    /**
     * Return the actual movement cost, having into account both the precomputed cost and dynamic conditions such as the
     * traffic density in case of using roads, the presence of near enemy units, or the presence of dynamic terrain
     * features such as mud and snow
     *
     * @param unit
     * @return the cost
     */
    public int getVariableCost(Unit unit) {
        MovementType movementType = unit.getMovementType();
        int offRoadCost = getOffRoadTerrainCost(movementType);
        // If road movement is possible then compute on-road movement taking into account traffic density
        int onRoadCost = IMPASSABLE;
        if (isOnRoadMovementAllowed(movementType)) {
            int density = Scale.INSTANCE.getCriticalDensity();
            int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : tile.getSurfaceUnits()) {
                if (MovementType.LAND_OR_AMPHIBIOUS.contains(surfaceUnit.getMovementType())) {
                    numHorsesAndVehicles += ((LandUnit) surfaceUnit).getNumVehiclesAndHorses();
                }
            }
            int minCost = movementType.getMinOnRoadCost();
            int maxCost = movementType.getMaxOnRoadCost();
            onRoadCost = MathUtils.setBounds(numHorsesAndVehicles / density, minCost, maxCost);
        }

        // Chose minimum of on-road and off-road movement costs
        int maxCost;
        int cost;
        if (offRoadCost < onRoadCost) {
            cost = offRoadCost;
            maxCost = movementType.getMaxOffRoadCost();
        } else {
            cost = onRoadCost;
            maxCost = movementType.getMaxOnRoadCost();
        }
        if (cost == IMPASSABLE) {
            return IMPASSABLE;
        }
        // APPLY PENALTIES
        Force force = unit.getForce();
        int penalty = 0;
        if (tile.hasEnemies(force)) { // Occupied by enemy
            penalty += ENEMY_OCCUPIED_PENALTY;
        } else if (tile.hasEnemiesNearby(force)) { // Enemy ZOC
            penalty += ENEMY_ZOC_PENALTY;
        } else if (!tile.isAlliedTerritory(force)) { // Owned by enemy
            penalty += ENEMY_OWNED_PENALTY;
        }

        return MathUtils.addBounded(cost, penalty, maxCost);
    }

    /**
     * Gets the movement cost for a particular {@linkn MovementType} according solely to the terrain
     *
     * @param movementType the type of movement
     * @return the cost
     */
    public int getTerrainCost(MovementType movementType) {
        return Math.min(getOffRoadTerrainCost(movementType), getOnRoadTerrainCost(movementType));
    }

    private boolean containsTerrainInDirection(Map<Terrain, Directions> terrainMap, Terrain terrain, Direction fromDir) {
        Directions directions = terrainMap.get(terrain);
        if (directions == null) {
            return false;
        }
        return directions.contains(fromDir);
    }

    private boolean containsSomeTerrainInDirection(Map<Terrain, Directions> terrainMap, Set<Terrain> terrains, Direction fromDir) {
        for (Terrain terrain : terrains) {
            if (containsTerrainInDirection(terrainMap, terrain, fromDir)) {
                return true;
            }
        }
        return false;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public void addFeature(Feature feature) {
        for (MovementType movementType : MovementType.LAND_OR_AMPHIBIOUS) {
            int offRoadCost = movementCost.get(movementType);
            switch (feature) {
                case SNOWY:
                    movementCost.put(movementType, MathUtils.addBounded(offRoadCost, SNOW_PENALTY, movementType.getMaxOffRoadCost()));
                    break;
                case MUDDY:
                    movementCost.put(movementType, MathUtils.addBounded(offRoadCost, MUD_PENALTY, movementType.getMaxOffRoadCost()));
                    break;
                case BRIDGE_DESTROYED:
                    hasRoad = false;
            }
        }
    }

    public void removeFeature(Feature feature) {
        for (MovementType movementType : MovementType.LAND_OR_AMPHIBIOUS) {
            int offRoadCost = movementCost.get(movementType);
            switch (feature) {
                case SNOWY:
                    movementCost.put(movementType, MathUtils.subtractBounded(offRoadCost, SNOW_PENALTY, movementType.getMinOffRoadCost()));
                    break;
                case MUDDY:
                    movementCost.put(movementType, MathUtils.subtractBounded(offRoadCost, MUD_PENALTY, movementType.getMinOffRoadCost()));
                    break;
                case BRIDGE_DESTROYED:
                    hasRoad = true;
            }
        }
    }

    private static int addCost(int value, int addend) {
        if (addend==IMPASSABLE || value == IMPASSABLE) return IMPASSABLE;
        return value + addend;
    }

    @Override
    public String toString() {
        return "MovementCost{" + "movementCost=" + movementCost + ", hasRoad=" + hasRoad + ", direction=" + direction + ", tile=" + tile + '}';
    }
}
