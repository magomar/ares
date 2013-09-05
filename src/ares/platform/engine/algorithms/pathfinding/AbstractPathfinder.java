package ares.platform.engine.algorithms.pathfinding;

import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunctions;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractPathfinder implements Pathfinder {

    protected Heuristic heuristic;
    protected CostFunction costFunction;

    public AbstractPathfinder() {
        this(MinimunDistance.create(DistanceCalculator.DELTA), CostFunctions.FASTEST);
    }

    protected AbstractPathfinder(Heuristic heuristic, CostFunction costFunction) {
        this.heuristic = heuristic;
        this.costFunction = costFunction;
    }

    @Override
    abstract public Path getPath(Tile origin, Tile destination, Unit unit);

    @Override
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public void setCostFunction(CostFunction costFunction) {
        this.costFunction = costFunction;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public String toStringVerbose() {
        return getClass().getSimpleName() + "{" +
                "heuristic=" + heuristic +
                ", costFunction=" + costFunction +
                '}';
    }
}
