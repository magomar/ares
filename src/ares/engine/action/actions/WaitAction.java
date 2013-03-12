package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WaitAction extends AbstractAction {

    public WaitAction(Unit unit) {
        super(unit, ActionType.WAIT, AbstractAction.TIME_UNKNOWN);
    }

    /**
     *
     * @param unit
     * @param duration in time ticks
     */
    public WaitAction(Unit unit, int duration) {
        super(unit, ActionType.WAIT, duration);
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
