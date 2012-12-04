package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import java.util.logging.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesHandler extends Handler {

    private MessagesViewer mv;
    
    public MessagesHandler(MessagesViewer mv) {
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
