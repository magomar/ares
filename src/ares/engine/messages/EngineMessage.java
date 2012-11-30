package ares.engine.messages;

import ares.engine.actors.Actor;
import java.util.Date;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class EngineMessage {

    private final Date time;
    private final EngineMessageType type;
    private final Actor actor;

    public EngineMessage(Date time, EngineMessageType type, Actor actor) {
        this.time = time;
        this.type = type;
        this.actor = actor;
    }

    public Date getTime() {
        return time;
    }

    public EngineMessageType getType() {
        return type;
    }

    public Actor getActor() {
        return actor;
    }
}
