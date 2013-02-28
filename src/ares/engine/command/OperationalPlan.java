package ares.engine.command;

import ares.engine.algorithms.planning.Planner;
import ares.scenario.board.Tile;
import ares.scenario.forces.Formation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class OperationalPlan {

    protected OperationType type;
    protected OperationForm form;
    protected boolean hasPlan;
    protected Formation formation;

    public OperationalPlan(OperationType type, Formation formation) {
        this.type = type;
        hasPlan = false;
        this.formation = formation;

    }

    public abstract void plan(Planner planner);

    public void updateObjectives() {
        for (Objective objective : formation.getObjectives()) {
            if (objective.checkAchieved(formation.getForce())) {
                objective.setAchieved(true);
            }
        }

    }
}