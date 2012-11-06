package ares.ai.planner;

import ares.engine.command.OperationForm;
import ares.ai.planner.maneuver.Maneuver;
import ares.ai.planner.maneuver.ManeuverType;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class FormationTaskAnalyzer {
    
    public FormationTaskAnalyzer() {
    }
    
    public void analyzeTask(FormationTaskNode tn) {
//        if(tn.getTask().getOperationForm() == OperationForm.ASSAULT) {
//            //TODO analyze preconditions and decide maneuver type
//            tn.setManeuver(Method.FRONTAL_ATTACK);
//        }
        if(tn.getTask().getGoal().getSurfaceUnits().size() > 0) {
            if(!tn.getTask().getGoal().getOwner().equals(tn.getFormation().getForce())) {
                Maneuver maneuver;
                for(Maneuver m : browseManeuvers(tn.getTask().getOperationForm())) {
                    m.setData(tn);
                    if(m.checkPreconditions() > 0) {
                        maneuver = m;
                        maneuver.plan();
                        break;
                    }
                }
            }
        }
        
    }
    
    public LinkedList<Maneuver> browseManeuvers(OperationForm of) {
        
        LinkedList<Maneuver> suitable = new LinkedList<>();
        Maneuver maneuver;
        
        for(ManeuverType type : ManeuverType.values()) {
            try {
                maneuver = (Maneuver)type.getManeuverClass().newInstance();
                if(maneuver.getOperations().contains(of)) {
                    suitable.add(maneuver);
                }
            }
            catch(InstantiationException | IllegalAccessException e) {
                System.out.println("Exception "+e);
            }
        }
        return suitable;
    }
}
