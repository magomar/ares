package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import ares.engine.messages.*;
import ares.platform.view.AbstractView;
import java.awt.*;
import java.util.logging.Level;
import javax.swing.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class MessagesView extends AbstractView<JPanel> implements MessagesViewer {

    private EngineMessageLogger msgLogger;
    private JTextArea textArea;
    private final static String newline = "\n";

    @Override
    protected JPanel layout() {
        JPanel messagesContainer = new JPanel(new BorderLayout());
        
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
    
    public JComponent setMessageLevelCheckBoxes(){
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.Y_AXIS));
        
        for(Level level : MessagesHandler.LEVELS){
            JCheckBox jcb = new JCheckBox(level.getName());
            jcb.setSelected(true);
            buttonPane.add(jcb);
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
}
