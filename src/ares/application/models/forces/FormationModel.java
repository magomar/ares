package ares.application.models.forces;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.List;

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

    public List<FormationModel> getSubordinatesModels() {
        List<FormationModel> subordinates = new ArrayList<>();
        for (Formation subordinate : formation.getSubordinates()) {
            FormationModel formationModel = subordinate.getModel(userRole);
            if (formationModel != null) {
                subordinates.add(formationModel);
            }
        }
        return subordinates;
    }

    public List<UnitModel> getUnitModels() {
        List<UnitModel> unitModels = new ArrayList<>();
        for (Unit unit : formation.getAvailableUnits()) {
            UnitModel unitModel = unit.getModel(userRole);
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }
}
