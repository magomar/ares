package ares.engine.command.operational;

import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class OperationalPlanFactory {

    public static OperationalPlan getOperationalPlan(OperationType operationType, Formation formation, List<Objective> objectives) {
        if (formation.getSubordinates().isEmpty()) {
            return new SingleOperationalPlan(operationType, formation, objectives);
        } else {
            return new CompositeOperationalPlan(operationType, formation, objectives);
        }
    }
}
