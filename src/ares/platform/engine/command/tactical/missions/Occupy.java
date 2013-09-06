package ares.platform.engine.command.tactical.missions;

import ares.platform.engine.action.Action;
import ares.platform.engine.action.ActionType;
import ares.platform.engine.action.actions.ChangeDeploymentAction;
import ares.platform.engine.action.actions.CombatAction;
import ares.platform.engine.action.actions.SurfaceMoveAction;
import ares.platform.engine.action.actions.WaitAction;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
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
        Path path = pathFinder.findPath(unit.getLocation(), targetTile, unit);
        if (path == null) {
//            LOG.log(Level.WARNING, "No path found for {0}", unit.toString());
            return;
        }
//        LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unit.toString(), path.toString()});
        Action move = null;
        if (targetTile.hasEnemies(unit.getForce())) {
            Action attack = new CombatAction(ActionType.ASSAULT, unit, path.subPathFrom(path.getLast().getPrev()));
            addFirstAction(attack);
            if (path.size() > 2) {
                move = new SurfaceMoveAction(ActionType.TACTICAL_MARCH, unit, path.subPathTo(path.getLast().getPrev()));
                addFirstAction(move);
                if (!move.checkPreconditions()) {
                    addFirstAction(new ChangeDeploymentAction(ActionType.ASSEMBLE, unit));
                }
            }
        } else {
            move = new SurfaceMoveAction(ActionType.TACTICAL_MARCH, unit, path);
            addFirstAction(move);
            if (!move.checkPreconditions()) {
                addFirstAction(new ChangeDeploymentAction(ActionType.ASSEMBLE, unit));
            }
        }

    }

}
