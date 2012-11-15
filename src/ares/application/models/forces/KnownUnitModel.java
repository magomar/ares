package ares.application.models.forces;

import ares.scenario.Scale;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.*;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class KnownUnitModel extends IdentifiedUnitModel {

    public KnownUnitModel(Unit unit, Scale scale) {
        super(unit, scale, InformationLevel.COMPLETE);
    }
    
    // TODO add getter methods as needed ...
}
