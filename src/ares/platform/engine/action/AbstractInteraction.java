package ares.platform.engine.action;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public abstract class AbstractInteraction implements Interaction {
    protected List<Action> actions = new ArrayList<>();

    public AbstractInteraction(Action action) {
        addAction(action);
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }
}
