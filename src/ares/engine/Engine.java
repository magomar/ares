package ares.engine;

import ares.engine.realtime.ClockEvent;
import ares.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Engine {

    public void start();

    public void stop();
    
    public void update(ClockEvent clockEvents);
    
    public Scenario getScenario();
}
