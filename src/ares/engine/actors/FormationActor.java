package ares.engine.actors;

import ares.engine.action.Action;
import ares.engine.action.ActionType;
import ares.engine.action.actions.SurfaceMoveAction;
import ares.engine.algorithms.routing.Path;
import ares.engine.movement.MovementType;
import ares.engine.realtime.RealTimeEngine;
import ares.scenario.Scale;
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

    public void plan() {
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

    }

    private void singlePlan(UnitActor unitActor) {
        Tile objective = formation.getObjectives().get(0);
        Path path = engine.getPathFinder().getPath(unitActor.getUnit().getLocation(), objective);
        if (path != null && path.relink() != -1) {
            LOG.log(Level.INFO, "New path for {0}: {1}", new Object[]{unitActor.toString(), path.toString()});
            Action moveAction = new SurfaceMoveAction(unitActor, ActionType.TACTICAL_MARCH, path, Scale.INSTANCE.getTileSize());
            unitActor.getPendingActions().add(moveAction);
        } else {
            LOG.log(Level.WARNING, "No path found for {0}", unitActor.toString());
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
