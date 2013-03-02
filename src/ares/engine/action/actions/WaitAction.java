package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WaitAction extends AbstractAction {

    public WaitAction(Unit unit, int duration) {
        super(unit, ActionType.WAIT, AbstractAction.AS_SOON_AS_POSSIBLE, duration);
    }
}
