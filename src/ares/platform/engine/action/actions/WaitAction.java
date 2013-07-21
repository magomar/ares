package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionType;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WaitAction extends AbstractAction {

    public WaitAction(Unit unit) {
        this(unit, AS_SOON_AS_POSSIBLE, TIME_UNKNOWN);
    }

    public WaitAction(Unit unit, int start, int duration) {
        super(ActionType.WAIT, unit, start, duration);
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
