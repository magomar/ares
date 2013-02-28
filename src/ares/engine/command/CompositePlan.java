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

    public CompositePlan(OperationType type, Formation formation) {
        super(type, formation);
    }

    public List<OperationalPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<OperationalPlan> plans) {
        this.plans = plans;
    }
    
    public void plan(Planner planner) {
        if (!hasPlan) {
            SinglePlan
            planner.plan(formation, newTask);
            hasPlan = true;
        }
    }    
}
