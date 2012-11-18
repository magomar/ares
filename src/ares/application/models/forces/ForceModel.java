package ares.application.models.forces;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.Force;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ForceModel extends RoleMediatedModel {

    protected final Force force;

    public ForceModel(Force force, UserRole role) {
        super(role);
        this.force = force;
    }

    public String getName() {
        return force.getName();
    }

    public Collection<UnitModel> getUnitModels() {
        Collection<UnitModel> unitModels = new ArrayList<>();
        for (Unit unit : force.getActiveUnits()) {
            UnitModel unitModel = unit.getModel(getUserRole());
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }
}
