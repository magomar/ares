package ares.engine.combat;

import ares.scenario.board.Direction;
import ares.scenario.board.Directionality;
import ares.scenario.board.Terrain;
import ares.scenario.board.Tile;
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
        Set<Terrain> tileTerrain = where.getTileTerrain();
        for (Terrain terrain : tileTerrain) {
            antiTank += terrain.getAntiTank();
            antiPersonnel += terrain.getAntiPersonnel();
            vehicles += terrain.getVehicles();
            infantry += terrain.getInfantry();
            stationary += terrain.getStationary();
        }
        Set<Terrain> sideTerrain = where.getSideTerrain().get(dir);
        for (Terrain terrain : sideTerrain) {
            if (terrain.getDirectionality() == Directionality.LOGICAL) {
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
}
