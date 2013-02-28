package ares.engine.algorithms.planning;

import ares.engine.RealTimeEngine;
import ares.engine.action.Action;
import ares.engine.action.ActionType;
import ares.engine.action.actions.ChangeDeploymentAction;
import ares.engine.action.actions.MoveAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.routing.Path;
import ares.engine.command.Objective;
import ares.engine.command.TacticalMission;
import ares.engine.movement.MovementType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.Queue;
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
    public void plan(Formation formation, Objective objective) {
        for (Unit unit : formation.getActiveUnits()) {
            if (unit.getMovement() != MovementType.AIRCRAFT) {
                Queue<Action> pendingActions = unit.getMission().getPendingActions();
                if (pendingActions.isEmpty()) {
                    singlePlan(unit, objective);
                }
            }
        }
    }

    @Override
    public void singlePlan(Unit unit, Objective objective) {
        Path path = engine.getPathFinder().getPath(unit.getLocation(), objective.getLocation());
        if (path != null && path.relink() != -1) {
            LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unit.toString(), path.toString()});
            MoveAction moveAction = new SurfaceMoveAction(unit, ActionType.TACTICAL_MARCH, path);
            TacticalMission mission = unit.getMission();
            mission.addFirstAction(moveAction);
            if (!moveAction.checkPrecondition()) {
                mission.addFirstAction(new ChangeDeploymentAction(unit, ActionType.ASSEMBLE));
            }
        } else {
            LOG.log(Level.WARNING, "No path found for {0}", unit.toString());
        }
    }
}
