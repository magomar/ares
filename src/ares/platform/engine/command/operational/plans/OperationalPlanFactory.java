package ares.platform.engine.command.operational.plans;

import ares.data.wrappers.scenario.Emphasis;
import ares.data.wrappers.scenario.SupportScope;
import ares.platform.engine.command.Objective;
import ares.platform.scenario.forces.Formation;

import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OperationalPlanFactory {

    OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope);
}
