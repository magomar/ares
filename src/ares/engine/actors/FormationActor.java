package ares.engine.actors;

import ares.engine.Engine;
import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.ChangeDeploymentAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.movement.MovementType;
import ares.engine.realtime.Clock;
import ares.platform.util.RandomGenerator;
import ares.model.board.Direction;
import ares.model.board.Tile;
import ares.model.forces.Formation;
import ares.model.forces.Unit;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FormationActor {

    private Formation formation;
    private List<UnitActor> unitActors;
    private Engine engine;

    public FormationActor(Formation formation, List<UnitActor> unitActors, Engine engine) {
        this.formation = formation;
        this.unitActors= unitActors;
        this.engine = engine;
    }

    public void plan(Clock clock) {
//        if (!hasPlan) {
//            TaskPlanner planner = new TaskPlanner(this, clock);
//            planner.obtainPlan();
//            hasPlan = true;
//        }

        for (UnitActor unitActor : unitActors) {
            Unit unit = unitActor.getUnit();
            if (unitActor.getUnit().getMovement() != MovementType.AIRCRAFT) {
                Tile location = unit.getLocation();
                Queue<Action> pendingActions = unitActor.getPendingActions();
                if (unit.getSpeed() > 0 && unitActor.getPendingActions().isEmpty() && unit.getEndurance() >= ActionType.APPROACH_MARCH.getWearRate() * clock.MINUTES_PER_TICK) {
                    Action anAction;
                    if (unitActor.getCurrentAction() != null && unitActor.getCurrentAction().getState() == ActionState.DELAYED) {
                        anAction = new ChangeDeploymentAction(unitActor, ActionType.ASSEMBLE, location, clock.getCurrentTime()+clock.MINUTES_PER_TICK);
                    } else {
                        Direction[] directions = unit.getLocation().getNeighbors().keySet().toArray(new Direction[0]);
                        int randomDirIndex = RandomGenerator.getInstance().nextInt(directions.length);
                        Direction fromDir = directions[randomDirIndex];
                        anAction = new SurfaceMoveAction(unitActor, ActionType.TACTICAL_MARCH, location, location.getNeighbors().get(fromDir), clock.getCurrentTime()+clock.MINUTES_PER_TICK, fromDir, engine.getScenario().getScale().getDistance());

                    }
                    pendingActions.add(anAction);
//                    System.out.println("New Action: " + anAction.toString());
                }
            }
        }
    }
}
