package ares.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.engine.algorithms.routing.PathFinder;
import ares.engine.command.Objective;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
class OffensiveOperationalPlan extends OperationalPlan {

    public OffensiveOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
        super(OperationalStance.OFFENSIVE, formation, objectives, emphasis, supportScope);
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
