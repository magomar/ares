package ares.engine.action.actions;

import ares.engine.realtime.Clock;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Force;
import ares.engine.messages.EngineMessageLogger;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class SurfaceMoveAction extends MoveAction {

    public SurfaceMoveAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start, Direction fromDir, int distance) {
        super(actor, type, origin, destination, start, fromDir, distance);
    }


    @Override
    public void execute(Clock clock) {
        if (checkPreconditions(clock)) {
            Unit unit = actor.getUnit();
            Force myForce = unit.getForce();
//            Collection<SurfaceUnit> unitsInDestination = destination.getSurfaceUnits();
            int duration;
            if (!myForce.equals(destination.getOwner()) && destination.getSurfaceUnits().size() > 0) {
                state = ActionState.ABORTED;
                duration = clock.MINUTES_PER_TICK;
                finish = clock.getCurrentTime();
                unit.setOpState(type.getPrecondition());
                
                System.out.println("[" + clock + "] -> " + "ABORTED " + this.toString());
            } else {
                if (timeToComplete > clock.MINUTES_PER_TICK) {
                    duration = clock.MINUTES_PER_TICK;
                    timeToComplete -= duration;
//                    System.out.println("[" + clock + "] -> " + "ONGOING " + this.toString());
                } else {
                    duration = timeToComplete;
                    timeToComplete = 0;
                    state = ActionState.COMPLETED;
                    finish = clock.getCurrentTime() - clock.MINUTES_PER_TICK + duration;
                    location.remove(unit);
                    destination.add(unit);
                    unit.setLocation(destination);
                    unit.setOpState(type.getEffectAfter());
                    System.out.println("[" + clock + "] -> " + "COMPLETED " + this.toString());
//                    EngineMessageLogger.info(this.toString());
                }
            }
            int wear = (int) (type.getWearRate() * duration);
            unit.changeEndurance(wear);
        } else {
//            System.out.println("[" + clock + "] -> " + "DELAYED " + this.toString());
        }
    }


}
