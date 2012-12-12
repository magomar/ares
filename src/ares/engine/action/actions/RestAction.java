package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RestAction extends AbstractAction {
    /**
     * Default sleep time in minutes
     */
    public static final int SLEEP_TIME = 8 * 60;

    public RestAction(UnitActor actor) {
        super(actor, ActionType.REST, AbstractAction.AS_SOON_AS_POSSIBLE, SLEEP_TIME);
    }

}
