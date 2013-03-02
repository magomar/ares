package ares.engine.algorithms.planning;

import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface Planner {
    boolean plan(Formation formation);
    boolean tacticalPlan(Unit unit, Objective objective);
}
