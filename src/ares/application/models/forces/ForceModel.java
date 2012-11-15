package ares.application.models.forces;

import ares.platform.model.FilteredAbstractModel;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Force;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class ForceModel extends FilteredAbstractModel {
    protected final Force force;
    protected Collection<UnitModel> unitModels;
    
    protected ForceModel(Force force, InformationLevel informationLevel, Collection<UnitModel> unitModels) {
        super(informationLevel);
        this.force = force;
        this.unitModels = unitModels;
    }

    public String getName() {
        return force.getName();
    }
    
    public abstract Collection<UnitModel> getUnitModels();


}
