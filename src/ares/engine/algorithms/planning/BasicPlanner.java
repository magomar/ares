package ares.engine.algorithms.planning;

import ares.engine.RealTimeEngine;
import ares.engine.action.Action;
import ares.engine.action.ActionType;
import ares.engine.action.actions.ChangeDeploymentAction;
import ares.engine.action.actions.MoveAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.algorithms.routing.Path;
import ares.engine.command.Objective;
import ares.engine.command.OperationalPlan;
import ares.engine.command.TacticalMission;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class BasicPlanner implements Planner {

    private static final Logger LOG = Logger.getLogger(BasicPlanner.class.getName());
    private final RealTimeEngine engine;

    public BasicPlanner(RealTimeEngine engine) {
        this.engine = engine;
    }

    @Override
    public void plan(Formation formation) {
        OperationalPlan plan = formation.getOperationalPlan();
        List<Objective> objectives = plan.getObjectives();
        if (objectives.isEmpty()) {
            for (Unit unit : formation.getActiveUnits()) {
                Queue<Action> pendingActions = unit.getMission().getPendingActions();
                if (pendingActions.isEmpty()) {
                    tacticalPlan(unit);
                }
            }
        } else {
            plan.updateObjectives();
            Objective objective = plan.getGoals().first();
            for (Unit unit : formation.getActiveUnits()) {
                Queue<Action> pendingActions = unit.getMission().getPendingActions();
                if (pendingActions.isEmpty()) {
                    tacticalPlan(unit, objective);
                }
            }
        }
    }

    private boolean tacticalPlan(Unit unit) {
        WaitAction wait = new WaitAction(unit);
        TacticalMission mission = unit.getMission();
        mission.addFirstAction(wait);
        return true;
    }

    @Override
    public boolean tacticalPlan(Unit unit, Objective objective) {
        Path path = engine.getPathFinder().getPath(unit.getLocation(), objective.getLocation());
        if (path == null || path.relink() == -1) {
            LOG.log(Level.SEVERE, "No path found for {0}, or path.relink() failed", unit.toString());
            return tacticalPlan(unit);
        }
        LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unit.toString(), path.toString()});
        MoveAction move = new SurfaceMoveAction(unit, ActionType.TACTICAL_MARCH, path);
        TacticalMission mission = unit.getMission();
        mission.addFirstAction(move);
        if (!move.checkPrecondition()) {
            mission.addFirstAction(new ChangeDeploymentAction(unit, ActionType.ASSEMBLE));
        }
        return true;

    }
}
