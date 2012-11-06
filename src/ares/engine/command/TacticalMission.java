package ares.engine.command;

import ares.engine.action.AbstractAction;
import ares.engine.action.Action;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
// TODO this class overlaps with Saul's TacticalMission in planner
// Check with him to integrate new platform with his planner
public class TacticalMission {

    /**
     * The type of the mission (assault, support by fire, etc.)
     *
     * @see TacticalMissionType
     */
    private final TacticalMissionType type;
    /**
     * A plan to accomplish this tactical mission. It is specified as a sequence of actions
     *
     * @see Action
     */
    private Queue<Action> pendingActions;

    public TacticalMission(TacticalMissionType type) {
        this.type = type;
        this.pendingActions = new PriorityQueue<>(2, AbstractAction.ACTION_START_COMPARATOR);
    }

    public TacticalMissionType getType() {
        return type;
    }

    public Queue<Action> getPendingActions() {
        return pendingActions;
    }

    public void addAction(Action action) {
        pendingActions.add(action);
    }

    public void setPendingActions(Queue<Action> pendingActions) {
        this.pendingActions = pendingActions;
    }

    public void clearActions() {
        pendingActions.clear();
    }
}
