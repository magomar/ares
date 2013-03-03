package ares.engine.command;

import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class OperationalPlan {

    protected OperationType type;
    protected OperationForm form;
    protected boolean hasPlan;
    protected Formation formation;
    private List<Objective> objectives;

    private Map<Unit, TacticalMission> missions;

    public OperationalPlan(OperationType type, Formation formation, List<Objective> objectives) {
        this.type = type;
        this.formation = formation;
        this.objectives = objectives;
        hasPlan = false;
    }

    public void updateObjectives() {
        for (Objective objective : objectives) {
            if (objective.checkAchieved(formation.getForce())) {
                objective.setAchieved(true);
            }
        }
    }
    public Formation getFormation() {
        return formation;
    }

    public List<Objective> getObjectives() {
        return objectives;
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

    public boolean hasPlan() {
        return hasPlan;
    }

}