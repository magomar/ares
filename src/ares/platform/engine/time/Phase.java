package ares.platform.engine.time;

import ares.platform.engine.RealTimeEngine;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Phase {

    PERCEIVE {
        @Override
        public void run(RealTimeEngine engine) {
            engine.perceive();
        }
    },
    SCHEDULE {
        @Override
        public void run(RealTimeEngine engine) {
            engine.schedule();
        }
    },
    ACT
    {
        @Override
        public void run
        (RealTimeEngine engine
        
            ) {
            engine.act();

        }
    };

    private Phase next;

    static {
        PERCEIVE.next = SCHEDULE;
        SCHEDULE.next = ACT;
        ACT.next = PERCEIVE;
    }

    public abstract void run(final RealTimeEngine engine);

    public Phase getNext() {
        return next;
    }
}
