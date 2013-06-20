package ares.platform.engine.messages;

import java.util.Date;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class EngineMessage {

    private final Date time;
    private final EngineMessageType type;

    public EngineMessage(Date time, EngineMessageType type) {
        this.time = time;
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public EngineMessageType getType() {
        return type;
    }
}
