package ares.application.shared.gui.views;

import ares.platform.engine.messages.EngineMessageLogger;
import ares.platform.engine.messages.EngineMessage;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.logging.Level;
import javax.swing.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesView extends AbstractView<JPanel> implements MessagesViewer {

    private EngineMessageLogger msgLogger;
    private ArrayList<JCheckBox> cbLevels;
    private JTextArea textArea;
    private final static String newline = "\n";
    private MessagesHandler handler;

    @Override
    protected JPanel layout() {
        JPanel messagesContainer = new JPanel(new BorderLayout());
        handler = new MessagesHandler(this);
        // Message levels
        messagesContainer.add(setMessageLevelCheckBoxes(), BorderLayout.WEST);
        // Messages area
        JPanel messagesPanel = new JPanel(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        messagesPanel.add(textArea, BorderLayout.CENTER);
        messagesContainer.add(new JScrollPane(messagesPanel), BorderLayout.CENTER);

        return messagesContainer;
    }

    public JComponent setMessageLevelCheckBoxes() {
        cbLevels = new ArrayList<>();
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));

        for (Level level : MessagesHandler.MessageLevel.LEVELS) {
            JCheckBox jcb = new JCheckBox(level.getName());
            jcb.setSelected(true);
            buttonPane.add(jcb);
            cbLevels.add(jcb);
        }

        return buttonPane;
    }

    @Override
    public void addMessage(EngineMessage message) {
        msgLogger.add(message);
    }

    @Override
    public void append(String str) {
        textArea.append(str + newline);
    }

    @Override
    public void clear() {
        textArea.setText("");
    }

    @Override
    public void setLogCheckBoxes(ActionListener logCheckBoxListener) {
        for (JCheckBox jcb : cbLevels) {
            jcb.addActionListener(logCheckBoxListener);
        }
    }

    @Override
    public MessagesHandler getHandler() {
        return handler;
    }
}
