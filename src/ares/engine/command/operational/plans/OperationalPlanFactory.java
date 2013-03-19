package ares.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface OperationalPlanFactory {

    OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope);
}
