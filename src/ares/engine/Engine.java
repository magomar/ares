package ares.engine;

import ares.engine.realtime.ClockEvent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Engine {

    public void start();

    public void stop();
    
    public void update(ClockEvent clockEvents);
}
