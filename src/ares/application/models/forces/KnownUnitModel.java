package ares.application.models.forces;

import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class KnownUnitModel extends IdentifiedUnitModel {

    public KnownUnitModel(Unit unit) {
        super(unit, KnowledgeCategory.COMPLETE);
    }

    public int getStamina() {
        return unit.getEndurance() * 100 / Unit.MAX_ENDURANCE;
    }
}
