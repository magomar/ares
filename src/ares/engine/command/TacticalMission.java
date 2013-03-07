package ares.engine.command;

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

/**
 * Holds the details of a tactical mission assigned to a unit: mission type, current plan of action Includes the engine
 * to manage the live cycle of actions: create, start, execute
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TacticalMission {

    /**
     * The unit assined to this tactical misssion
     */
    private final Unit unit;
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
     * A plan to accomplish this tactical mission. It is specified as a sequence of actions to be executed sequentially
     *
     * @see Action
     */
    private Deque<Action> pendingActions;

    public TacticalMission(TacticalMissionType type, Unit unit) {
        this.type = type;
        this.unit = unit;
        this.pendingActions = pendingActions = new LinkedList<>();
    }

    public void commit() {
        currentAction.commit();
    }

    /**
     * Executes {@link #currentAction}
     *
     * @param event
     */
    public void execute() {
        currentAction.execute();
    }

    /**
     * Checks the currently scheduled action ({@link #currentAction}). If no action is scheduled it either schedules a
     * new action from {@link #pendingActions}, or creates and schedules a new {@link Action} (a {@link WaitAction} or a
     * {@link  RestAction}. It returns the currently scheduled action.
     *
     * @return the currently scheduled action ({@link #currentAction})
     */
    public Action schedule() {
        if (currentAction != null
                && (currentAction.getState() == ActionState.COMPLETED
                || currentAction.getState() == ActionState.ABORTED)) {
            currentAction = null;
        }
        if (currentAction == null) {
            if (!pendingActions.isEmpty() && pendingActions.peek().canBeStarted()) {
                currentAction = pendingActions.poll();
            } else {
                if (unit.canExecute(ActionType.WAIT)) {
                    currentAction = new WaitAction(unit);
                } else {
                    currentAction = new RestAction(unit);
                }
            }
//            currentAction.start();
        }
        return currentAction;
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
        return "TacticalMission{" + type + " by " + unit.getName() + '}';
    }
}
