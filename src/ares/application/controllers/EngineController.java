package ares.application.controllers;

import ares.application.boundaries.view.CommandBarViewer;
import ares.application.boundaries.view.MessagesViewer;
import ares.application.commands.EngineCommands;
import ares.application.views.MessagesHandler;
import ares.platform.controllers.AbstractSecondaryController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class EngineController extends AbstractSecondaryController {
    private static final Logger LOG = Logger.getLogger(EngineController.class.getName());
    private final CommandBarViewer menuView;
    private final MessagesViewer messagesView;

    public EngineController(CommandBarViewer menuView, MessagesViewer messagesView, WeGoPlayerController mainController) {
        super(mainController);
        this.menuView = menuView;
        this.messagesView = messagesView;
    }
  
    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            mainController.getEngine().start();
            menuView.setCommandEnabled(EngineCommands.START.getName(), false);
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            mainController.getEngine().stop();
            menuView.setCommandEnabled(EngineCommands.START.getName(), true);
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            messagesView.clear();
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), true);
            menuView.setCommandEnabled(EngineCommands.NEXT.getName(), false);
            mainController.getEngine().start();
        }
    }

    ActionListener StartActionListener() {
        return new StartActionListener();
    }

    ActionListener PauseActionListener() {
        return new PauseActionListener();
    }

    ActionListener NextActionListener() {
        return new NextActionListener();
    }
}
