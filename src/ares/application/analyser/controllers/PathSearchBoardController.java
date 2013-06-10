package ares.application.analyser.controllers;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.models.ScenarioModel;
import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class PathSearchBoardController {

    private final BoardViewer boardView;
    private final TerrainLayerViewer terrainLayer;
    private final GridLayerViewer gridLayer;
    private ScenarioModel scenarioModel;

    public PathSearchBoardController(BoardInteractor interactor) {
        this.boardView = interactor.getBoardView();
        terrainLayer = (TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME);
        gridLayer = (GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME);
    }

    public void setScenario(Scenario scenario, int profile) {
        this.scenarioModel = scenario.getModel();
        boardView.setProfile(profile);
        terrainLayer.updateScenario(scenarioModel);
        gridLayer.updateScenario(scenarioModel);
    }

    public void changeProfile(int profile) {
        boardView.setProfile(profile);
        terrainLayer.updateScenario(scenarioModel);
    }

    public void switchLayerVisible(String layerName) {
        boardView.switchLayerVisible(layerName);
    }
}