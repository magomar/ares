package ares.application.shared.controllers;

import ares.application.player.boundaries.interactors.MiniMapInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.MiniMapNavigationLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.layerviews.MiniMapNavigationLayerView;
import ares.application.shared.models.ScenarioModel;
import ares.platform.scenario.Scenario;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapController {

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
        miniMapView.addMouseListener(new MiniMapMouseListener());
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

    private class MiniMapMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    Point tileCoord = GraphicsModel.INSTANCE.pixelToTile(me.getPoint(), miniMapView.getProfile());
                    boardView.centerViewOn(tileCoord);
                    break;
            }

        }
    }
}
