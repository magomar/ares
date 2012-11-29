package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatAction extends MoveAction {

    public CombatAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start, int distance) {
        super(actor, type, origin, destination, start, distance);
    }

    @Override
    public void execute(Clock clock) {
        System.out.println(actor + "Attacking");
    }
}
