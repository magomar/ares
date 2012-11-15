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

    private final Collection<UnitModel> unitModels;

    public KnownForceModel(Force force) {
        super(force, InformationLevel.COMPLETE);
        unitModels = new ArrayList<>();
        for (Unit unit : force.getActiveUnits()) {
            UnitModel unitModel = unit.getCompleteModel();
            if (unitModel != null) {
                unitModels.add(unitModel);
            }
        }
    }

    public Collection<UnitModel> getUnitModels() {
        return unitModels;
    }
}
