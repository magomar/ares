package ares.engine.command;

import ares.engine.algorithms.planning.Planner;
import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class CompositePlan extends OperationalPlan {

    private List<OperationalPlan> plans;
    /**
     * List of objectives (used by the programmed opponent to generate plans)
     */


    public CompositePlan(OperationType type, Formation formation, List<Objective> objectives) {
        super(type, formation, objectives);
    }


    public List<OperationalPlan> getPlans() {
        return plans;
    }

//    public void setPlans(List<OperationalPlan> plans) {
//        this.plans = plans;
//    }

}
