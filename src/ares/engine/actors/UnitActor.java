package ares.engine.actors;

import ares.engine.action.AbstractAction;
import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.realtime.Clock;
import ares.engine.realtime.ClockEvent;
import ares.engine.realtime.ClockEventType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.PriorityQueue;
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

    public void schedule(Clock clock
//            , ActionSpace space
            ) {
        if (currentAction != null
                && (currentAction.getState() == ActionState.COMPLETED
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            Tile destination = unit.getLocation();
            if (!pendingActions.isEmpty()) {
                Action nextAction = pendingActions.peek();
                int actionStart = nextAction.getStart();
                if (actionStart < clock.getCurrentTime() + clock.MINUTES_PER_TICK) {
                    currentAction = pendingActions.poll();
//                    space.putAction(destination, currentAction);
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

    public void perceive(Clock clock) {
        // TODO PERCEPTION 
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
