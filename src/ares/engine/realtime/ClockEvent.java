package ares.engine.realtime;

import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ClockEvent {

    private Clock clock;
    private Set<ClockEventType> eventTypes;

    public ClockEvent(Clock clock, Set<ClockEventType> eventTypes) {
        this.clock = clock;
        this.eventTypes = eventTypes;
    }

    public Clock getClock() {
        return clock;
    }

    public Set<ClockEventType> getEventTypes() {
        return eventTypes;
    }

    @Override
    public String toString() {
        return "ClockEvent{" + "clock=" + clock + ", eventTypes=" + eventTypes + '}';
    }
    

}
