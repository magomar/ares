package ares.application.models;

import ares.scenario.Scenario;
import ares.scenario.board.BoardInfo;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioModel {
    private final Scenario scenario;
    private final

    public ScenarioModel(Scenario scenario, UserRole userRole) {
        this.scenario = scenario;
    }
    
    public BoardInfo getBoardInfo() {
        return scenario.getBoardInfo();
    }
    
    public BoardModel getBoardModel() {
        return scenario.getBoard().getModel(userRole);
    }
}
