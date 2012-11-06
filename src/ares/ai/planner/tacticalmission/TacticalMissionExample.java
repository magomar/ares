/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;
import ares.ai.planner.UnitTaskNode;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TacticalMissionExample extends AbstractTacticalMission {

    public TacticalMissionExample(Scenario scenario) {
        super(scenario);
    }

    @Override
    public int plan(UnitTaskNode taskNode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
