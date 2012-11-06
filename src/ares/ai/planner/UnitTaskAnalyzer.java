package ares.ai.planner;

import ares.engine.realtime.Clock;
import ares.engine.algorithms.PathFinder;
import ares.ai.planner.tacticalmission.TacticalMission;

/**
 *
 * @author Saúl Esteban
 */
public class UnitTaskAnalyzer {
    
    Clock clock;
    PathFinder pathFinder;
    
    public UnitTaskAnalyzer(Clock c) {
        clock = c;
        pathFinder = new PathFinder();
    }
    
    public void analyzeTask(UnitTaskNode tn) {
        
        TacticalMission mission;
        try {
            mission = (TacticalMission)tn.getTask().getTacticalMissionType().getTacticalMissionClass().newInstance();
            mission.plan(tn);
        }
        catch(InstantiationException | IllegalAccessException e) {
            System.out.println("Exception "+e);
        }
    }
}