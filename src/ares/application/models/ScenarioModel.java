package ares.application.models;

import ares.application.models.board.BoardModel;
import ares.application.models.forces.ForceModel;
import ares.platform.model.AbstractModel;
import ares.scenario.Scenario;
import ares.scenario.board.BoardInfo;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ScenarioModel extends AbstractModel {
    private final Scenario scenario;
    private final BoardModel boardModel;
    private final ForceModel[] forceModel;

    public ScenarioModel(Scenario scenario, InformationLevel informationLevel) {
        super(informationLevel);
        this.scenario = scenario;
        boardModel= scenario.getBoard().getCompleteModel();
        Force[] forces =scenario.getForces();
        forceModel = new ForceModel[forces.length];
        for (int i = 0; i < forces.length; i++) {
            forceModel[i] = forces[i].getModel(null);
        }
        
    }

    
    public BoardInfo getBoardInfo() {
        return scenario.getBoardInfo();
    }
    
    public BoardModel getBoardModel() {
        return boardModel;
    }

    public ForceModel[] getForceModel() {
        for (Force force : scenario.getForces()) {
            forceModel
        }
        return forceModel;
    }
    
    
}
