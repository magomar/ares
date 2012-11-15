package ares.application.models.forces;

import ares.platform.model.FilteredAbstractModel;
import ares.scenario.Scale;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class UnitModel extends FilteredAbstractModel {

    protected final Unit unit;
    protected final Scale scale;
   

    public UnitModel(Unit unit, Scale scale, InformationLevel informationLevel) {
        super(informationLevel);
        this.unit = unit;
        this.scale = scale;
    }
    
}
