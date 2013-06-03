package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.DoubleBoardInteractor;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.controllers.BoardController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.platform.action.ActionGroup;
import ares.platform.scenario.Scenario;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ComparatorController implements ActionController {

    private final DoubleBoardInteractor interactor;
    private static final Logger LOG = Logger.getLogger(ComparatorController.class.getName());
    private Scenario scenario;
    private final BoardController leftBoardController;

    public ComparatorController(DoubleBoardInteractor interactor) {
        this.interactor = interactor;
        interactor.registerLogger(LOG);
        leftBoardController = new BoardController(interactor);
        
    }


    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        // obtain the scenario model with the active userRole
        ScenarioModel scenarioModel = scenario.getModel();
        interactor.getBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        interactor.getBoardView().loadScenario(scenarioModel);
        interactor.getSecondBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        interactor.getSecondBoardView().loadScenario(scenarioModel);
    }

    @Override
    public ActionGroup getActionGroup() {
        return leftBoardController.getActionGroup();
    }


}
