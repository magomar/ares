package ares.ai;

import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class FormationAI {

    /**
     * List of available (on-board) line units. This collection excludes reinforcements, destroyed/withdrawed units and
     * divided units. Line units are able to perform assaults by themselves
     */
    private List<Unit> lineUnits;
    /**
     * List of active line-support and support units. This units are not able to perform assaults
     */
    private List<Unit> supportUnits;
    private Unit headquarters;
    /**
     * List of active service-support units
     */
    private List<Unit> serviceUnits;

    public FormationAI() {
        lineUnits = new ArrayList<>();
        supportUnits = new ArrayList<>();
        serviceUnits = new ArrayList<>();
    }

    public void plan() {
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
