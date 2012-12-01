package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import ares.engine.messages.EngineMessage;
import ares.engine.messages.EngineMessageLogger;
import ares.platform.view.AbstractView;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MessagesView extends AbstractView<JScrollPane> implements MessagesViewer {

    private EngineMessageLogger msgLogger;
    private JTextArea textArea;
    private final static String newline = "\n";

    @Override
    protected JScrollPane layout() {
        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        p.add(textArea, BorderLayout.CENTER);
        return new JScrollPane(p);
    }

    @Override
    public void addMessage(EngineMessage message) {
        msgLogger.add(message);
    }

    @Override
    public void append(String str) {
        textArea.setText(str + newline);
    }

    @Override
    public void clear() {
        textArea.setText("");
    }
}
