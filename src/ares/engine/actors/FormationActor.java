package ares.engine.actors;

import ares.engine.action.Action;
import ares.engine.action.ActionState;
import ares.engine.action.ActionType;
import ares.engine.action.actions.ChangeDeploymentAction;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.routing.Path;
import ares.engine.algorithms.PathFinderAstar;
import ares.engine.movement.MovementType;
import ares.engine.realtime.Clock;
import ares.engine.realtime.RealTimeEngine;
import ares.platform.util.RandomGenerator;
import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FormationActor {

    private Formation formation;
    private List<UnitActor> unitActors;
    private RealTimeEngine engine;
    private boolean hasPlan;
    private static final Logger LOG = Logger.getLogger(FormationActor.class.getName());

    public FormationActor(Formation formation, RealTimeEngine engine) {
        this.formation = formation;
        this.unitActors = new ArrayList<>();
        for (Unit unit : formation.getActiveUnits()) {
            unit.activate();
            unitActors.add(new UnitActor(unit));
        }
        this.engine = engine;
        hasPlan = false;
    }

    public void plan(Clock clock) {
        if (!hasPlan) {
            for (UnitActor unitActor : unitActors) {
                Unit unit = unitActor.getUnit();
                if (unit.getMovement() != MovementType.AIRCRAFT) {
                    Queue<Action> pendingActions = unitActor.getPendingActions();
                    if (pendingActions.isEmpty()) {
                        singlePlan(unitActor);
                    }
                }
            }
//            TaskPlanner planner = new TaskPlanner(this, clock);
//            planner.obtainPlan();
//            hasPlan = true;
        }
        //                Tile location = unit.getLocation();
//                if (unit.getSpeed() > 0 && unitActor.getPendingActions().isEmpty() && unit.getEndurance() >= ActionType.APPROACH_MARCH.getWearRate() * clock.MINUTES_PER_TICK) {
//                    Action anAction;
//                    if (unitActor.getCurrentAction() != null && unitActor.getCurrentAction().getState() == ActionState.DELAYED) {
//                        anAction = new ChangeDeploymentAction(unitActor, ActionType.ASSEMBLE, location, clock.getCurrentTime() + clock.MINUTES_PER_TICK);
//                    } else {
//                        Direction[] directions = unit.getLocation().getNeighbors().keySet().toArray(new Direction[0]);
//                        int randomDirIndex = RandomGenerator.getInstance().nextInt(directions.length);
//                        Direction fromDir = directions[randomDirIndex];
//                        anAction = new SurfaceMoveAction(unitActor, ActionType.TACTICAL_MARCH, location, location.getNeighbors().get(fromDir), clock.getCurrentTime() + clock.MINUTES_PER_TICK, fromDir, engine.getScenario().getScale().getDistance());
//
//                    }
//                    pendingActions.add(anAction);
//                    System.out.println("New Action: " + anAction.toString());
    }

    private void singlePlan(UnitActor unitActor) {
        Tile objective = formation.getObjectives().get(0);
        Path path = null;//pathFinder.findPath(unitActor.getUnit().getLocation(), objective, unitActor.getUnit());
        if (path != null) {
            Tile from = unitActor.getUnit().getLocation();
            for (Tile to : path.getTiles().subList(1, path.getTiles().size())) {
                Action moveAction = new SurfaceMoveAction(unitActor, ActionType.TACTICAL_MARCH, from, to, engine.getClock(), engine.getScenario().getScale().getDistance());
                unitActor.getPendingActions().add(moveAction);
                from = to;
            }
            LOG.log(Level.INFO, "New plan for {0}: {1}", new Object[]{unitActor.toString(), path.toString()});
        }
    }

    public List<UnitActor> getUnitActors() {
        return unitActors;
    }

    @Override
    public String toString() {
        return formation + ", unitActors=" + unitActors + '}';
    }
}
