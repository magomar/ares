package ares.ai.planner;

import ares.engine.command.TacticalMissionType;
import ares.scenario.forces.Unit;

/**
 *  
 * @author Saúl Esteban
 */
public enum Method {
    
    AIRBORNE_ASSAULT,
    AIR_ASSAULT,
    AMPHIBIOUS_ASSAULT,
    ENCIRCLEMENT,
    FRONTAL_ATTACK,
    INFILTRATION,
    PENETRATION,
    TURNING_MOVEMENT;
    
    private Method() {
        
    }
    
    public void useManeuver(FormationTaskNode tn) {
        
        if(tn.getManeuver() == Method.FRONTAL_ATTACK) {
            for(Unit unit : tn.getFormation().getActiveUnits()) {
                if(unit.getLocation().getX() == tn.getTask().getGoal().getX() && unit.getLocation().getY() == tn.getTask().getGoal().getY()) {
                    continue;
                }
                //TODO Set different missions for different types of units
//                UnitTask task = new UnitTask(TacticalMissionType.ASSAULT,tn.getTask().getGoal());
//                UnitTaskNode node = new UnitTaskNode(task,unit);
//                tn.addSubtask(node);
            }
        }
    }
}
