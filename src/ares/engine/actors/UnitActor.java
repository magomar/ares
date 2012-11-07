package ares.engine.actors;

import ares.engine.action.AbstractAction;
import ares.engine.action.Action;
import ares.engine.action.ActionSpace;
import ares.engine.action.ActionState;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.realtime.Clock;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class encapsulates the behavior of a unit. It provides a single access point to a unit.
 * Different adaptors could provide alternative unit behaviors.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitActor implements Actor {

    private Unit unit;
    /**
     * The current, ongoing action
     */
    private Action currentAction;
    /**
     * A list of actions to be executed sequentially (a queue)
     *
     * @see Action
     */
    private Queue<Action> pendingActions;

    public UnitActor(Unit unit) {
        this.unit = unit;
        // Probably we do not need to use a priority queue, a linkedlist will suffice...
        this.pendingActions = new PriorityQueue<>(2, AbstractAction.ACTION_START_COMPARATOR);
    }

    public void addAction(Action action) {
        pendingActions.add(action);
    }

    public void schedule(Clock clock, ActionSpace space) {
        if (currentAction != null && 
                (currentAction.getState() == ActionState.COMPLETED 
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            Tile destination = currentAction.getDestination();
            if (!pendingActions.isEmpty()) {
                Action nextAction = pendingActions.peek();
                int actionStart = nextAction.getStart();
                if (actionStart < clock.getCurrentTime() + clock.MINUTES_PER_TICK) {
                    currentAction = pendingActions.poll();
                    space.putAction(destination, currentAction);
                }
            } else {
                int time = clock.getCurrentTime();
                if (unit.getEndurance() > 0) {
                    currentAction = new WaitAction(this, destination, time);
                } else {
                    currentAction = new RestAction(this, destination, time);
                }
            }
        }
    }
//    public ActionState executeAction(Action action, Clock clock) {
//        if (action != null && action.getState() != ActionState.COMPLETED && action.getState() != ActionState.ABORTED) {
//            action.execute(clock);
//
//        }
//        return action.getState();
//    }

    public void act(Clock clock) {
        if (currentAction != null) {
            currentAction.execute(clock);

        }
//        if (clock.isNewDay()) {
//            quality = (2 * proficiency + readiness) / 3;
//            efficacy = (2 * proficiency + readiness + supply) / 4;
//            int enduranceRestored = (MAX_ENDURANCE * 200 + MAX_ENDURANCE * readiness + MAX_ENDURANCE * supply) / 400;
//            endurance = Math.min(endurance + enduranceRestored, enduranceRestored);
//            stamina = endurance * 100 / MAX_ENDURANCE;
//            maxRange = speed * MAX_ENDURANCE / 90 / 1000;
//            range = speed * endurance / 90 / 1000;
//            Scale scale = scenario.getScale();
//            attackStrength = (int) (efficacy * (antiTank + antiPersonnel) / scale.getArea());
//            defenseStrength = (int) (efficacy * defense / scale.getArea());
//            health = (int) (efficacy - 1 / 20);
//        }
    }

//    public TacticalMission getTacticalMission() {
//        return tacticalMission;
//    }
    public Unit getUnit() {
        return unit;

    }

    public Action getAction() {
        return currentAction;
    }
}
