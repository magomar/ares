package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

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
        return distanceCalculator.getCost(origin, destination) - 1 + origin.getMinExitCosts().get(unit.getMovement());
    }
}
