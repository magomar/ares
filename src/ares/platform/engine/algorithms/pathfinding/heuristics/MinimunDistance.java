package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class MinimunDistance implements Heuristic {

    protected final DistanceCalculator distanceCalculator;

    public MinimunDistance(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public double getCost(Tile origin, Tile destination, Unit unit) {
        return distanceCalculator.getCost(origin, destination);
    }
}
