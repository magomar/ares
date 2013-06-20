package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface Heuristic {

    double getCost(Tile origin, Tile destination, Unit unit);

}
