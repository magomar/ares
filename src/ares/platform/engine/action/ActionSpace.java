package ares.platform.engine.action;

import ares.platform.scenario.board.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ActionSpace {

    /**
     * Map of actions grouped by location. This map associates map tiles with actions happening at that location
     */
    private final Map<Tile, List<Action>> actionMap;
    /**
     * List of scheduled actions
     */
    private final List<Action> scheduledActions;
    /**
     * List of already started actions
     */
    private final List<Action> startedActions;

    public ActionSpace() {
        scheduledActions = new ArrayList<>();
        startedActions = new ArrayList<>();
        actionMap = new HashMap<>();
    }

    public void addAction(Action action) {
        scheduledActions.add(action);
    }

    public void putAction(Tile location, Action action) {
        if (actionMap.containsKey(location)) {
            actionMap.get(location).add(action);
        } else {
            List<Action> moreActions = new ArrayList<>();
            moreActions.add(action);
            actionMap.put(location, moreActions);
        }
    }

    public void resolveInteractions() {
        for (Action action : scheduledActions) {
            if (action.getState() == ActionState.CREATED) {
            }
        }
        for (Map.Entry<Tile, List<Action>> entry : actionMap.entrySet()) {
            Tile tile = entry.getKey();
            List<Action> list = entry.getValue();

        }
    }
}
