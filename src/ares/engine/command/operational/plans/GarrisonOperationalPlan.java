package ares.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.engine.algorithms.routing.PathFinder;
import ares.engine.command.Objective;
import ares.scenario.forces.Formation;
import java.util.List;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
class GarrisonOperationalPlan extends OperationalPlan {

    public GarrisonOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
        super(OperationalStance.GARRISON, formation, objectives, emphasis, supportScope);
    }

    @Override
    public void plan(PathFinder pathFinder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}