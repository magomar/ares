package ares.engine.actors;

import ares.engine.realtime.Clock;
import ares.scenario.forces.Formation;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FormationActor {

    private Formation formation;

    public FormationActor(Formation formation) {
        this.formation = formation;
    }
    
    public void activate() {
        
    }

    public void plan(Clock clock) {
//        if (!hasPlan) {
//            TaskPlanner planner = new TaskPlanner(this, clock);
//            planner.obtainPlan();
//            hasPlan = true;
//        }
        
//        for (Unit unit : formation.getLineUnits()) {
//            if (unit.getMovement() != MovementType.AIRCRAFT) {
//                Tile location = unit.getLocation();
//                Queue<Action> pendingActions = unit.getPendingActions();
//                if (unit.getSpeed() > 0 && unit.getPendingActions().isEmpty() && unit.getEndurance() >= ActionType.APPROACH_MARCH.getWearRate() * clock.MINUTES_PER_TICK) {
//                    Action anAction;
//                    if (unit.getAction() != null && unit.getAction().getState() == ActionState.DELAYED) {
//                        anAction = new ChangeDeploymentAction(unit, ActionType.ASSEMBLE, location, scenario);
//                    } else {
//                        Direction[] directions = unit.getLocation().getNeighbors().keySet().toArray(new Direction[0]);
//                        int randomDirIndex = RandomGenerator.getInstance().nextInt(directions.length);
//                        Direction fromDir = directions[randomDirIndex];
//                        anAction = new SurfaceMoveAction((SurfaceUnit) unit, ActionType.TACTICAL_MARCH, location, location.getNeighbors().get(fromDir), clock.getCurrentTime() + 10, fromDir, scenario);
//
//                    }
//                    pendingActions.add(anAction);
////                    System.out.println("New Action: " + anAction.toString());
//                }
//            }
//        }
    }
}
