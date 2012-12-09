package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import java.util.*;
import java.util.logging.*;

/**
 * Manages messages to be displayed on the message viewer.
 * 
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesHandler extends Handler {

    private MessagesViewer mv;
    // This turn's records by level
    private LinkedList<LogRecord> turnRecords;
    

    public MessagesHandler(MessagesViewer mv) {
        super();
        this.mv = mv;
        turnRecords = new LinkedList<>();
        setFormatter(new SimpleFormatter());
    }

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record))
            return;
        Level level = record.getLevel();
        
        // Only record our custom levels
        if (level instanceof MessageLevel){
            MessageLevel mlevel = (MessageLevel) level;
            // Add to the record list
            turnRecords.add(record);
            // and if level is enabled make it visible
            if (mlevel.isEnabled())
                mv.append(getFormatter().format(record));
        }

    }

    /**
     * Clears the message viewer text and
     * resets the records list.
     */
    @Override
    public void flush() {
        mv.clear();
        turnRecords = new LinkedList<>();
    }

    @Override
    public void close() throws SecurityException {
    }

    public void enableLogLevel(String levelName) {
        for(Level l : MessageLevel.LEVELS){
            if(l.getName().equals(levelName)){
                ((MessageLevel)l).setEnabled(true);
            }
        }
        mv.clear();
        for(LogRecord record : turnRecords){
            if (record.getLevel() instanceof MessageLevel && ((MessageLevel)record.getLevel()).isEnabled())
                mv.append(getFormatter().format(record));
        }
    }
    
    public void disableLogLevel(String levelName) {
        for(Level l : MessageLevel.LEVELS){
            if(l.getName().equals(levelName)){
                ((MessageLevel)l).setEnabled(false);
            }
        }
        mv.clear();
        for(LogRecord record : turnRecords){
            if (record.getLevel() instanceof MessageLevel && ((MessageLevel)record.getLevel()).isEnabled())
                mv.append(getFormatter().format(record));
        }        
    }
    

    public static class MessageLevel extends Level {

        // Game sys msgs: Opened/loaded/closed scenario...
        public static final Level GAME_SYSTEM = new MessageLevel("Game system", Level.WARNING.intValue() + 1, true);
        // Movement messages: unit went from tile X to tile Y
        public static final Level MOVEMENT = new MessageLevel("Movement", Level.WARNING.intValue() + 2, true);
        // Combat messages: unit X attacked unit Y; X lost 40 health; Y won
        public static final Level COMBAT = new MessageLevel("Combat", Level.WARNING.intValue() + 3, true);
        // Engine messages
        public static final Level ENGINE = new MessageLevel("Engine", Level.WARNING.intValue() + 4, true);
        // All levels
        public static final Level[] LEVELS = {GAME_SYSTEM, MOVEMENT, COMBAT, ENGINE};
        // True if log level is selected
        public boolean enabled;
        
        protected MessageLevel(String name, Integer value, Boolean enabled) {
            super(name, value);
            this.enabled = enabled;
        }
        
        protected boolean isEnabled() {
            return enabled;
        }
        
        protected void setEnabled(Boolean enabled){
            this.enabled = enabled;
        }
    }
}
