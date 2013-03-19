package ares.application.models.forces;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class FormationModel extends RoleMediatedModel {

    private final Formation formation;

    public FormationModel(Formation formation, UserRole userRole) {
        super(userRole);
        this.formation = formation;
    }

    public String getName() {
        return formation.getName();
    }

    public Collection<UnitModel> getUnitModels() {
        Collection<UnitModel> unitModels = new ArrayList<>();
        for (Unit unit : formation.getAvailableUnits()) {
            UnitModel unitModel = unit.getModel(getUserRole());
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }
}
