package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.scenario.forces.Unit;

/**
 * Action to change that change between static (deployed) and mobile status. Actually, there are two types of action in
 * this category:
 *
 * {@link ActionType.ASSEMBLE} changes operational state to null {@link OpState.MOBILE}
 *
 * {@link ActionType.DEPLOY} changes operational state to {@link OpState.DEPLOYED}
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ChangeDeploymentAction extends AbstractAction {

    public static final int CHANGE_DEPLOYMENT_TIME = 15;

    public ChangeDeploymentAction(Unit unit, ActionType type) {
        super(unit, type, AbstractAction.AS_SOON_AS_POSSIBLE, (int) (CHANGE_DEPLOYMENT_TIME * unit.getEchelon().getSpeedModifier()));
    }
}
