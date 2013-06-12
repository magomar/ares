package ares.application.shared.controllers;

import ares.application.player.boundaries.interactors.MiniMapInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.MiniMapNavigationLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.layerviews.MiniMapNavigationLayerView;
import ares.application.shared.models.ScenarioModel;
import ares.platform.action.ActionGroup;
import ares.platform.action.CommandAction;
import ares.platform.action.CommandGroup;
import ares.platform.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapController implements ActionController {

    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final BoardViewer miniMapView;
    private final BoardViewer boardView;
    private final TerrainLayerViewer terrainLayer;
    private final UnitsLayerViewer unitsLayer;
    private final MiniMapNavigationLayerViewer navigationLayer;
    private ScenarioModel scenarioModel;

    public MiniMapController(MiniMapInteractor miniMapInteractor) {
        miniMapView = miniMapInteractor.getMiniMapView();
        boardView = miniMapInteractor.getBoardView();
        terrainLayer = (TerrainLayerViewer) miniMapView.getLayerView(TerrainLayerViewer.NAME);
        unitsLayer = (UnitsLayerViewer) miniMapView.getLayerView(UnitsLayerViewer.NAME);
        navigationLayer = (MiniMapNavigationLayerViewer) miniMapView.getLayerView(MiniMapNavigationLayerView.NAME);
        // create action groups
        Action[] viewActions = {zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);
    }

    public void setScenario(Scenario scenario, int profile) {
        this.scenarioModel = scenario.getModel();
        miniMapView.setProfile(profile);
        // Render board: paint terrain and units
        terrainLayer.updateScenario(scenarioModel);
        unitsLayer.updateScenario(scenarioModel);
        navigationLayer.update(boardView.getContentPane().getViewport(), boardView.getProfile());
    }

    public void changeBoardViewport() {
        navigationLayer.update(boardView.getContentPane().getViewport(), boardView.getProfile());
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.setProfile(GraphicsModel.INSTANCE.nextActiveProfile());
            terrainLayer.updateScenario(scenarioModel);
            unitsLayer.updateScenario(scenarioModel);
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            miniMapView.setProfile(GraphicsModel.INSTANCE.previousActiveProfile());
            terrainLayer.updateScenario(scenarioModel);
            unitsLayer.updateScenario(scenarioModel);
        }
    }

}
