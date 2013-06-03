package ares.application.controllers;

import ares.application.boundaries.interactor.MessagesInteractor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class MessagesController {

    private final MessagesInteractor coordinator;

    public MessagesController(MessagesInteractor coordinator) {
        this.coordinator = coordinator;

        // Add listeners to MessagesView check boxes
        coordinator.getMessagesView().setLogCheckBoxes(new LogCheckBoxListener());
    }

    private class LogCheckBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox jcb = (JCheckBox) o;
                if (jcb.isSelected()) {
                    // Show logs with this level
                    coordinator.getMessagesView().getHandler().enableLogLevel(jcb.getText());
                } else {
                    // hide logs with this level
                    coordinator.getMessagesView().getHandler().disableLogLevel(jcb.getText());
                }
            }

        }
    }
}
