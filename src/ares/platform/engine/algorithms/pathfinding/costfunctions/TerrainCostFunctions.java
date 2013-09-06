package ares.platform.engine.algorithms.pathfinding.costfunctions;

import ares.platform.engine.movement.MovementCost;
import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public enum TerrainCostFunctions implements CostFunction {
    FASTEST {
        @Override
        public double getCost(Direction dir, Tile destination, Unit unit) {
            return destination.getEnterCost(dir).getTerrainCost(unit.getMovementType());
        }
    },
    SHORTEST {
        @Override
        public double getCost(Direction dir, Tile destination, Unit unit) {
            int cost = destination.getEnterCost(dir).getTerrainCost(unit.getMovementType());
            if (cost == MovementCost.IMPASSABLE) {
                return cost;
            } else {
                return 1;
            }
        }
    };
}
