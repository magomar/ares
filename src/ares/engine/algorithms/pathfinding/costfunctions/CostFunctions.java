package ares.engine.algorithms.pathfinding.costfunctions;

import ares.engine.movement.MovementCost;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum CostFunctions implements CostFunction {

    SHORTEST {
        @Override
        public double getCost(Direction dir, Tile destination, Unit unit) {
            int cost = destination.getEnterCost(dir).getActualCost(unit);
            if (cost == MovementCost.IMPASSABLE) {
                return cost;
            } else {
                return 1;
            }
        }
    },
    FASTEST {
        @Override
        public double getCost(Direction dir, Tile destination, Unit unit) {
            return destination.getEnterCost(dir).getActualCost(unit);
        }
    },
    SAFEST {
        @Override
        public double getCost(Direction dir, Tile destination, Unit unit) {
            return destination.getEnterCost(dir).getActualCost(unit);
        }
    };
}
