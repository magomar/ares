package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class MinimunDistance implements Heuristic {

    private static Map<DistanceCalculator, MinimunDistance> instances = new EnumMap<>(DistanceCalculator.class);

    public static MinimunDistance create(DistanceCalculator distanceCalculator) {
        if (instances.containsKey(distanceCalculator)) {
            return instances.get(distanceCalculator);
        } else {
            MinimunDistance newInstance = new MinimunDistance(distanceCalculator);
            instances.put(distanceCalculator, newInstance);
            return newInstance;
        }
    }
    protected final DistanceCalculator distanceCalculator;

    private MinimunDistance(DistanceCalculator distanceCalculator) {
        this.distanceCalculator = distanceCalculator;
    }

    @Override
    public double getCost(Tile origin, Tile destination, Unit unit) {
        return distanceCalculator.getCost(origin, destination);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
}
