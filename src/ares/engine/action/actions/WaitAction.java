package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WaitAction extends AbstractAction {

    public WaitAction(UnitActor actor, int duration) {
        super(actor, ActionType.WAIT, AbstractAction.AS_SOON_AS_POSSIBLE, duration);
    }
}
