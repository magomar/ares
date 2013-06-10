package ares.application.shared.controllers;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.models.ScenarioModel;
import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractBoardController {

    protected final BoardViewer boardView;
    protected Scenario scenario;

    public AbstractBoardController(BoardInteractor interactor) {
        this.boardView = interactor.getBoardView();
    }

    public void setScenario(Scenario scenario, int profile) {
        this.scenario = scenario;
        boardView.setProfile(profile);
        ScenarioModel scenarioModel = scenario.getModel();
        boardView.loadScenario(scenarioModel);
    }
}
