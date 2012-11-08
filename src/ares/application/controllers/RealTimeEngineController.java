package ares.application.controllers;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.player.AresMenus;
import ares.application.views.MenuBarView;
import ares.engine.realtime.RealTimeEngine;
import ares.platform.controller.AbstractController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        menuBarView.addActionListener(EngineCommands.STOP.name(), new StopActionListener());
        menuBarView.addActionListener(FileCommands.OPEN_SCENARIO.name(), new OpenScenarioActionListener());
        menuBarView.addActionListener(FileCommands.CLOSE_SCENARIO.name(), new CloseScenarioActionListener());
    }

    @Override
    protected void registerAllModels() {
        getModel(RealTimeEngine.class).addPropertyChangeListener(this);
    }

    private class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            getModel(RealTimeEngine.class).start();
        }
    }

    private class StopActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
        }
    }

    private class OpenScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            MenuBarView menuBarView = getView(MenuBarView.class);
            menuBarView.getMenuElement(AresMenus.ENGINE_MENU.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.START.getName()).getComponent().setEnabled(true);
            menuBarView.getMenuElement(EngineCommands.STOP.getName()).getComponent().setEnabled(false);
            menuBarView.getMenuElement(EngineCommands.NEXT.getName()).getComponent().setEnabled(false);
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
