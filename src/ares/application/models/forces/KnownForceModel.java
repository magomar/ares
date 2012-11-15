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
public final class KnownForceModel extends ForceModel {

    public KnownForceModel(Force force) {
        super(force, InformationLevel.COMPLETE, new ArrayList<UnitModel>());
    }

    @Override
    public Collection<UnitModel> getUnitModels() {
        for (Unit unit : force.getActiveUnits()) {
            UnitModel unitModel = unit.getCompleteModel();
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
        return unitModels;
    }
}
