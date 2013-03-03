package ares.engine.command;

import ares.engine.ClockEvent;
import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.scenario.Clock;
import ares.scenario.forces.Unit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds the details of a tactical mission assigned to a unit: mission type, current plan of action Includes the engine
 * to manage the live cycle of actions: create, schedule, execute
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TacticalMission {

    private static final Logger LOG = Logger.getLogger(TacticalMission.class.getName());
    /**
     * The type of the mission (assault, support by fire, etc.)
     *
     * @see TacticalMissionType
     */
    private final TacticalMissionType type;
    /**
     * The current, ongoing action
     */
    private Action currentAction;
    /**
     * A plan to accomplish the tactical mission. It is specified as a sequence of actions to be executed sequentially
     *
     * @see Action
     */
    private Deque<Action> pendingActions;

    public TacticalMission(TacticalMissionType type) {
        this.type = type;
        this.pendingActions = pendingActions = new LinkedList<>();
    }

    public void act(ClockEvent ce) {
        if (currentAction != null) {
            currentAction.execute();
        } else {
            LOG.log(Level.SEVERE, "Action = null for {0} at {1}", new Object[]{this, Clock.INSTANCE.toString()});
        }
//        if (ce.getEventTypes().contains(ClockEventType.DAY)) {
//            unit.recover();
//        }
    }

    public void schedule(Unit unit, ClockEvent ce //, ActionSpace space
            ) {
        if (currentAction != null
                && (currentAction.getState() == ActionState.COMPLETED
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            if (!pendingActions.isEmpty()) {
                Action nextAction = pendingActions.peek();
                if (nextAction.canBeStarted()) {
                    currentAction = pendingActions.poll();
                    currentAction.start();
//                    Tile destination = unit.getLocation();
//                    space.putAction(destination, currentAction);
                }
            } else {
                if (unit.canExecute(ActionType.WAIT)) {
                    currentAction = new WaitAction(unit, Clock.INSTANCE.getMINUTES_PER_TICK());
                } else {
                    currentAction = new RestAction(unit);
                }
            }
        }
    }

    public TacticalMissionType getType() {
        return type;
    }

    public void addAction(Action action) {
        pendingActions.add(action);
    }

//    public void setPendingActions(Queue<Action> pendingActions) {
//        this.pendingActions = (Deque<Action>) pendingActions;
//    }
    public void clearActions() {
        pendingActions.clear();
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public Queue<Action> getPendingActions() {
        return pendingActions;
    }

    public void addFirstAction(Action action) {
        pendingActions.addFirst(action);
    }

    public void addLastAction(Action action) {
        pendingActions.addLast(action);
    }

    @Override
    public String toString() {
        return  "type=" + type;
    }
}
