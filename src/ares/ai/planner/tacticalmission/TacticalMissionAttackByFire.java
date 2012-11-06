package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;
import ares.engine.action.ActionType;
import ares.engine.action.actions.CombatAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.Path;
import ares.engine.algorithms.PathFinder;
import ares.ai.planner.UnitTaskNode;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.SurfaceUnit;

/**
 *
 * @author Saúl Esteban
 */
public class TacticalMissionAttackByFire extends AbstractTacticalMission {

    public TacticalMissionAttackByFire(Scenario scenario) {
        super(scenario);
    }

    @Override
    public int plan(UnitTaskNode taskNode) {

        PathFinder pathFinder = new PathFinder();
        Path approach = pathFinder.findPath(taskNode.getUnit().getLocation(), taskNode.getTask().getPosition(), taskNode.getUnit());
        Tile[] path = new Tile[approach.getTiles().size()];
        approach.getTiles().toArray(path);
        Direction direction = null;

        for (int i = 1; i < approach.getTiles().size(); i++) {
            for (Direction dir : Direction.values()) {
                if (path[i - 1].getNeighbors().get(dir).equals(path[i])) {
                    direction = dir;
                    break;
                }
            }
            SurfaceMoveAction surfaceMoveAction = new SurfaceMoveAction((SurfaceUnit) taskNode.getUnit(), ActionType.TACTICAL_MARCH, path[i - 1], path[i], direction.getOpposite(), scenario);
            taskNode.addAction(surfaceMoveAction);
        }

        for (Direction dir : Direction.values()) {
            if (taskNode.getTask().getPosition().getNeighbors().get(dir).equals(taskNode.getTask().getGoal())) {
                direction = dir;
                break;
            }
        }
        CombatAction attack = new CombatAction(taskNode.getUnit(), ActionType.ATTACK_BY_FIRE, taskNode.getTask().getPosition(), taskNode.getTask().getGoal(), direction.getOpposite(), scenario);
        taskNode.addAction(attack);

        return attack.getFinish();
    }
}
