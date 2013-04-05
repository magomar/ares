package ares.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.engine.algorithms.pathfinding.PathFinder;
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
class GarrisonOperationalPlan extends OperationalPlan {

    public GarrisonOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
        super(OperationalStance.GARRISON, formation, objectives, emphasis, supportScope);
    }

    @Override
    public void plan(PathFinder pathFinder) {
        for (Unit unit : formation.getAvailableUnits()) {
            TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(unit, unit.getLocation(), pathFinder);
            unit.setMission(mission);
        }
    }
}
