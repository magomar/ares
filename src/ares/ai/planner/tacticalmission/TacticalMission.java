package ares.ai.planner.tacticalmission;

import ares.ai.planner.UnitTaskNode;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface TacticalMission {
    
    /**
     * 
     * @return the estimated time to complete, in minutes
     */
    public int plan(UnitTaskNode taskNode);
    //TODO Include position tile
}
