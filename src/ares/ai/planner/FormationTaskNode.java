package ares.ai.planner;

import ares.scenario.forces.Formation;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Saúl Esteban
 */
public class FormationTaskNode {
    
    private FormationTask task;
    private List<UnitTaskNode> subtasks;
    private Formation formation;
    private Method maneuver;
    
    public FormationTaskNode(FormationTask ft, Formation f) {
        task = ft;
        formation = f;
        subtasks = new LinkedList<UnitTaskNode>();
    }
    
    public void setManeuver(Method m) {
        maneuver = m;
    }
    
    public FormationTask getTask() {
        return task;
    }
    
    public void addSubtask(UnitTaskNode t) {
        subtasks.add(t);
    }
    
    public List<UnitTaskNode> getSubtasks() {
        return subtasks;
    }
    
    public Formation getFormation() {
        return formation;
    }
    
    public Method getManeuver() {
        return maneuver;
    }
}
