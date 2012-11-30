package ares.application.views;

import java.util.logging.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesHandler extends Handler {

    MessagesView mv;
    
    public MessagesHandler(MessagesView mv) {
        super();
        this.mv = mv;
        setFormatter(new SimpleFormatter());
    }


    @Override
    public void publish(LogRecord record) {
            if(!isLoggable(record)) {
            return;
        }
            mv.append(getFormatter().format(record));
    }

    @Override
    public void flush() {
        mv.clear();
    }

    @Override
    public void close() throws SecurityException {
        
    }
}
