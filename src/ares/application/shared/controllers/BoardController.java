package ares.application.shared.controllers;

import ares.application.shared.action.ActionGroup;
import ares.application.shared.action.CommandAction;
import ares.application.shared.action.CommandGroup;
import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class BoardController implements ActionController {

    protected final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    protected final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    protected final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    protected final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    protected final ActionGroup actions;
    protected final BoardViewer boardView;
    protected final TerrainLayerViewer terrainLayerView;
    protected final UnitsLayerViewer unitsLayerView;
    protected final GridLayerViewer gridLayerView;
    protected final BoardInteractor interactorView;
    protected Scenario scenario;
    protected UserRole userRole;

    public BoardController(BoardInteractor interactor) {
        this.boardView = interactor.getBoardView();
        this.interactorView = interactor;
        terrainLayerView = (TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME);
        unitsLayerView = (UnitsLayerViewer) boardView.getLayerView(UnitsLayerViewer.NAME);
        gridLayerView = (GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME);
        gridLayerView.setVisible(false);
        // create action groups
        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);

    }

    public void setScenario(Scenario scenario, UserRole userRole, int profile) {
        this.scenario = scenario;
        this.userRole = userRole;
        boardView.setProfile(profile);
        // Render board: paint terrain and units
        ScenarioModel scenarioModel = scenario.getModel(userRole);
        terrainLayerView.updateScenario(scenarioModel);
        unitsLayerView.updateScenario(scenarioModel);
        gridLayerView.updateScenario(scenarioModel);
    }

    public void updateScenario() {
        // Render board: paint terrain and units
        ScenarioModel scenarioModel = scenario.getModel(userRole);
        unitsLayerView.updateScenario(scenarioModel);
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.setProfile(GraphicsModel.INSTANCE.nextActiveProfile());
            ScenarioModel scenarioModel = scenario.getModel(userRole);
            terrainLayerView.updateScenario(scenarioModel);
            unitsLayerView.updateScenario(scenarioModel);
            gridLayerView.updateScenario(scenarioModel);
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.setProfile(GraphicsModel.INSTANCE.previousActiveProfile());
            ScenarioModel scenarioModel = scenario.getModel(userRole);
            terrainLayerView.updateScenario(scenarioModel);
            unitsLayerView.updateScenario(scenarioModel);
            gridLayerView.updateScenario(scenarioModel);
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.switchLayerVisible(GridLayerViewer.NAME);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.switchLayerVisible(UnitsLayerViewer.NAME);
        }
    }
}
