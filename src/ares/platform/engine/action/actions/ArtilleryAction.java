package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionType;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ArtilleryAction extends AbstractAction {

    private final Tile target;

    public ArtilleryAction(ActionType actionType, Unit unit, int start, int duration, Tile target) {
        super(actionType, unit, start, duration);
        this.target = target;
    }

    public Tile getTarget() {
        return target;
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
