package ares.engine.command;

import ares.engine.action.Action;
import ares.engine.action.ActionSpace;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.scenario.forces.Unit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds the details of a tactical mission assigned to a unit: mission type, current plan of action Includes the engine
 * to manage the live cycle of actions: create, start, executeAction
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

    public void commit(ActionSpace actionSpace) {
        currentAction.commit();
    }

    /**
     * Executes {@link #currentAction}
     *
     * @param event
     */
    public void executeAction() {
        currentAction.execute();
    }

    /**
     * Checks the currently scheduled action ({@link #currentAction}). If no action is scheduled it either schedules a
     * new action from {@link #pendingActions}, or creates and schedules a new {@link Action} (a {@link WaitAction} or a
     * {@link  RestAction}. It returns the currently scheduled action.
     *
     * @return the currently scheduled action ({@link #currentAction})
     */
    public Action scheduleAction() {
        // check currentAction and fix if needed
        if (currentAction != null) {
            switch (currentAction.getState()) {
                case COMPLETED:
                case ABORTED:
                    currentAction = null;
                    break;
                default:
                    if (!currentAction.canBeExecuted()) {
                        if (currentAction.getType() != ActionType.WAIT) pendingActions.push(currentAction);
                        currentAction = null;
                        break;
                    }
                    if (currentAction.getType() == ActionType.WAIT && hasExecutablePendingAction()) {
                        currentAction = scheduleNextAction();
                        break;
                    }
            }
        }
        if (currentAction == null) {
            if (hasExecutablePendingAction()) {
                currentAction = scheduleNextAction();
            } else {
                if (unit.canEndure(ActionType.WAIT)) {
                    currentAction = new WaitAction(unit);
                } else {
                    currentAction = new RestAction(unit);
                }
            }
        }
        return currentAction;
    }

    private Action scheduleNextAction() {
        Action nextAction = pendingActions.poll();
        if (nextAction.getState() == ActionState.CREATED) { // non started yet
            nextAction.start();
        }
        return nextAction;

    }

    private boolean hasExecutablePendingAction() {
        return !pendingActions.isEmpty() && pendingActions.peek().canBeExecuted();
    }

    public TacticalMissionType getType() {
        return type;
    }

    public void addAction(Action action) {
        pendingActions.add(action);
    }

    public void clearActions() {
        pendingActions.clear();
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public Queue<Action> getPendingActions() {
        return pendingActions;
    }

    /**
     * Pushes the given action to the head of the {@link pendingActions} queue
     *
     * @see Deque#push(java.lang.Object)
     * @param action
     */
    public void pushAction(Action action) {
        pendingActions.push(action);
    }

    /**
     * Adds the given action to the tail of the {@link pendingActions} queue
     *
     * @see Deque#offer(java.lang.Object)
     * @param action
     */
    public void offerAction(Action action) {
        pendingActions.offer(action);
    }

    @Override
    public String toString() {
        return "TacticalMission{" + type + " by " + unit.getName() + '}';
    }
}
