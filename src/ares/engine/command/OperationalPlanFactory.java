package ares.engine.command;

import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class OperationalPlanFactory {
        public static OperationalPlan getOperationalPlan(OperationType operationType, Formation formation, List<Objective> objectives) {
     
        if (formation.getSubordinates().isEmpty()) {
            return new SinglePlan(operationType, formation, objectives);
        } else {
            return new CompositePlan(operationType, formation, objectives);
        }
    }
}
