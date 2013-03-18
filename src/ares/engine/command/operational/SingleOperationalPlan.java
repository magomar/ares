package ares.engine.command.operational;

import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class SingleOperationalPlan extends OperationalPlan {

    public SingleOperationalPlan(OperationType type, Formation formation, List<Objective> objectives) {
        super(type, formation, objectives);
    }

}
