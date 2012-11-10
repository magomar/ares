package ares.engine.action.actions;

import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.model.board.Direction;
import ares.model.board.Tile;
import ares.model.forces.AirUnit;
import ares.model.forces.Unit;
import java.util.List;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AirMoveAction extends MoveAction {

    public AirMoveAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start, Direction fromDir, int distance) {
        super(actor, type, origin, destination, start, fromDir, distance);
    }

    @Override
    public void execute(Clock clock) {
        if (checkPreconditions(clock)) {
            Unit unit = actor.getUnit();
            List<AirUnit> unitsInDestination = (List<AirUnit>) destination.getAirUnits();
            int duration;
            if (unitsInDestination.size() > 0 && !unitsInDestination.get(0).getForce().equals(unit.getForce())) {
                duration = clock.MINUTES_PER_TICK;
                finish = clock.getCurrentTime();
                state = ActionState.ABORTED;
                System.out.println("[" + clock + "] -> " + "ABORTED " + this.toString());
            } else {
                if (timeToComplete > clock.MINUTES_PER_TICK) {
                    duration = clock.MINUTES_PER_TICK;
                    timeToComplete -= duration;
                } else {
                    duration = timeToComplete;
                    timeToComplete = 0;
                    state = ActionState.COMPLETED;
                    finish = clock.getCurrentTime() - clock.MINUTES_PER_TICK + duration;
                    location.remove(unit);
                    destination.add(unit);
                    unit.setLocation(destination);
                    System.out.println("[" + clock + "] -> " + "COMPLETED " + this.toString());
                }
            }
            int wear = (int) (type.getWearRate() * duration);
            unit.changeEndurance(wear);
        }
    }

    @Override
    public String toString() {
        return actor.toString() + " from " + location + " to " + destination + " at " + (speed * 60.0 / 1000) + " km/h";
    }
}
