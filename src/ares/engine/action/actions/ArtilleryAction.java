package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ArtilleryAction extends AbstractAction {
    private Tile target;

    public ArtilleryAction(UnitActor actor, ActionType type, Tile target, int start, int duration) {
        super(actor, type, start, duration);
        this.target = target;
    }

    public Tile getTarget() {
        return target;
    }

}
