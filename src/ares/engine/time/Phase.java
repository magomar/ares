package ares.engine.time;

import ares.engine.RealTimeEngine;

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
    ACT {
        @Override
        public void run(RealTimeEngine engine) {
            engine.act();

        }
    },
    SCHEDULE {
        @Override
        public void run(RealTimeEngine engine) {
            engine.schedule();
        }
    };
    private Phase next;

    static {
        PERCEIVE.next = ACT;
        ACT.next = SCHEDULE;
        SCHEDULE.next = PERCEIVE;
    }

    public abstract void run(final RealTimeEngine engine);

    public Phase getNext() {
        return next;
    }
}
