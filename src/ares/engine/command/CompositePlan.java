package ares.engine.command;

import ares.scenario.forces.Formation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class CompositePlan extends OperationalPlan {

    /**
     * List of operational plans for subordinated formations
     */
    private List<OperationalPlan> plans;

    public CompositePlan(OperationType type, Formation formation, List<Objective> objectives) {
        super(type, formation, objectives);
        plans = new ArrayList<>();
        for (Formation subordinate : formation.getSubordinates()) {
            plans.add(subordinate.getOperationalPlan());
        }
    }

    public List<OperationalPlan> getPlans() {
        return plans;
    }
}
