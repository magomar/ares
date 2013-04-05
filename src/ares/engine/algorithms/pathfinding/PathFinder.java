package ares.engine.algorithms.pathfinding;

import ares.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface PathFinder {


    void setHeuristic(Heuristic heuristic);

    Path getPath(Tile origin, Tile destination, Unit unit);
}
