package ares.application.controllers;

import ares.application.views.MessagesHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class MessagesController extends AbstractSecondaryController {

    public MessagesController(WeGoPlayerController wgpc) {
        super(wgpc);
    }

    private class LogCheckBoxListener implements ActionListener {

        public LogCheckBoxListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (e.getSource() instanceof JCheckBox) {
                JCheckBox jcb = (JCheckBox) o;
                if (jcb.isSelected()) {
                    // Show logs with this level
                    ((MessagesHandler) mainController.getMessagesView().getHandler()).enableLogLevel(jcb.getText());
                } else {
                    // hide logs with this level
                    ((MessagesHandler) mainController.getMessagesView().getHandler()).disableLogLevel(jcb.getText());
                }
            }

        }
    }
    
    ActionListener LogCheckBoxListener(){
        return new LogCheckBoxListener();
    }
}
