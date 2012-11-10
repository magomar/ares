package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.player.AresMenus;
import ares.application.views.BoardView;
import ares.application.views.MenuBarView;
import ares.engine.realtime.Clock;
import ares.engine.realtime.ClockEvent;
import ares.engine.realtime.ClockEventType;
import ares.engine.realtime.RealTimeEngine;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controller.AbstractController;
import ares.platform.view.InternalFrameView;
import ares.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngineController extends AbstractController {

    private static final Logger LOG = Logger.getLogger(RealTimeEngineController.class.getName());

    @Override
    protected void registerAllActionListeners() {
        MenuBarView menuBarView = getView(MenuBarView.class);
        menuBarView.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuBarView.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuBarView.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());

        menuBarView.addActionListener(FileCommands.OPEN_SCENARIO.name(), new OpenScenarioActionListener());
        menuBarView.addActionListener(FileCommands.CLOSE_SCENARIO.name(), new CloseScenarioActionListener());

    }

    @Override
    protected void registerAllModels() {
        getModel(RealTimeEngine.class).addPropertyChangeListener(this);
    }
    // I have to overwrite this, but I do not like doing this
    // it should be done by the view, but being inside an Internal Frame it cannot change the title of the frame

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        super.propertyChange(evt);
        switch (evt.getPropertyName()) {
            case RealTimeEngine.CLOCK_EVENT_PROPERTY:
                ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
                Clock clock = clockEvent.getClock();
                InternalFrameView<BoardView> boardFrame = getInternalFrameView(BoardView.class);
                boardFrame.setTitle("Turn: " + clock.getTurn() + "   Time: " + clock.toString());
                if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                    MenuBarView menuBarView = getView(MenuBarView.class);
//                    menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(false);
                    menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(false);
                    menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(true);
                }
                break;
        }
    }

    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            getModel(RealTimeEngine.class).start();
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(false);
            menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(true);
//            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            getModel(RealTimeEngine.class).stop();
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(false);
//            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
        }
    }

    private class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            MenuBarView menuBarView = getView(MenuBarView.class);
//            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
            getModel(RealTimeEngine.class).start();
        }
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.getMenuElement(AresMenus.ENGINE_MENU.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.PAUSE.getName()).getComponent().setEnabled(false);
            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
//            menuBarView.getMenuElement(AresMenus.VIEW_MENU.getName()).getComponent().setEnabled(true);
        }
    }

    private class CloseScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.getMenuElement(AresMenus.ENGINE_MENU.getName()).getComponent().setEnabled(false);
        }
    }
}
