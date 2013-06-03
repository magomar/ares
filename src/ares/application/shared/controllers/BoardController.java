package ares.application.shared.controllers;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.platform.action.ActionGroup;
import ares.platform.action.CommandAction;
import ares.platform.action.CommandGroup;
import ares.platform.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.Action;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class BoardController implements ActionController {

    private static final Logger LOG = Logger.getLogger(BoardController.class.getName());
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final BoardInteractor interactor;
    private Scenario scenario;

    public BoardController(BoardInteractor interactor) {
        this.interactor = interactor;
        interactor.registerLogger(LOG);
        // create action groups
        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        interactor.getBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        ScenarioModel scenarioModel = scenario.getModel();
        interactor.getBoardView().loadScenario(scenarioModel);
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            interactor.getBoardView().setProfile(GraphicsModel.INSTANCE.nextActiveProfile());
            interactor.getBoardView().loadScenario(scenario.getModel());
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            interactor.getBoardView().setProfile(GraphicsModel.INSTANCE.previousActiveProfile());
            interactor.getBoardView().loadScenario(scenario.getModel());
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            interactor.getBoardView().switchLayerVisible(BoardViewer.GRID);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            interactor.getBoardView().switchLayerVisible(BoardViewer.UNITS);
        }
    }
}
