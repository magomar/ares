package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.model.board.Direction;
import ares.model.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatAction extends MoveAction {

    public CombatAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start, Direction fromDir, int distance) {
        super(actor, type, origin, destination, start, fromDir, distance);
    }

    @Override
    public void execute(Clock clock) {
        System.out.println(actor + "Attacking");
    }
}
