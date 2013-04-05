package ares.engine.command.operational.operations;

import ares.engine.algorithms.pathfinding.PathFinder;
import ares.engine.command.Objective;
import ares.engine.command.operational.Operation;
import ares.scenario.forces.Formation;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OperationTypeFactory {
    Operation getNewOperation(OperationType opType, Formation formation, Objective goal, PathFinder pathFinder);
}
