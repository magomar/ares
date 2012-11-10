package ares.engine.action;

import ares.model.board.Tile;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ActionSpace {

    Map<Tile, Queue<Action>> actionMap;

    public ActionSpace() {
        actionMap = new HashMap<>();
    }

    public void putAction(Tile location, Action action) {

        if (actionMap.containsKey(location)) {
            actionMap.get(location).add(action);
        } else {
            Queue<Action> newActionQueue = new PriorityQueue<>();
            newActionQueue.add(action);
            actionMap.put(location, newActionQueue);
        }
    }
}
