package ares.engine.algorithms.pathfinding;

import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface PathFinder {

    Path getPath(Tile origin, Tile destination, Unit unit);
}
