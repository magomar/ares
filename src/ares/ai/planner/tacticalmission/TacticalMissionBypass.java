package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;
import ares.engine.action.ActionType;
import ares.engine.action.actions.CombatAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.Path;
import ares.engine.algorithms.PathFinder;
import ares.ai.planner.UnitTaskNode;
import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.SurfaceUnit;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class TacticalMissionBypass extends AbstractTacticalMission {

    public TacticalMissionBypass(Scenario scenario) {
        super(scenario);
    }

    @Override
    public int plan(UnitTaskNode taskNode) {

        PathFinder pathFinder = new PathFinder();
        Path approach = pathFinder.findPath(taskNode.getUnit().getLocation(), taskNode.getTask().getGoal(), taskNode.getUnit());
        Tile[] path = new Tile[approach.getTiles().size()];
        approach.getTiles().toArray(path);
        Direction direction = null;

        int i = 1;
        while (Board.getDistanceInTilesBetween(path[i], taskNode.getTask().getGoal()) > 3) {
            for (Direction dir : Direction.values()) {
                if (path[i - 1].getNeighbors().get(dir).equals(path[i])) {
                    direction = dir;
                    break;
                }
            }
            SurfaceMoveAction surfaceMoveAction = new SurfaceMoveAction((SurfaceUnit) taskNode.getUnit(), ActionType.TACTICAL_MARCH, path[i - 1], path[i], direction.getOpposite(), scenario);
            taskNode.addAction(surfaceMoveAction);
            i++;
        }

        LinkedList<Tile> avoiding = new LinkedList<>();
        for (Tile neighbour : taskNode.getTask().getGoal().getNeighbors().values()) {
            avoiding.add(neighbour);
        }

        Tile goal = path[i];
        for (Tile neighbour : taskNode.getTask().getGoal().getNeighbors().values()) {
            for (Tile neighboursNeighbour : neighbour.getNeighbors().values()) {
            }
        }

        Path round = pathFinder.findPath(path[i], taskNode.getTask().getGoal(), taskNode.getUnit(), avoiding);
        path = new Tile[round.getTiles().size()];
        round.getTiles().toArray(path);

        for (i = 1; i < approach.getTiles().size() - 1; i++) {
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
            if (path[approach.getTiles().size() - 2].getNeighbors().get(dir).equals(taskNode.getTask().getGoal())) {
                direction = dir;
                break;
            }
        }
        CombatAction attack = new CombatAction(taskNode.getUnit(), ActionType.ATTACK_BY_FIRE, path[approach.getTiles().size() - 2], taskNode.getTask().getGoal(), direction.getOpposite(), scenario);
        taskNode.addAction(attack);

        return attack.getFinish();
    }
}
