package ares.engine;

import ares.engine.realtime.ClockEvent;
import ares.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Engine {

    public Scenario getScenario();

    public void update(ClockEvent clockEvent);
}
