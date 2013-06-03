package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionType;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RestAction extends AbstractAction {

    /**
     * Default sleep time in time ticks
     */
    public static final int SLEEP_TIME = 8 * 60 / Clock.INSTANCE.getMINUTES_PER_TICK();

    public RestAction(Unit unit) {
        super(unit, ActionType.REST, SLEEP_TIME);
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
