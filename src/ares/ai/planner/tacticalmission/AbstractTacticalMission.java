package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractTacticalMission implements TacticalMission {
    protected Scenario scenario;

    public AbstractTacticalMission(Scenario scenario) {
        this.scenario = scenario;
    }
    
}
