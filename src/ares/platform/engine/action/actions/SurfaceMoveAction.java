package ares.platform.engine.action.actions;

import ares.platform.engine.action.ActionType;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * Movement action for surface units
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
