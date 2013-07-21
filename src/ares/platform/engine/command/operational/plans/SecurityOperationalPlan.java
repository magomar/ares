/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
class SecurityOperationalPlan extends OperationalPlan {

    public SecurityOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
        super(OperationalStance.SECURITY, formation, objectives, emphasis, supportScope, actionSpace);
    }

    @Override
    public void plan(Pathfinder pathFinder) {
        if (!goals.isEmpty()) {
            Objective objective = goals.first();
            for (Unit unit : formation.getAvailableUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.buildTacticalMission(unit, objective.getLocation(), pathFinder, actionSpace);
                unit.setMission(mission);
            }
        } else {
            for (Unit unit : formation.getAvailableUnits()) {
                TacticalMission mission = TacticalMissionType.OCCUPY.buildTacticalMission(unit, unit.getLocation(), pathFinder, actionSpace);
                unit.setMission(mission);
            }
        }
    }
}
