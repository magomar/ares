package ares.engine.command.tactical.missions;

import ares.engine.action.ActionType;
import ares.engine.action.actions.ChangeDeploymentAction;
import ares.engine.action.actions.MoveAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.PathFinder;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class Occupy extends TacticalMission {
    private static final Logger LOG = Logger.getLogger(Occupy.class.getName());

    public Occupy(TacticalMissionType type, Unit unit, Tile target) {
        super(type, unit, target);
    }

    @Override
    public void plan(PathFinder pathFinder) {
        currentAction = null;
        pendingActions.clear();
        if (unit.getLocation().equals(targetTile)) {
            pushAction(new WaitAction(unit));
            return;
        }
        Path path = pathFinder.getPath(unit.getLocation(), targetTile, unit);
        if (path == null || path.relink() == -1) {
            LOG.log(Level.WARNING, "No path found for {0}, or path.relink() failed", unit.toString());
            return;
        }
        LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unit.toString(), path.toString()});
        MoveAction move = new SurfaceMoveAction(unit, ActionType.TACTICAL_MARCH, path);
        pushAction(move);
        if (!move.checkPreconditions()) {
            pushAction(new ChangeDeploymentAction(unit, ActionType.ASSEMBLE));
        }
    }
    
}
