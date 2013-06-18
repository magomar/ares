package ares.platform.engine.command.operational.plans;

import ares.data.jaxb.Emphasis;
import ares.data.jaxb.SupportScope;
import ares.platform.engine.command.Objective;
import ares.platform.scenario.forces.Formation;

import java.util.List;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum OperationalStance implements OperationalPlanFactory {

    OFFENSIVE {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new OffensiveOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    DEFENSIVE {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    RECONNAISSANCE {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    SECURITY {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new SecurityOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    GARRISON {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new GarrisonOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    FIXED {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new FixedOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    },
    RESERVE {
        @Override
        public OperationalPlan getNewOperationalPlan(Formation formation, List<Objective> objectives,Emphasis emphasis, SupportScope supportScope) {
            OperationalPlan opPlan = new ReserveOperationalPlan(formation, objectives, emphasis, supportScope);
            return opPlan;
        }
    };
}
