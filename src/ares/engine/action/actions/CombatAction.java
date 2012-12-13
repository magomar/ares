package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.algorithms.routing.Path;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatAction extends MoveAction {

    public CombatAction(UnitActor actor, ActionType type, Path path) {
        super(actor, type, path);
    }

}
