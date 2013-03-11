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

    public ArtilleryAction(Unit unit, int start, ActionType type, int duration, Tile target) {
        super(unit, start, type, duration);
        this.target = target;
    }

    public Tile getTarget() {
        return target;
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean checkFeasibility() {
        return true;
    }
}
