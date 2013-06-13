package ares.platform.engine.command.tactical;

import ares.platform.engine.action.Action;
import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.action.ActionState;
import ares.platform.engine.action.ActionType;
import ares.platform.engine.action.actions.MoveAction;
import ares.platform.engine.action.actions.RestAction;
import ares.platform.engine.action.actions.WaitAction;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds the details of a tactical mission assigned to a unit: mission type, current plan of action Includes the engine
 * to manage the live cycle of actions: create, start, executeAction
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class TacticalMission {

    /**
     * The unit assined to this tactical misssion
     */
    protected final Unit unit;
    /**
     * The type of the mission (assault, support by fire, etc.)
     *
     * @see TacticalMissionType
     */
    protected TacticalMissionType type;
    /**
     * The tile targeted by this mission. The objective of the mission is related to this tile. For example, for an
     * {@link TacticalMissionType#OCCUPY} mission this tile has to be taken, for a
     * {@link TacticalMissionType#ATTACK_BY_FIRE} this tile has to be fired at.
     */
    protected Tile targetTile;
//    protected Unit targetUnit;
    /**
     * The current, ongoing action
     */
    protected Action currentAction;
    /**
     * A plan to accomplish this tactical mission. It is specified as a sequence of actions to be executed sequentially
     *
     * @see Action
     */
    protected Deque<Action> pendingActions;

    public TacticalMission(TacticalMissionType type, Unit unit, Tile target) {
        this.type = type;
        this.unit = unit;
        this.targetTile = target;
        this.pendingActions = new LinkedList<>();
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
    }

//    public void setTargetUnit(Unit targetUnit) {
//        this.targetUnit = targetUnit;
//    }
    public void setType(TacticalMissionType type) {
        this.type = type;
    }

    public abstract void plan(Pathfinder pathFinder);

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
                        if (currentAction.getType() != ActionType.WAIT) {
                            // if current action is not wait and cannot be executed, then delay it
                            currentAction.delay();
                            pendingActions.addFirst(currentAction);
                        }
                        currentAction = null;
                        break;
                    }
                    if (currentAction.getType() == ActionType.WAIT && hasExecutablePendingAction()) {
                        // Wait actions are abandoned and replaced by pending actions if possible
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

//    public void clearActions() {
//        pendingActions.clear();
//    }
    public Action getCurrentAction() {
        return currentAction;
    }

    public Queue<Action> getPendingActions() {
        return pendingActions;
    }

    /**
     * Adds the given action to the head of {@link pendingActions}
     *
     * @param action
     */
    public void addFirstAction(Action action) {
        pendingActions.addFirst(action);
    }

    /**
     * Adds the given action to the tail of {@link pendingActions}
     *
     * @param action
     */
    public void addLastAction(Action action) {
        pendingActions.addLast(action);
    }

    @Override
    public String toString() {
        return type.name() + ' ' + targetTile;
    }

    public String toStringMultiline() {
        StringBuilder sb = new StringBuilder();
        sb.append(type.name()).append(' ').append(targetTile).append('\n');
        sb.append("Action: ").append(currentAction).append('\n');
        sb.append("Pending: ").append(pendingActions).append('\n');
        return sb.toString();
    }

    public Path getPath() {
        Path path = null;
        Action action = currentAction;
        if (action instanceof MoveAction) {
            path = ((MoveAction) action).getPath();
        } 
        
        else {
            Action nextAction = pendingActions.peek();
            if (nextAction instanceof MoveAction) {
                path = ((MoveAction) nextAction).getPath();
            }
        }
        return path;
    }
}
