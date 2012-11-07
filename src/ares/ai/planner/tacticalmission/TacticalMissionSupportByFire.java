package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;
import ares.engine.action.Action;
import ares.engine.action.ActionType;
import ares.engine.action.actions.CombatAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.Path;
import ares.engine.algorithms.PathFinder;
import ares.ai.planner.UnitTaskNode;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.SurfaceUnit;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class TacticalMissionSupportByFire extends AbstractTacticalMission {

    public TacticalMissionSupportByFire(Scenario scenario) {
        super(scenario);
    }

    @Override
    public int plan(UnitTaskNode taskNode) {
        //TODO Revise all
        Tile position, allyPosition = null;
        LinkedList<Tile> possiblePositions = new LinkedList<>();
        //Action[] mainMission = new Action[taskNode.getUnitForSupport().getPendingActions().size()];
        //taskNode.getUnitForSupport().getPendingActions().toArray(mainMission);

//        for (int i = 0; i < mainMission.length; i++) {
//            if (mainMission[i].getType() == ActionType.ASSAULT || mainMission[i].getType() == ActionType.ATTACK_BY_FIRE) {
//                allyPosition = mainMission[i].getOrigin();
//                taskNode.getTask().setGoal(mainMission[i].getDestination());
//            }
//        }

        for (Tile tile : taskNode.getTask().getGoal().getNeighbors().values()) {
            if (tile.getNeighbors().containsValue(allyPosition) && tile.getNeighbors().containsValue(taskNode.getTask().getGoal())) {
                possiblePositions.add(tile);
            }
        }
        if (Math.sqrt(Math.pow(possiblePositions.getFirst().getX() - taskNode.getUnit().getLocation().getX(), 2) + Math.pow(possiblePositions.getFirst().getY() - taskNode.getUnit().getLocation().getY(), 2))
                < Math.sqrt(Math.pow(possiblePositions.getLast().getX() - taskNode.getUnit().getLocation().getX(), 2) + Math.pow(possiblePositions.getLast().getY() - taskNode.getUnit().getLocation().getY(), 2))) {
            position = possiblePositions.getFirst();
        } else {
            position = possiblePositions.getLast();
        }

        PathFinder pathFinder = new PathFinder();
        Path approach = pathFinder.findPath(taskNode.getUnit().getLocation(), position, taskNode.getUnit());
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
 //           SurfaceMoveAction surfaceMoveAction = new SurfaceMoveAction((SurfaceUnit) taskNode.getUnit(), ActionType.TACTICAL_MARCH, path[i - 1], path[i], direction.getOpposite(), scenario);
//            taskNode.addAction(surfaceMoveAction);
        }

        for (Direction dir : Direction.values()) {
            if (position.getNeighbors().get(dir).equals(taskNode.getTask().getGoal())) {
                direction = dir;
                break;
            }
        }
//        CombatAction support = new CombatAction(taskNode.getUnit(), ActionType.SUPPORT_BY_FIRE, position, taskNode.getTask().getGoal(), direction.getOpposite(), scenario);
//        taskNode.addAction(support);

        return 0;//support.getFinish();
    }
}
