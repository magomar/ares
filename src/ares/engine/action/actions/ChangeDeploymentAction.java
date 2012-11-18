package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.scenario.board.Tile;

/**
 * Actions that change between static (deployed) and mobile status. There are two types of actions in this category:
 * Assemble and Deploy
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ChangeDeploymentAction extends AbstractAction {

    public ChangeDeploymentAction(UnitActor actor, ActionType type, Tile destination, int start) {
        super(actor, type, destination, destination, start);
        finish = (int) (start + 15 * actor.getUnit().getEchelon().getSpeedModifier());
        timeToComplete = finish - start;
    }

    @Override
    public void execute(Clock clock) {
        if (checkPreconditions(clock)) {
            int duration;
            if (timeToComplete > clock.MINUTES_PER_TICK) {
                duration = clock.MINUTES_PER_TICK;
                timeToComplete -= duration;
                System.out.println("[" + clock + "] -> " + "ONGOING " + this.toString());
            } else {
                duration = timeToComplete;
                timeToComplete = 0;
                state = ActionState.COMPLETED;
                finish = clock.getCurrentTime() - clock.MINUTES_PER_TICK + duration;
                actor.getUnit().setOpState(type.getEffectAfter());
                int wear = (int) (type.getWearRate() * duration);
                actor.getUnit().changeEndurance(wear);
                System.out.println("[" + clock + "] -> " + "COMPLETED " + this.toString());
            }
        } else {
            System.out.println("[" + clock + "] -> " + "DELAYED " + this.toString());
        }
    }

    @Override
    public String toString() {
        return actor.toString() + "ChangeDeployment " + " @ " + location + " from " + type.getPrecondition() + " to"
                + type.getEffectAfter() + " (" + start + "->" + finish + ")";
    }
}
