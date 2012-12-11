package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.views.MessagesHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class EngineController extends AbstractSecondaryController {

    public EngineController(WeGoPlayerController wgpc) {
        super(wgpc);
    }

    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainController.getLog().log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            mainController.getEngine().start();
            mainController.getMenuView().setCommandEnabled(EngineCommands.START.getName(), false);
            mainController.getMenuView().setCommandEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainController.getLog().log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            mainController.getEngine().stop();
            mainController.getMenuView().setCommandEnabled(EngineCommands.START.getName(), true);
            mainController.getMenuView().setCommandEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainController.getMessagesView().clear();
            mainController.getLog().log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            mainController.getMenuView().setCommandEnabled(EngineCommands.PAUSE.getName(), true);
            mainController.getMenuView().setCommandEnabled(EngineCommands.NEXT.getName(), false);
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
