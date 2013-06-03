package ares.platform.engine.time;

import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ClockEvent {

    private Set<ClockEventType> eventTypes;

    public ClockEvent(Set<ClockEventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public Set<ClockEventType> getEventTypes() {
        return eventTypes;
    }

    @Override
    public String toString() {
        return "ClockEvent{" + "clock=" + Clock.INSTANCE + ", eventTypes=" + eventTypes + '}';
    }
}
