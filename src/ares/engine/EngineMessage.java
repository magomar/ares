package ares.engine;

import ares.engine.actors.Actor;
import java.util.Date;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class EngineMessage {
    Date time;
    EngineMessageType type;
    Actor actor;
}
