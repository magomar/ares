package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class EnhancedMinimunDistance implements Heuristic {

    private static final Map<DistanceCalculator, EnhancedMinimunDistance> instances = new EnumMap<>(DistanceCalculator.class);

    public static EnhancedMinimunDistance create(DistanceCalculator distanceCalculator) {
        if (instances.containsKey(distanceCalculator)) {
            return instances.get(distanceCalculator);
        } else {
            EnhancedMinimunDistance newInstance = new EnhancedMinimunDistance(distanceCalculator);
            instances.put(distanceCalculator, newInstance);
            return newInstance;
        }
    }

    protected final DistanceCalculator distanceCalculator;

    private EnhancedMinimunDistance(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public double getCost(Tile origin, Tile destination, Unit unit) {
        return distanceCalculator.getCost(origin, destination) - 1 + origin.getMinExitCosts().get(unit.getMovementType());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
