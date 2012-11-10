package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.model.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ArtilleryAction extends AbstractAction {

    public ArtilleryAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start) {
        super(actor, type, origin, destination, start);
    }

    @Override
    public void execute(Clock clock) {
        System.out.println(actor + "Bombarding");
    }
}
