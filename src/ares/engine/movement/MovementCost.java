package ares.engine.movement;

import ares.data.jaxb.MultiDirection;
import ares.platform.util.EnumSetOperations;
import ares.scenario.Scale;
import ares.scenario.board.*;
import ares.scenario.forces.*;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MovementCost {

    public static final int IMPASSABLE = Integer.MAX_VALUE;
    /**
     * Pre-computed movement costs. This map links the different movement types to their costs for a given destination
     * (tile and direction). Movement cost is specified in terms of how many times the cost is reduced. That is, a cost
     * of 1 means moving at standard speed, 2 means moving at half the standard speed, 3 means moving at a third of the
     * standard speed and so on.
     */
    // TODO check if it's better to initialize all costs to IMPASSABLE
    private Map<MovementType, Integer> movementCost;
    private static final int ONE = 1;

    public MovementCost(Direction fromDir, Tile destination) {
        movementCost = new EnumMap<>(MovementType.class);

        Set<Feature> features = destination.getFeatures();

        boolean destroyedBridge = false;

        for (Feature tf : features) {
            // Tile only usable by aircrafts
            if (tf.equals(Feature.NON_PLAYABLE) || tf.equals(Feature.PEAK)) {
                for (MovementType moveType : EnumSet.range(MovementType.FIXED, MovementType.FOOT)) {
                    movementCost.put(moveType, IMPASSABLE);
                }
                return;
            }
            if (tf.equals(Feature.BRIDGE_DESTROYED)) {
                destroyedBridge = true;
            }
        }
        //Set AIRCRAFT movement: can move across all tiles, even the non_playable ones (to move between playable areas)
        movementCost.put(MovementType.AIRCRAFT, ONE);

        // Set FIXED movement, impassable for any tile
        movementCost.put(MovementType.FIXED, IMPASSABLE);
        Map<Terrain, Directions> terrainMap = destination.getTerrain();
        Set<Terrain> terrains = terrainMap.keySet();

        // Set NAVAL movement, only allowed across water tiles (DEEP_WATER and SHALLOW water)
        // Water tiles are impasable for all movement types except NAVAL and AIRCRAFT
        if (EnumSetOperations.nonEmptyIntersection(terrains, Terrain.ANY_WATER)) {
            movementCost.put(MovementType.NAVAL, ONE);
            for (MovementType moveType : EnumSet.range(MovementType.RIVERINE, MovementType.FOOT)) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        }
        movementCost.put(MovementType.NAVAL, IMPASSABLE);

        // Set RIVERINE movement, only allowed across RIVER, SUPER_RIVER,CANAL and SUPER_CANAL
        // River tiles are have a unitary cost for units capable of RIVERINE movement
        if (EnumSetOperations.nonEmptyIntersection(terrains, Terrain.ANY_RIVER)) {
            movementCost.put(MovementType.RIVERINE, ONE);
        } else {
            movementCost.put(MovementType.RIVERINE, IMPASSABLE);
        }
        // Set RAIL movement, only allowed across non broken RAIL 
        if (containsTerrainInDirection(terrainMap, Terrain.RAIL, fromDir) //      && !containsTerrainInDirection(terrainMap, Terrain.BROKEN_RAIL, fromDir) // Assuming RAIL and BROKEN_RAIL are mutually exclusive
                ) {
            movementCost.put(MovementType.RAIL, ONE);
        } else {
            movementCost.put(MovementType.RAIL, IMPASSABLE);
        }

        boolean offRoadMovement = (containsSomeTerrainInDirection(terrainMap, Terrain.ANY_ROAD, fromDir) && !destroyedBridge ? false : true);

        // Set remaining movement types
        //Distinguishes between movement along roads and off-road movement
        if (!offRoadMovement) {
            // Road-based movement
            for (MovementType moveType : MovementType.ANY_LAND_MOVEMENT) {
                movementCost.put(moveType, moveType.getMinOnRoadCost());
            }
        } else {
            //Off-road movement
            int amphibiousCost = MovementType.AMPHIBIOUS.getMinOffRoadCost();
            int motorizedCost = MovementType.MOTORIZED.getMinOffRoadCost();
            int mixedCost = MovementType.MIXED.getMinOffRoadCost();
            int footCost = MovementType.FOOT.getMinOffRoadCost();
            for (Map.Entry<Terrain, Directions> entry : terrainMap.entrySet()) {
                Terrain t = entry.getKey();
                Directions directions = entry.getValue();
                if (!t.isDirectional() || directions.contains(fromDir)) {
                    motorizedCost += t.getMotorized();
                    mixedCost += t.getMixed();
                    footCost += t.getFoot();
                    amphibiousCost += t.getAmphibious();
                }
            }
            motorizedCost = Math.min(motorizedCost, Math.min(MovementType.MOTORIZED.getMaxOffRoadCost(), IMPASSABLE));
            mixedCost = Math.min(motorizedCost, Math.min(MovementType.MIXED.getMaxOffRoadCost(), IMPASSABLE));
            footCost = Math.min(motorizedCost, Math.min(MovementType.FOOT.getMaxOffRoadCost(), IMPASSABLE));
            amphibiousCost = Math.min(motorizedCost, Math.min(MovementType.AMPHIBIOUS.getMaxOffRoadCost(), IMPASSABLE));
            movementCost.put(MovementType.MOTORIZED, motorizedCost);
            movementCost.put(MovementType.MIXED, mixedCost);
            movementCost.put(MovementType.FOOT, footCost);
            movementCost.put(MovementType.AMPHIBIOUS, amphibiousCost);
        }
    }

    /**
     * Return the actual movement cost, having into account both the precomputed cost and dinamic conditions such as the
     * traffic density in case of using roads, the presence of near enemy units, or the presence of dynamic terrain
     * features such as mud and snow
     *
     * @param unit
     * @param destination
     * @param fromDir
     * @return
     */
    public int getActualCost(Unit unit, Tile destination, Direction fromDir) {
        int cost;
        Direction toDir = fromDir.getOpposite();

        // TODO check for enemies
        if (!unit.getForce().equals(destination.getOwner()) && destination.getSurfaceUnits().size() > 0) {
            return IMPASSABLE;
        }
        if (destination.hasEnemies(unit.getForce())) {
            return IMPASSABLE;
        }

        Map<Terrain, Directions> terrainMap = destination.getTerrain();
//        Set<Terrain> terrains = terrainMap.keySet();
        MovementType moveType = unit.getMovement();
        if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(moveType)
                && containsSomeTerrainInDirection(terrainMap, Terrain.ANY_ROAD, toDir)) {
            int density = Scale.INSTANCE.getCriticalDensity();
            int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : destination.getSurfaceUnits()) {
                if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(surfaceUnit.getMovement())) {
                    numHorsesAndVehicles += ((LandUnit) surfaceUnit).getNumVehiclesAndHorses();
                }
            }
            cost = Math.max(ONE, Math.min(moveType.getMaxOnRoadCost(), numHorsesAndVehicles / density));
        } else {
            cost = movementCost.get(moveType);
        }
        // TODO check for mud and snow

        // TODO check for enemy ZOC's

        // TODO check for enemy controlled territory
        return cost;
    }

    public int getActualCost(Unit unit, Tile destination, Direction fromDir, boolean avoidEnemies, boolean shortest) {
        int cost;
        MovementType moveType = unit.getMovement();
        int penalty = 0;
        if (shortest) {
            // If it's possible, then go for it
            int d = movementCost.get(moveType);
            return (d < IMPASSABLE) ? 1 : IMPASSABLE;
        }
        if (destination.hasEnemies(unit.getForce())) {
            if (avoidEnemies) {
                return IMPASSABLE;
            }
            // Enemies on the way
            penalty++;
        }
        Direction toDir = fromDir.getOpposite();
        Map<Terrain, Directions> terrainMap = destination.getTerrain();

        if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(moveType)
                && containsSomeTerrainInDirection(terrainMap, Terrain.ANY_ROAD, toDir)) {
            int density = Scale.INSTANCE.getCriticalDensity(), numHorsesAndVehicles = 0;
            // If destination isn't oberved SurfaceUnits will be empty
            for (SurfaceUnit su : destination.getSurfaceUnits()) {
                if (MovementType.ANY_LAND_OR_AMPH_MOVEMENT.contains(su.getMovement())) {
                    numHorsesAndVehicles += ((LandUnit) su).getNumVehiclesAndHorses();
                }
            }
            cost = Math.max(ONE, Math.min(moveType.getMaxOnRoadCost(), numHorsesAndVehicles / density));
        } else {
            cost = movementCost.get(unit.getMovement());
        }

        if (destination.getFeatures().contains(Feature.SNOWY) || destination.getFeatures().contains(Feature.MUDDY)) {
            penalty++;
        }
        return cost + penalty;
    }

    /**
     * Return the precomputed movement cost (cost depending on inmutable terrain characteristics such as the terrain
     * type)
     *
     * @param movement
     * @return
     */
    public int getPrecomputedCost(MovementType movement) {
        return movementCost.get(movement);
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
}
