package ares.engine.movement;

import ares.data.jaxb.TerrainFeature;
import ares.model.board.Direction;
import ares.model.board.Directionality;
import ares.model.board.Terrain;
import ares.model.board.Tile;
import ares.model.forces.LandUnit;
import ares.model.forces.SurfaceUnit;
import ares.model.forces.Unit;
import ares.model.scenario.Scenario;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MovementCost {

    public static final int IMPASSABLE = Integer.MAX_VALUE;
    public static final int MAX_ROAD_COST = 3;
    /**
     * Pre-computed movement costs. This map links the diferent movement types to their costs for a given destination
     * (tile and direction). Movement cost is specified in terms of how many times the cost is reduced. That is, a cost
     * of 1 means moving at standard speed, 2 means moving at half the standard speed, 3 means moving at a third of the
     * standard speed and so on.
     */
    private Map<MovementType, Integer> movementCost;
//    public static final MovementType[] MOVEMENT_TYPES = MovementType.values();

    private static final int ONE = 1;
    private Scenario scenario;

    public MovementCost(Direction fromDir, Tile destination, Scenario scenario) {
        this.scenario = scenario;
        movementCost = new EnumMap<>(MovementType.class);
        //Set AIRCRAFT movement: can move across all tiles, even the non_playable ones (to move between playable areas)
        movementCost.put(MovementType.AIRCRAFT, ONE);
        Set<TerrainFeature> features = destination.getFeatures();
        // NON PLAYABLE tile, impassable for non AIRCRAFT units
        if (features.contains(TerrainFeature.NON_PLAYABLE)) {
            for (MovementType moveType : EnumSet.range(MovementType.FIXED, MovementType.FOOT)) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        }
        // Set FIXED movement, impassable for any tile
        movementCost.put(MovementType.FIXED, IMPASSABLE);
        Set<Terrain> tileTerrain = destination.getTileTerrain();
        // Set NAVAL movement, only allowed across water tiles
        // Check WATER, impasable for all movement types except NAVAL and AIRCRAFT
        if (tileTerrain.contains(Terrain.DEEP_WATER) || tileTerrain.contains(Terrain.SHALLOW_WATER)) {
            movementCost.put(MovementType.NAVAL, ONE);
            for (MovementType moveType : EnumSet.range(MovementType.RIVERINE, MovementType.FOOT)) {
                movementCost.put(moveType, IMPASSABLE);
            }
            return;
        } else {
            movementCost.put(MovementType.NAVAL, IMPASSABLE);
        }
        // Set RIVERINE movement, only allowed across RIVER and CANAL
        if (tileTerrain.contains(Terrain.RIVER) || tileTerrain.contains(Terrain.SUPER_RIVER)
                || tileTerrain.contains(Terrain.CANAL) || tileTerrain.contains(Terrain.SUPER_CANAL)) {
            movementCost.put(MovementType.RIVERINE, ONE);
        } else {
            movementCost.put(MovementType.RIVERINE, IMPASSABLE);
        }
        Direction toDir = fromDir.getOpposite();
        Set<Terrain> sideTerrain = destination.getSideTerrain().get(toDir);
        // Set RAIL movement, only allowed across non broken RAIL 
        if (sideTerrain.contains(Terrain.RAIL) && !sideTerrain.contains(Terrain.BROKEN_RAIL)) {
            movementCost.put(MovementType.RAIL, ONE);
        } else {
            movementCost.put(MovementType.RAIL, IMPASSABLE);
        }
        // Set remaining movement types
        //Distinguishes between movement along roads and off-road movement
        if ((sideTerrain.contains(Terrain.ROAD) || sideTerrain.contains(Terrain.IMPROVED_ROAD))
                && !features.contains(TerrainFeature.BRIDGE_DESTROYED)) {
            // Road-based movement
            for (MovementType moveType : EnumSet.range(MovementType.MOTORIZED, MovementType.FOOT)) {
                movementCost.put(moveType, ONE);
            }
        } else {
            //Off-road movement
            int amphibiousCost = MovementType.AMPHIBIOUS.getMinCost();
            int motorizedCost = MovementType.MOTORIZED.getMinCost();
            int mixedCost = MovementType.MIXED.getMinCost();
            int footCost = MovementType.FOOT.getMinCost();
            for (Terrain terrain : tileTerrain) {
                motorizedCost += terrain.getMotorized();
                mixedCost += terrain.getMixed();
                footCost += terrain.getFoot();
                amphibiousCost += terrain.getAmphibious();
            }
            for (Terrain terrain : sideTerrain) {
                if (terrain.getDirectionality() == Directionality.LOGICAL) {
                    motorizedCost += terrain.getMotorized();
                    mixedCost += terrain.getMixed();
                    footCost += terrain.getFoot();
                    amphibiousCost += terrain.getAmphibious();
                }
            }
            motorizedCost = Math.min(motorizedCost, Math.min(MovementType.MOTORIZED.getMaxCost(), IMPASSABLE));
            mixedCost = Math.min(motorizedCost, Math.min(MovementType.MIXED.getMaxCost(), IMPASSABLE));
            footCost = Math.min(motorizedCost, Math.min(MovementType.FOOT.getMaxCost(), IMPASSABLE));
            amphibiousCost = Math.min(motorizedCost, Math.min(MovementType.AMPHIBIOUS.getMaxCost(), IMPASSABLE));
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
        MovementType movementType = unit.getMovement();
        // TODO check for enemies
        if (!unit.getForce().equals(destination.getOwner()) && destination.getSurfaceUnits().size() > 0) {
            return IMPASSABLE;
        }
        
        if (MovementType.MOBILE_LAND_UNIT.contains(movementType)
                && (sideTerrain.contains(Terrain.ROAD) || sideTerrain.contains(Terrain.IMPROVED_ROAD))) {
            int density = scenario.getScale().getCriticalDensity();
            int numHorsesAndVehicles = 0;
            for (SurfaceUnit surfaceUnit : destination.getSurfaceUnits()) {
                if (MovementType.MOBILE_LAND_UNIT.contains(surfaceUnit.getMovement())) {
                    numHorsesAndVehicles += ((LandUnit)surfaceUnit).getNumVehiclesAndHorses();
                }
            }
            cost = Math.max(ONE, Math.min(MAX_ROAD_COST, numHorsesAndVehicles / density));
        } else {
            cost = movementCost.get(movementType);
        }
        // TODO check for mud and snow
        
        // TODO check for enemy ZOC's
        
        // TODO check for enemy controlled territory
        return cost;
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
