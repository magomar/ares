package ares.application.views;

import ares.application.boundaries.view.MessagesViewer;
import ares.platform.view.AbstractView;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MessagesView extends AbstractView<JScrollPane> implements MessagesViewer {

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
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(MessagesView.class.getName()).log(Level.INFO, evt.toString());
    }

    @Override
    public void append(String str) {
        textArea.setText(str+newline);
    }

    @Override
    public void clear() {
        textArea.setText("");
    }
}
