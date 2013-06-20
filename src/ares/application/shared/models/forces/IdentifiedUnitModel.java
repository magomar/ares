package ares.application.shared.models.forces;

import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.scenario.forces.Echelon;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class IdentifiedUnitModel extends DetectedUnitModel {

    public IdentifiedUnitModel(Unit unit) {
        super(unit, KnowledgeCategory.GOOD);
    }

    protected IdentifiedUnitModel(Unit unit, KnowledgeCategory kLevel) {
        super(unit, kLevel);
    }

    @Override
    public String getName() {
        return unit.getName();
    }

    public int getAttackStrength() {
        return (int) unit.getAttackStrength();
    }

    public int getDefenseStrength() {
        return (int) unit.getDefenseStrength();
    }

    public int getHealth() {
        return unit.getEfficacy() - 1 / 20;
    }

    public Echelon getEchelon() {
        return unit.getEchelon();
    }

    @Override
    public String getFormation() {
        return unit.getFormation().getName();
    }

    @Override
    public String getDescription() {
        return unit.toStringMultiline();
    }

}
