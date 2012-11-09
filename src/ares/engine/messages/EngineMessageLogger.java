package ares.engine.messages;

import ares.platform.model.AbstractModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class EngineMessageLogger extends AbstractModel {

    public static final String MESSAGES_PROPERTY = "Messages";
    public List<EngineMessage> messages = new ArrayList<>();

    public void add(EngineMessage msg) {
        EngineMessage oldValue = messages.get(messages.size() - 1);
        messages.add(msg);
        firePropertyChange(MESSAGES_PROPERTY, oldValue, msg);
    }
}
