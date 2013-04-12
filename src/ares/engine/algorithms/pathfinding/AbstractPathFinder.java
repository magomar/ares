package ares.engine.algorithms.pathfinding;

import ares.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractPathFinder implements PathFinder {

    protected final Heuristic heuristic;


    public AbstractPathFinder(Heuristic heuristic) {
       this.heuristic = heuristic;
    }

    @Override
    abstract public Path getPath(Tile origin, Tile destination, Unit unit);
}
