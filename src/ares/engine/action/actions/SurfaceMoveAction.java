package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.algorithms.routing.Path;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class SurfaceMoveAction extends MoveAction {


    public SurfaceMoveAction(UnitActor actor, ActionType type, Path path) {
        super(actor, type, path);
    }

    @Override
    public boolean checkFeasibility() {
        Tile nextDestination = currentNode.getTile();
        return !nextDestination.hasEnemies(actor.getUnit().getForce());
    }

}
