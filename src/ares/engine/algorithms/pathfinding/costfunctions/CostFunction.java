package ares.engine.algorithms.pathfinding.costfunctions;

import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface CostFunction {
        double getCost(Direction dir, Tile destination, Unit unit);
}
