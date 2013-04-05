package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.algorithms.pathfinding.Path;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class SurfaceMoveAction extends MoveAction {

    public SurfaceMoveAction(Unit unit, ActionType type, Path path) {
        super(unit, type, path);
    }

    @Override
    public boolean isFeasible() {
        Tile nextDestination = currentNode.getTile();
        return !nextDestination.hasEnemies(unit.getForce());
    }
}
