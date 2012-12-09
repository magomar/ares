package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import java.util.logging.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesHandler extends Handler {

    // Game sys msgs: Opened/loaded/closed scenario...
    public static final Level GAME_SYSTEM = new MessageLevel("Game system", Level.SEVERE.intValue() + 1);
    // Movement messages: unit went from tile X to tile Y
    public static final Level MOVEMENT = new MessageLevel("Movement", Level.SEVERE.intValue() + 2);
    // Combat messages: unit X attacked unit Y; X lost 40 health; Y won
    public static final Level COMBAT = new MessageLevel("Combat", Level.SEVERE.intValue() + 3);
    
    // All levels
    public static final Level[] LEVELS = { GAME_SYSTEM, MOVEMENT, COMBAT };
    
    private MessagesViewer mv;

    public MessagesHandler(MessagesViewer mv) {
        super();
        this.mv = mv;
        setFormatter(new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record))
            return;
        mv.append(getFormatter().format(record));
    }

    @Override
    public void flush() {
        mv.clear();
    }

    @Override
    public void close() throws SecurityException {
    }

    public static class MessageLevel extends Level {

        public MessageLevel(String name, Integer value) {
            super(name, value);
        }
    }
}
