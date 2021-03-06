package ares.platform.engine.command.operational.plans;

import ares.data.wrappers.scenario.Emphasis;
import ares.data.wrappers.scenario.SupportScope;
import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.command.Objective;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.scenario.forces.Formation;
import ares.platform.scenario.forces.Unit;

import java.util.List;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
class FixedOperationalPlan extends OperationalPlan {

    public FixedOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
        super(OperationalStance.FIXED, formation, objectives, emphasis, supportScope);
    }

    @Override
    public void plan(Pathfinder pathFinder) {
        for (Unit unit : formation.getAvailableUnits()) {
            TacticalMission mission = TacticalMissionType.OCCUPY.buildTacticalMission(unit, unit.getLocation(), pathFinder);
            unit.setMission(mission);
        }
    }
}
