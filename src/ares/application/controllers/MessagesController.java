package ares.application.controllers;

import ares.application.boundaries.view.MessagesViewer;
import ares.platform.controllers.AbstractSecondaryController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class MessagesController extends AbstractSecondaryController {

    private final MessagesViewer messagesView;

    public MessagesController(WeGoPlayerController mainController) {
        super(mainController);
        this.messagesView = mainController.getMessagesView();

        // Add listeners to MessagesView check boxes
        messagesView.setLogCheckBoxes(new LogCheckBoxListener());
    }

    private class LogCheckBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox jcb = (JCheckBox) o;
                if (jcb.isSelected()) {
                    // Show logs with this level
                    messagesView.getHandler().enableLogLevel(jcb.getText());
                } else {
                    // hide logs with this level
                    messagesView.getHandler().disableLogLevel(jcb.getText());
                }
            }

        }
    }
}
