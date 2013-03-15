package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.time.Clock;
import ares.scenario.TurnLength;
import ares.scenario.forces.Unit;

/**
 * Action to alternate between static (deployed) and mobile status. Actually, there are two types of action in
 * this category:
 *
 * {@link ActionType.ASSEMBLE} changes operational state to null {@link OpState.MOBILE}
 *
 * {@link ActionType.DEPLOY} changes operational state to {@link OpState.DEPLOYED}
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ChangeDeploymentAction extends AbstractAction {

    public static final int CHANGE_DEPLOYMENT_TIME = TurnLength.FULL_WEEK.getMinutesPerTick(); // Base deployment time

    public ChangeDeploymentAction(Unit unit, ActionType type) {
        super(unit, type, (int) (unit.getEchelon().getModifiedTime(CHANGE_DEPLOYMENT_TIME) / Clock.INSTANCE.getMINUTES_PER_TICK()));
    }

    public ChangeDeploymentAction(Unit unit, int start, ActionType type) {
        super(unit, start, type, (int) (unit.getEchelon().getModifiedTime(CHANGE_DEPLOYMENT_TIME) / Clock.INSTANCE.getMINUTES_PER_TICK()));
    }

    @Override
    protected void applyOngoingEffects() {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
