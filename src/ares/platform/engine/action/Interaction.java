package ares.platform.engine.action;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public interface Interaction {
    void addAction(Action action);

    void execute();
}
