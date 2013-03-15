package ares.engine.command.operational;

import ares.engine.action.Action;
import ares.engine.algorithms.routing.PathFinder;
import ares.engine.command.Objective;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
    }

    public void plan(PathFinder pathFinder) {
//        updateObjectives();
        if (!goals.isEmpty()) {
            Objective objective = goals.first();
            for (Unit unit : formation.getActiveUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(unit, objective.getLocation(), pathFinder);
//                mission.plan(pathFinder);
                unit.setMission(mission);
            }
        } else {
            for (Unit unit : formation.getActiveUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(unit, unit.getLocation(), pathFinder);
//                mission.plan(pathFinder);
                unit.setMission(mission);
            }
        }
    }

    private void updateObjectives() {
        for (Objective objective : objectives) {
            if (objective.isAchieved(formation)) {
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