package ares.engine.actors;

import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.realtime.Clock;
import ares.engine.realtime.ClockEvent;
import ares.engine.realtime.ClockEventType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class encapsulates the behavior of a unit. It provides a single access point to a unit. Different actors could
 * provide alternative unit behaviors.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitActor implements Actor {

    /**
     * The unit handled by this actor
     */
    private Unit unit;
//    /**
//     * The tactical Artificial Intelligence responsible of reacting to scenario events
//     */
//    private UnitTacAI tacAI;
    /**
     * The current, ongoing action
     */
    private Action currentAction;
    /**
     * A collection of actions to be executed sequentially
     *
     * @see Action
     */
    private Deque<Action> pendingActions;

    public UnitActor(Unit unit) {
        this.unit = unit;
        pendingActions = new LinkedList<>();
    }

    public void addFirstAction(Action action) {
        pendingActions.addFirst(action);
    }

    public void addLastAction(Action action) {
        pendingActions.addLast(action);
    }

    public void schedule(ClockEvent ce //, ActionSpace space
            ) {
        Clock clock = ce.getClock();
        if (currentAction != null
                && (currentAction.getState() == ActionState.COMPLETED
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            if (!pendingActions.isEmpty()) {
                Action nextAction = pendingActions.peek();
                if (nextAction.checkTimetoStart(clock) && nextAction.checkPrecondition()) {
                    currentAction = pendingActions.poll();
                    currentAction.start(clock);
//                    Tile destination = unit.getLocation();
//                    space.putAction(destination, currentAction);
                }
            } else {
                if (unit.getEndurance() > ActionType.WAIT.getRequiredEndurace(clock.MINUTES_PER_TICK)) {
                    currentAction = new WaitAction(this, clock.MINUTES_PER_TICK);
                } else {
                    currentAction = new RestAction(this);
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
    public void perceive(Clock clock) {
        for (Tile tile : unit.getLocation().getNeighbors().values()) {
            tile.reconnoissance(unit, clock.MINUTES_PER_TICK);
        }
    }

    public void act(ClockEvent ce) {
        Clock clock = ce.getClock();
        if (currentAction != null) {
            currentAction.execute(clock);
        }
        if (ce.getEventTypes().contains(ClockEventType.DAY)) {
            unit.recover();
        }
    }

//    public TacticalMission getTacticalMission() {
//        return tacticalMission;
//    }
    public Unit getUnit() {
        return unit;

    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public Queue<Action> getPendingActions() {
        return pendingActions;
    }

    @Override
    public String toString() {
//        return "UnitActor{" + "unit=" + unit + ", currentAction=" + currentAction + ", pendingActions=" + pendingActions + '}';
        return unit.toString();
    }
}
