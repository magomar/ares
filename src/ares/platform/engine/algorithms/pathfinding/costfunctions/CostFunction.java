package ares.platform.engine.algorithms.pathfinding.costfunctions;

import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface CostFunction {
        double getCost(Direction dir, Tile destination, Unit unit);
}
