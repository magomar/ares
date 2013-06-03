package ares.platform.engine.algorithms.pathfinding;

import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface PathFinder {

    Path getPath(Tile origin, Tile destination, Unit unit);
}
