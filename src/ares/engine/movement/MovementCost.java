package ares.engine.movement;

import ares.application.models.board.TileModel;
import ares.application.models.forces.UnitModel;
import ares.scenario.Scale;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.*;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MovementCost {
    public static final int IMPASSABLE = Integer.MAX_VALUE;
//    public static final int MAX_ROAD_COST = 3;
//    public static final int MIN_ROAD_COST = 1;
    /**
     * Pre-computed movement costs. This map links the different movement types to their costs for a given destination
     * (tile and direction). Movement cost is specified in terms of how many times the cost is reduced. That is, a cost
     * of 1 means moving at standard speed, 2 means moving at half the standard speed, 3 means moving at a third of the
     * standard speed and so on.
     */
    // TODO check if it's better to initialize all costs to IMPASSABLE
    private Map<MovementType, Integer> movementCost;

    private static final int ONE = 1;
    private Scenario scenario;

    public MovementCost(Direction fromDir, Tile destination, Scenario scenario) {
        this.scenario = scenario;
        movementCost = new EnumMap<>(MovementType.class);

        //Set AIRCRAFT movement: can move across all tiles, even the non_playable ones (to move between playable areas)
        movementCost.put(MovementType.AIRCRAFT, ONE);
        Set<TerrainFeatures> features = destination.getTerrainFeatures();

        boolean destroyedBridge = false;
        boolean offRoadMovement = true;

        for (TerrainFeatures tf : features) {
            // Tile only usable by aircrafts
            if (tf.equals(TerrainFeatures.NON_PLAYABLE) || tf.equals(TerrainFeatures.PEAK)) {
                for (MovementType moveType : EnumSet.range(MovementType.FIXED, MovementType.FOOT)) {
                    movementCost.put(moveType, IMPASSABLE);
                }
                return;
            }
            if (tf.equals(TerrainFeatures.BRIDGE_DESTROYED)) {
                destroyedBridge = true;
            }
        }


        // Set FIXED movement, impassable for any tile
        movementCost.put(MovementType.FIXED, IMPASSABLE);

        Set<Terrain> tileTerrainSet = destination.getTileTerrain();
        for (Terrain terrain : tileTerrainSet) {

            // Set NAVAL movement, only allowed across water tiles
            // Check WATER, impasable for all movement types except NAVAL and AIRCRAFT
            if (terrain.equals(Terrain.DEEP_WATER) || terrain.equals(Terrain.SHALLOW_WATER)) {
                movementCost.put(MovementType.NAVAL, ONE);
                for (MovementType moveType : EnumSet.range(MovementType.RIVERINE, MovementType.FOOT)) {
                    movementCost.put(moveType, IMPASSABLE);
                }
                return;
            }

            // Set RIVERINE movement, only allowed across RIVER and CANAL
            if (terrain.equals(Terrain.RIVER) || terrain.equals(Terrain.SUPER_RIVER) || terrain.equals(Terrain.CANAL) || terrain.equals(Terrain.SUPER_CANAL)) {
                movementCost.put(MovementType.RIVERINE, ONE);
            }
        }

        movementCost.put(MovementType.NAVAL, IMPASSABLE);
        if (movementCost.get(MovementType.RIVERINE) == null) {
            movementCost.put(MovementType.RIVERINE, IMPASSABLE);
        }

        Set<Terrain> sideTerrainSet = destination.getSideTerrain().get(fromDir.getOpposite());
        for (Terrain sideTerrain : sideTerrainSet) {

            // Set RAIL movement, only allowed across non broken RAIL 
            // We can do this because terrains are always ordered
            if (sideTerrain.equals(Terrain.RAIL)) {
                movementCost.put(MovementType.RAIL, ONE);
            }
            if (sideTerrain.equals(Terrain.BROKEN_RAIL)) {
                movementCost.put(MovementType.RAIL, IMPASSABLE);
            }

            //If I thought this was off road but now I see I have a road and no destroyed bridge, then this isn't offRoadMovement
            if (offRoadMovement) {
                if ((sideTerrain.equals(Terrain.ROAD) || sideTerrain.equals(Terrain.IMPROVED_ROAD)) && !destroyedBridge) {
                    offRoadMovement = false;
                }
            }
        }
        if (movementCost.get(MovementType.RAIL) == null) {
            movementCost.put(MovementType.RAIL, IMPASSABLE);
        }

        // Set remaining movement types
        //Distinguishes between movement along roads and off-road movement
        if (!offRoadMovement) {
            // Road-based movement
            for (MovementType moveType : EnumSet.range(MovementType.MOTORIZED, MovementType.FOOT)) {
                movementCost.put(moveType, moveType.getMinOnRoadCost());
            }
        } else {
            //Off-road movement
            int amphibiousCost = MovementType.AMPHIBIOUS.getMinOffRoadCost();
            int motorizedCost = MovementType.MOTORIZED.getMinOffRoadCost();
            int mixedCost = MovementType.MIXED.getMinOffRoadCost();
            int footCost = MovementType.FOOT.getMinOffRoadCost();
            for (Terrain tileTerrain : tileTerrainSet) {
                motorizedCost += tileTerrain.getMotorized();
                mixedCost += tileTerrain.getMixed();
                footCost += tileTerrain.getFoot();
                amphibiousCost += tileTerrain.getAmphibious();
            }
            for (Terrain sideTerrain : sideTerrainSet) {
                if (sideTerrain.getDirectionality() == Directionality.LOGICAL) {
                    motorizedCost += sideTerrain.getMotorized();
                    mixedCost += sideTerrain.getMixed();
                    footCost += sideTerrain.getFoot();
                    amphibiousCost += sideTerrain.getAmphibious();
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
     * @param from
     * @return
     */
    public int getActualCost(Unit unit, Tile destination, Direction from) {
        int cost;
        Direction toDir = from.getOpposite();
        Set<Terrain> sideTerrain = destination.getSideTerrain().get(toDir);
        MovementType moveType = unit.getMovement();
        // TODO check for enemies
        if (!unit.getForce().equals(destination.getOwner()) && destination.getSurfaceUnits().size() > 0) {
            return IMPASSABLE;
        }
        if (destination.hasEnemies(unit.getForce())) {
            return IMPASSABLE;
        }

        if (MovementType.MOBILE_LAND_UNIT.contains(moveType)
                && (sideTerrain.contains(Terrain.ROAD) || sideTerrain.contains(Terrain.IMPROVED_ROAD))) {
            int density = Scale.INSTANCE.getCriticalDensity();
            int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : destination.getSurfaceUnits()) {
                if (MovementType.MOBILE_LAND_UNIT.contains(surfaceUnit.getMovement())) {
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
        MovementType moveType = unit.getMovement();
        int penalty = 0, cost;
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

        Set<Terrain> sideTerrain = destination.getSideTerrain().get(fromDir);

        if (MovementType.MOBILE_LAND_UNIT.contains(moveType)
                && (sideTerrain.contains(Terrain.ROAD) || sideTerrain.contains(Terrain.IMPROVED_ROAD))) {
            int density = Scale.INSTANCE.getCriticalDensity(), numHorsesAndVehicles = 0;
            // If destination isn't oberved SurfaceUnits will be empty
            for (SurfaceUnit su : destination.getSurfaceUnits()) {
                if (MovementType.MOBILE_LAND_UNIT.contains(su.getMovement())) {
                    numHorsesAndVehicles += ((LandUnit) su).getNumVehiclesAndHorses();
                }
            }
            cost = Math.max(ONE, Math.min(moveType.getMaxOnRoadCost(), numHorsesAndVehicles / density));
        } else {
            cost = movementCost.get(unit.getMovement());
        }

        if (destination.getTerrainFeatures().contains(TerrainFeatures.SNOWY) || destination.getTerrainFeatures().contains(TerrainFeatures.MUDDY)) {
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
}
