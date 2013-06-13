package ares.platform.engine.movement;

import ares.platform.scenario.forces.Force;
import ares.platform.scenario.forces.SurfaceUnit;
import ares.platform.scenario.board.Direction;
import ares.platform.scenario.forces.LandUnit;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.board.Feature;
import ares.platform.scenario.board.Terrain;
import ares.platform.scenario.forces.Unit;
import ares.platform.scenario.board.Directions;
import ares.data.jaxb.TerrainType;
import ares.platform.util.EnumSetOperations;
import ares.platform.util.MathUtils;
import ares.platform.scenario.Scale;
import java.util.*;

/**
 * MovementCost objects contain the precomputed off-road movement costs for all movement types and a single tile and
 * direction. Each movement type has a cost to enter a {@link Tile}, which depends on the {@link TerrainType} it
 * contains. On-road movement cost is not precomputed, instead it is computed dynamically to take into account the
 * density of vehicle and horses in a tile at the moment.
 *
 * @see Tile#moveCosts
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MovementCost {

    public static final int IMPASSABLE = Integer.MAX_VALUE;
    public static final int UNITARY_MOVEMENT_COST = 1;
    private static final int ENEMIES_PENALTY = 4;
    private static final int SNOW_PENALTY = 2;
    private static final int MUD_PENALTY = 2;
    /**
     * Pre-computed movement costs. This map links the different movement types to their costs for a given destination
     * (tile and direction). Movement speed is inversely proportional to the cost. A cost of 1 means moving at standard
     * speed, 2 means moving at half the standard speed, 3 means moving at a third of the standard speed and so on.
     */
    private final Map<MovementType, Integer> movementCost;
    /**
     * Whether there is a (non-destroyed) road
     */
    private boolean hasRoad;
    private final Direction direction;
    private final Tile tile;

    public MovementCost(Tile tile, Direction direction) {
        this.tile = tile;
        this.direction = direction;
        movementCost = new EnumMap<>(MovementType.class);

        Set<Feature> features = tile.getFeatures();

        boolean destroyedBridge = false;

        //Set AIRCRAFT movement: can move across all tiles, including peaks and non-playable tiles
        movementCost.put(MovementType.AIRCRAFT, UNITARY_MOVEMENT_COST);
        // Set IMPASSABLE in case of peaks and non-playable tiles, for any non aircraft unit
        if (features.contains(Feature.NON_PLAYABLE) || features.contains(Feature.PEAK)) {
            for (MovementType moveType : MovementType.ANY_NON_AIRCRAFT_MOVEMENT) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        }
        // Check for destroyed bridges
        if (features.contains(Feature.BRIDGE_DESTROYED)) {
            destroyedBridge = true;
        }

        // Set FIXED movement, impassable for any tile
        movementCost.put(MovementType.FIXED, IMPASSABLE);
        Map<Terrain, Directions> terrainMap = tile.getTerrain();
        Set<Terrain> terrains = terrainMap.keySet();

        // Set NAVAL movement, only allowed across water tiles (DEEP_WATER and SHALLOW water)
        // Water tiles are impasable for all movement types except NAVAL and AIRCRAFT
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
        // Check wether on-road movement is possible (road and no bridge destroyed)
        hasRoad = (containsSomeTerrainInDirection(terrainMap, Terrain.ANY_ROAD, direction) && !destroyedBridge ? true : false);
        // Set remaining movement types
        int amphibiousCost = MovementType.AMPHIBIOUS.getMinOffRoadCost();
        int motorizedCost = MovementType.MOTORIZED.getMinOffRoadCost();
        int mixedCost = MovementType.MIXED.getMinOffRoadCost();
        int footCost = MovementType.FOOT.getMinOffRoadCost();
        for (Map.Entry<Terrain, Directions> entry : terrainMap.entrySet()) {
            Terrain t = entry.getKey();
            Directions directions = entry.getValue();
            if (!t.isDirectional() || directions.contains(direction)) {
                motorizedCost = MathUtils.addBounded(motorizedCost, t.getMotorized(), IMPASSABLE);
                mixedCost = MathUtils.addBounded(mixedCost, t.getMixed(), IMPASSABLE);
                footCost = MathUtils.addBounded(footCost, t.getFoot(), IMPASSABLE);
                amphibiousCost = MathUtils.addBounded(amphibiousCost, t.getAmphibious(), IMPASSABLE);
            }
        }
        if (motorizedCost < IMPASSABLE) {
            movementCost.put(MovementType.AMPHIBIOUS, Math.min(motorizedCost, MovementType.AMPHIBIOUS.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.AMPHIBIOUS, IMPASSABLE);
        }
        if (motorizedCost < IMPASSABLE) {
            movementCost.put(MovementType.MOTORIZED, Math.min(motorizedCost, MovementType.MOTORIZED.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.MOTORIZED, IMPASSABLE);
        }
        if (motorizedCost < IMPASSABLE) {
            movementCost.put(MovementType.MIXED, Math.min(motorizedCost, MovementType.MIXED.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.MIXED, IMPASSABLE);
        }
        if (motorizedCost < IMPASSABLE) {
            movementCost.put(MovementType.FOOT, Math.min(motorizedCost, MovementType.FOOT.getMaxOffRoadCost()));
        } else {
            movementCost.put(MovementType.FOOT, IMPASSABLE);
        }

    }

    /**
     * Return the actual movement cost, having into account both the precomputed cost and dinamic conditions such as the
     * traffic density in case of using roads, the presence of near enemy units, or the presence of dynamic terrain
     * features such as mud and snow
     *
     * @param unit
     * @param destination
     * @param direction
     * @return
     */
    public int getActualCost(Unit unit) {
        Force force = unit.getForce();
        // Check for enemies. Enemies prevent movement
        if (tile.hasEnemies(force) || (!tile.isPlayable() && !unit.isAircraft())) {
            return IMPASSABLE;
        }

        MovementType moveType = unit.getMovement();
        // COMPUTE ON-ROAD MOVEMENT
        // If road movement is possible then compute on-road movement taking into account traffic density
        int onRoadCost = IMPASSABLE;
        if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(moveType) && hasRoad) {
            int density = Scale.INSTANCE.getCriticalDensity();
            int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : tile.getSurfaceUnits()) {
                if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(surfaceUnit.getMovement())) {
                    numHorsesAndVehicles += ((LandUnit) surfaceUnit).getNumVehiclesAndHorses();
                }
            }
            int minCost = moveType.getMinOnRoadCost();
            int maxCost = moveType.getMaxOnRoadCost();
            onRoadCost = MathUtils.setBounds(numHorsesAndVehicles / density, minCost, maxCost);
        }
        // COMPUTE OFF-ROAD MOVEMENT
        int offRoadcost = movementCost.get(moveType);
        if (offRoadcost < IMPASSABLE) {
            Set<Feature> features = tile.getFeatures();
            if (features.contains(Feature.SNOWY)) {
                offRoadcost = MathUtils.addBounded(offRoadcost, SNOW_PENALTY, moveType.getMaxOffRoadCost());
            }
            if (features.contains(Feature.MUDDY)) {
                offRoadcost = MathUtils.addBounded(offRoadcost, MUD_PENALTY, moveType.getMaxOffRoadCost());
            }
        }
        // Chose minimun of on-road and off-road movement costs
        int maxCost;
        int cost;
        if (offRoadcost < onRoadCost) {
            cost = offRoadcost;
            maxCost = moveType.getMaxOffRoadCost();
        } else {
            cost = onRoadCost;
            maxCost = moveType.getMaxOnRoadCost();
        }

        if (cost == IMPASSABLE) {
            return IMPASSABLE;
        }

        // APPLY PENALTIES
        int penalty = 0;
        if (tile.hasEnemiesNearby(force)) { // Enemy ZOC
            penalty += 2;
        } 
        else if (tile.isAlliedTerritory(force)) { // Controlled territory
            penalty++;
        }

        return MathUtils.addBounded(cost, penalty, maxCost);
    }

    public int getEstimatedCost(MovementType moveType) {
        // Check for enemies. Enemies prevent movement
        if (!tile.isPlayable() && moveType != MovementType.AIRCRAFT) {
            return IMPASSABLE;
        }

        // COMPUTE ON-ROAD MOVEMENT
        // If road movement is possible then compute on-road movement taking into account traffic density
        int onRoadCost = IMPASSABLE;
        if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(moveType) && hasRoad) {
//            int density = Scale.INSTANCE.getCriticalDensity();
//            int numHorsesAndVehicles = 0;
//            for (SurfaceUnit surfaceUnit : tile.getSurfaceUnits()) {
//                if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(surfaceUnit.getMovement())) {
//                    numHorsesAndVehicles += ((LandUnit) surfaceUnit).getNumVehiclesAndHorses();
//                }
//            }
//            int minCost = moveType.getMinOnRoadCost();
//            int maxCost = moveType.getMaxOnRoadCost();
//            onRoadCost = MathUtils.setBounds(numHorsesAndVehicles / density, minCost, maxCost);
            onRoadCost = moveType.getMinOnRoadCost();
        }
        // COMPUTE OFF-ROAD MOVEMENT
        int offRoadcost = movementCost.get(moveType);
        if (offRoadcost < IMPASSABLE) {
            Set<Feature> features = tile.getFeatures();
            if (features.contains(Feature.SNOWY)) {
                offRoadcost = MathUtils.addBounded(offRoadcost, SNOW_PENALTY, moveType.getMaxOffRoadCost());
            }
            if (features.contains(Feature.MUDDY)) {
                offRoadcost = MathUtils.addBounded(offRoadcost, MUD_PENALTY, moveType.getMaxOffRoadCost());
            }
        }
        // Chose minimun of on-road and off-road movement costs
        int maxCost;
        int cost;
        if (offRoadcost < onRoadCost) {
            cost = offRoadcost;
            maxCost = moveType.getMaxOffRoadCost();
        } else {
            cost = onRoadCost;
            maxCost = moveType.getMaxOnRoadCost();
        }

        if (cost == IMPASSABLE) {
            return IMPASSABLE;
        }

        return Math.min(cost, maxCost);
    }
    
    
//    public int getEstimatedCost(Unit unit) {
//        MovementType moveType = unit.getMovement();
//        int cost;
//
//        int penalty = 0;
//        if (tile.hasEnemies(unit.getForce())) {
//            penalty += ENEMIES_PENALTY;
//        }
//        cost = movementCost.get(moveType);
//        return cost + penalty;
//    }

    
    /**
     * Gets the precomputed movement cost for a single {@code movementType}
     *
     * @param movementType
     * @return
     */
    public int getMovementCost(MovementType movementType) {
        return movementCost.get(movementType);
    }

    /**
     * Gets the precomputed costs for all the movement types
     *
     * @see MovementType
     * @return
     */
    public Map<MovementType, Integer> getMovementCost() {
        return movementCost;
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

    @Override
    public String toString() {
        return "MovementCost{" + "movementCost=" + movementCost + ", hasRoad=" + hasRoad + ", direction=" + direction + ", tile=" + tile + '}';
    }
}
