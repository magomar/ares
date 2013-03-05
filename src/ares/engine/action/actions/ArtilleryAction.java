package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ArtilleryAction extends AbstractAction {

    private Tile target;

    public ArtilleryAction(Unit unit, ActionType type, Tile target, int start, int duration) {
        super(unit, type, start, duration);
        this.target = target;
    }

    public Tile getTarget() {
        return target;
    }
}
