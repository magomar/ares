package ares.platform.engine.algorithms.pathfinding;

import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Pathfinder {

    Path getPath(Tile origin, Tile destination, Unit unit);

    ExtendedPath getExtendedPath(Tile origin, Tile destination, Unit unit);

    void setHeuristic(Heuristic heuristic);

    void setCostFunction(CostFunction costFunction);

    Heuristic getHeuristic();

    CostFunction getCostFunction();
}
