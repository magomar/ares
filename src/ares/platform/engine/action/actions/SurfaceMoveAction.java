package ares.platform.engine.action.actions;

import ares.platform.engine.action.ActionSpace;
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

    public SurfaceMoveAction(ActionType actionType, Unit unit, Path path, ActionSpace actionSpace) {
        this(actionType, unit, AS_SOON_AS_POSSIBLE, path, actionSpace);
    }

    public SurfaceMoveAction(ActionType actionType, Unit unit, int start, Path path, ActionSpace actionSpace) {
        this(actionType, unit, start, TIME_UNKNOWN, path, actionSpace);
    }

    public SurfaceMoveAction(ActionType actionType, Unit unit, int start, int duration, Path path, ActionSpace actionSpace) {
        super(actionType, unit, start, duration, path, actionSpace);
    }

    @Override
    public boolean isFeasible() {
        Tile nextDestination = currentNode.getTile();
        return !nextDestination.hasEnemies(unit.getForce());
    }
}
