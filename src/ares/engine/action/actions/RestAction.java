package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.model.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RestAction extends AbstractAction {

    public RestAction(UnitActor actor, Tile destination, int start) {
        super(actor, ActionType.REST, destination, destination, start);
    }

    @Override
    public void execute(Clock clock) {
        start = Math.max(start, clock.getCurrentTime() - clock.MINUTES_PER_TICK);
        int duration = clock.MINUTES_PER_TICK;
        int wear = (int) (type.getWearRate() * duration);
        actor.getUnit().changeEndurance(wear);
        state = ActionState.COMPLETED;
        finish = clock.getCurrentTime();
//        System.out.println("[" + clock + "] -> " + this.toString());
    }

    @Override
    public String toString() {
        return actor.toString() + "RESTED " + " @ " + location.getX() + "," + location.getY()
                + " (" + start + "->" + finish + ")";
    }
}
