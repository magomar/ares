package ares.engine.algorithms.pathfinding.heuristics;

import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface Heuristic {

    double getCost(Tile origin, Tile destination, Unit unit);
}
