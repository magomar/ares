package ares.platform.engine.command.tactical.missions;

import ares.platform.engine.action.ActionType;
import ares.platform.engine.action.actions.ChangeDeploymentAction;
import ares.platform.engine.action.actions.MoveAction;
import ares.platform.engine.action.actions.SurfaceMoveAction;
import ares.platform.engine.action.actions.WaitAction;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class Occupy extends TacticalMission {
//    private static final Logger LOG = Logger.getLogger(Occupy.class.getName());

    public Occupy(TacticalMissionType type, Unit unit, Tile target) {
        super(type, unit, target);
    }

    @Override
    public void plan(Pathfinder pathFinder) {
        currentAction = null;
        pendingActions.clear();
        if (unit.getLocation().equals(targetTile)) {
            addFirstAction(new WaitAction(unit));
            return;
        }
        Path path = pathFinder.getPath(unit.getLocation(), targetTile, unit);
        if (path == null) {
//            LOG.log(Level.WARNING, "No path found for {0}", unit.toString());
            return;
        }
//        LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unit.toString(), path.toString()});
        MoveAction move = new SurfaceMoveAction(unit, ActionType.TACTICAL_MARCH, path);
        addFirstAction(move);
        if (!move.checkPreconditions()) {
            addFirstAction(new ChangeDeploymentAction(unit, ActionType.ASSEMBLE));
        }
    }
    
}
