package ares.engine.action.actions;

import ares.application.models.board.TileModel;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.algorithms.routing.Path;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class SurfaceMoveAction extends MoveAction {


    public SurfaceMoveAction(UnitActor actor, ActionType type, Path path, int tileSize) {
        super(actor, type, path, tileSize);
    }

    @Override
    public boolean checkFeasibility() {
        TileModel nextDestination = path.getFirst().getTile();
        return !nextDestination.hasEnemies(actor.getUnit().getForce().getName());
    }

}
