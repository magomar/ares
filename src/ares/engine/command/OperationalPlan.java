package ares.engine.command;

import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class OperationalPlan {

    protected OperationType type;
    protected OperationForm form;
    protected Formation formation;
    private List<Objective> objectives;
    private SortedSet<Objective> goals;
    private Map<Unit, TacticalMission> missions;

    public OperationalPlan(OperationType type, Formation formation, List<Objective> objectives) {
        this.type = type;
        this.formation = formation;
        this.objectives = objectives;
        goals = new TreeSet<>();
        goals.addAll(objectives);
        for (Objective objective : objectives) {
            goals.add(objective);
        }
    }

    public void updateObjectives() {
        for (Objective objective : objectives) {
            if (objective.isAchieved(formation.getForce())) {
                if (!objective.isAchieved()) {
                    objective.setAchieved(true);
                    goals.remove(objective);
                }
            } else {
                if (objective.isAchieved()) {
                    objective.setAchieved(false);
                    goals.add(objective);
                }
            }
        }
    }

    public Formation getFormation() {
        return formation;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public SortedSet<Objective> getGoals() {
        return goals;
    }

    public OperationType getType() {
        return type;
    }

    public OperationForm getForm() {
        return form;
    }

    public Map<Unit, TacticalMission> getMissions() {
        return missions;
    }
    
    public boolean hasGoals() {
        return !goals.isEmpty();
    }
}