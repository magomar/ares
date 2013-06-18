package ares.platform.engine.messages;

import ares.platform.model.AbstractBean;

import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class EngineMessageLogger extends AbstractBean {

    public static final String MESSAGES_PROPERTY = "Messages";
    private final Map<EngineMessageType, List<EngineMessage>> messages;

    public EngineMessageLogger() {
        messages = new EnumMap<>(EngineMessageType.class);
    }

    public void add(EngineMessage msg) {
        List<EngineMessage> oldValue = messages.get(msg.getType());
        List<EngineMessage> msgList = new LinkedList<>(oldValue);
        msgList.add(msg);
        firePropertyChange(MESSAGES_PROPERTY, oldValue, msgList);
    }
}
