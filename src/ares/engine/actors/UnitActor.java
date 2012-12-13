package ares.engine.actors;

import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.realtime.ClockEvent;
import ares.scenario.Clock;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class encapsulates the behavior of a unit. It provides a single access point to a unit. Different actors could
 * provide alternative unit behaviors.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitActor implements Actor {

    private static final Logger LOG = Logger.getLogger(UnitActor.class.getName());
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
        if (currentAction != null
                && (currentAction.getState() == ActionState.COMPLETED
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            if (!pendingActions.isEmpty()) {
                Action nextAction = pendingActions.peek();
                if (nextAction.checkTimetoStart() && nextAction.checkPrecondition()) {
                    currentAction = pendingActions.poll();
                    currentAction.start();
//                    Tile destination = unit.getLocation();
//                    space.putAction(destination, currentAction);
                }
            } else {
                if (unit.getEndurance() > ActionType.WAIT.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK())) {
                    currentAction = new WaitAction(this, Clock.INSTANCE.getMINUTES_PER_TICK());
                } else {
                    currentAction = new RestAction(this);
                }
            }
        }
    }

    public void perceive() {
        for (Tile tile : unit.getLocation().getNeighbors().values()) {
            tile.reconnoissance(unit, Clock.INSTANCE.getMINUTES_PER_TICK());
        }
    }

    public void act(ClockEvent ce) {
        if (currentAction != null) {
            currentAction.execute();
        } else {
            LOG.log(Level.WARNING, "Action = null for {0} at {1}", new Object[]{this, Clock.INSTANCE.toString()});
        }
//        if (ce.getEventTypes().contains(ClockEventType.DAY)) {
//            unit.recover();
//        }
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
