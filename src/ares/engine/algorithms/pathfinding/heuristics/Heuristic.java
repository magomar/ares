package ares.engine.algorithms.pathfinding.heuristics;

import ares.scenario.board.Tile;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface Heuristic {

    int getCost(Tile origin, Tile destination);
}
