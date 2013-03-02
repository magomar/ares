package ares.engine.command;

import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class SinglePlan extends OperationalPlan {

    public SinglePlan(OperationType type, Formation formation, List<Objective> objectives) {
        super(type, formation, objectives);
    }

}
