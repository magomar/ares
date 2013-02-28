package ares.engine.algorithms.planning;

import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface Planner {
    void plan(Formation formation, Objective objective);
    void singlePlan(Unit unit, Objective objective);
}
