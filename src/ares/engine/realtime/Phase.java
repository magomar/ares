package ares.engine.realtime;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Phase {

    ACTIVATE {
        @Override
        public void run(RealTimeEngine engine) {
            engine.activate();
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
        ACT.next = SCHEDULE;
        SCHEDULE.next = ACT;
    }

    abstract void run(final RealTimeEngine engine);

    public Phase getNext() {
        return next;
    }
}
