package ares.application.models.forces;

import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Force;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class EnemyForceModel extends ForceModel {

    public EnemyForceModel(Force force) {
        super(force, InformationLevel.POOR);
    }

    public Collection<UnitModel> getUnitModels() {
        Collection<UnitModel>  unitModels = new ArrayList<>();
        for (Unit unit : force.getActiveUnits()) {
            UnitModel unitModel = unit.getModel(force);
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }
}
