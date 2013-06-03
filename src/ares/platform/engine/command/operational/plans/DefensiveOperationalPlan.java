package ares.platform.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.platform.engine.algorithms.pathfinding.PathFinder;
import ares.platform.engine.command.Objective;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.scenario.forces.Formation;
import ares.platform.scenario.forces.Unit;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
class DefensiveOperationalPlan extends OperationalPlan {

    public DefensiveOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
        super(OperationalStance.DEFENSIVE, formation, objectives, emphasis, supportScope);
    }

    @Override
    public void plan(PathFinder pathFinder) {
        if (!goals.isEmpty()) {
            Objective objective = goals.first();
            for (Unit unit : formation.getAvailableUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(unit, objective.getLocation(), pathFinder);
                unit.setMission(mission);
            }
        } else {
            for (Unit unit : formation.getAvailableUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(unit, unit.getLocation(), pathFinder);
                unit.setMission(mission);
            }
        }
    }
}
