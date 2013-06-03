package ares.platform.engine.command.operational.operations;

import ares.platform.engine.algorithms.pathfinding.PathFinder;
import ares.platform.engine.command.Objective;
import ares.platform.engine.command.operational.Operation;
import ares.platform.scenario.forces.Formation;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OperationTypeFactory {
    Operation getNewOperation(OperationType opType, Formation formation, Objective goal, PathFinder pathFinder);
}
