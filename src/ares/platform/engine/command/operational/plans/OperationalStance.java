package ares.platform.engine.command.operational.plans;

import ares.data.wrappers.scenario.Emphasis;
import ares.data.wrappers.scenario.SupportScope;
import ares.platform.engine.command.Objective;
import ares.platform.scenario.forces.Formation;

import java.util.List;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum OperationalStance {

    OFFENSIVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new OffensiveOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    DEFENSIVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    RECONNAISSANCE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    SECURITY {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new SecurityOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    GARRISON {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new GarrisonOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    FIXED {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new FixedOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    },
    RESERVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope) {
            return new ReserveOperationalPlan(formation, objectives, emphasis, supportScope);
        }
    };

    public abstract OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope);
}
