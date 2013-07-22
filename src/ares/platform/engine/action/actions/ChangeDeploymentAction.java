package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.action.ActionType;
import ares.platform.scenario.TurnLength;
import ares.platform.scenario.forces.Unit;

/**
 * Action to alternate between static (deployed) and mobile status. Actually, there are two types of action in
 * this category:
 * <p/>
 * {@link ActionType#ASSEMBLE} changes operational state to null {@link ares.platform.scenario.forces.OpState#MOBILE}
 * <p/>
 * {@link ActionType#DEPLOY} changes operational state to {@link ares.platform.scenario.forces.OpState#DEPLOYED}
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ChangeDeploymentAction extends AbstractAction {

    public static final int CHANGE_DEPLOYMENT_TIME = TurnLength.FULL_WEEK.getMinutesPerTick(); // Base deployment time

    public ChangeDeploymentAction(ActionType actionType, Unit unit) {
        this(actionType, unit, AS_SOON_AS_POSSIBLE);
    }

    public ChangeDeploymentAction(ActionType actionType, Unit unit, int start) {
        super(actionType, unit, start, (int) unit.getEchelon().getModifiedTime(CHANGE_DEPLOYMENT_TIME));
    }

    @Override
    protected void applyOngoingEffects(ActionSpace actionSpace) {
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
