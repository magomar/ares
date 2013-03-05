package ares.engine.action;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Counter {

    ACTION_COUNTER;
    private final AtomicInteger COUNTER = new AtomicInteger();

    public int count() {
        return COUNTER.getAndIncrement();
    }
}
