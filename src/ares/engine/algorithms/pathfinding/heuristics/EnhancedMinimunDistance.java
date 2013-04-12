package ares.engine.algorithms.pathfinding.heuristics;

import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class EnhancedMinimunDistance extends MinimunDistance {

    public EnhancedMinimunDistance(DistanceCalculator distanceCalculator) {
        super(distanceCalculator);
    }

    @Override
    public double getCost(Tile origin, Tile destination, Unit unit) {
        return distanceCalculator.getCost(origin, destination) - 1 + origin.getMinMoveCosts().get(unit.getMovement());
    }
}
