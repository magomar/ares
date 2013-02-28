package ares.engine.command;

import ares.scenario.forces.Formation;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class SinglePlan extends OperationalPlan {

    private Objective objective;

    public SinglePlan(OperationType type, Formation formation) {
        super(type, formation);
    }
}
