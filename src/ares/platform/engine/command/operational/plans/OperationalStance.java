package ares.platform.engine.command.operational.plans;

import ares.data.wrappers.scenario.Emphasis;
import ares.data.wrappers.scenario.SupportScope;
import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.command.Objective;
import ares.platform.scenario.forces.Formation;

import java.util.List;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum OperationalStance {

    OFFENSIVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new OffensiveOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    DEFENSIVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    RECONNAISSANCE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new DefensiveOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    SECURITY {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new SecurityOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    GARRISON {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new GarrisonOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    FIXED {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new FixedOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    },
    RESERVE {
        @Override
        public OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace) {
            OperationalPlan opPlan = new ReserveOperationalPlan(formation, objectives, emphasis, supportScope, actionSpace);
            return opPlan;
        }
    };

    public abstract OperationalPlan buildOperationalPlan(Formation formation, List<Objective> objectives, Emphasis emphasis, SupportScope supportScope, ActionSpace actionSpace);
}
