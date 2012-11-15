package ares.application.models.forces;

import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class KnownUnitModel extends IdentifiedUnitModel {

    public KnownUnitModel(Unit unit) {
        super(unit, KnowledgeLevel.COMPLETE);
    }
    
    // TODO add getter methods as needed ...
}
