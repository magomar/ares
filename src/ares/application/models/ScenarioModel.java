package ares.application.models;

import ares.application.gui.GraphicsModel;
import ares.application.models.board.BoardModel;
import ares.application.models.forces.ForceModel;
import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class ScenarioModel extends RoleMediatedModel {

    private final Scenario scenario;
    private final ForceModel[] forceModel;

    public ScenarioModel(Scenario scenario, UserRole userRole) {
        super(userRole);
        this.scenario = scenario;
        Force[] forces = scenario.getForces();
        forceModel = new ForceModel[forces.length];
        for (int i = 0; i < forces.length; i++) {
            forceModel[i] = forces[i].getModel(userRole);
        }
    }

    public BoardModel getBoardModel() {
        return scenario.getBoard().getModel(userRole);
    }

    public ForceModel[] getForceModel() {
        return forceModel;

    }
}
