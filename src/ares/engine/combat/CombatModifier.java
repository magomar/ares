package ares.engine.combat;

import ares.scenario.board.Direction;
import ares.scenario.board.Directions;
import ares.scenario.board.Terrain;
import ares.scenario.board.Tile;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatModifier {

    private double antiTank;
    private double antiPersonnel;
    private double vehicles;
    private double infantry;
    private double stationary;

    public CombatModifier(Tile where, Direction dir) {
        Map<Terrain, Directions> terrainMap = where.getTerrain();
        Set<Terrain> tileTerrain = terrainMap.keySet();
        for (Terrain terrain : tileTerrain) {
            if (!terrain.isDirectional() || containsTerrainInDirection(terrainMap, terrain, dir)) {
                antiTank += terrain.getAntiTank();
                antiPersonnel += terrain.getAntiPersonnel();
                vehicles += terrain.getVehicles();
                infantry += terrain.getInfantry();
                stationary += terrain.getStationary();
            }

        }
    }

    public double getAntiPersonnel() {
        return antiPersonnel;
    }

    public double getAntiTank() {
        return antiTank;
    }

    public double getInfantry() {
        return infantry;
    }

    public double getStationary() {
        return stationary;
    }

    public double getVehicles() {
        return vehicles;
    }

    private boolean containsTerrainInDirection(Map<Terrain, Directions> terrainMap, Terrain terrain, Direction fromDir) {
        Directions directions = terrainMap.get(terrain);
        if (directions == null) {
            return false;
        }
        return directions.contains(fromDir);
    }
}
