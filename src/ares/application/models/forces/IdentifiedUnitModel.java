package ares.application.models.forces;

import ares.scenario.Scale;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.Echelon;
import ares.scenario.forces.Unit;
import ares.scenario.forces.UnitType;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class IdentifiedUnitModel extends DetectedUnitModel {

    public IdentifiedUnitModel(Unit unit, Scale scale) {
        super(unit, scale, InformationLevel.GOOD);
    }

    protected IdentifiedUnitModel(Unit unit, Scale scale, InformationLevel informationLevel) {
        super(unit, scale, informationLevel);
    }

    public String getName() {
        return unit.getName();
    }

    public int getAttackStrength() {
        return (int) (unit.getEfficacy() * (unit.getAntiTank() + unit.getAntiPersonnel() / scale.getArea()));
    }

    public int getDefenseStrength() {
        return (int) ((unit.getEfficacy() * unit.getDefense() / scale.getArea()));
    }

    public int getHealth() {
        return (int) (unit.getEfficacy() - 1 / 20);
    }

    public Echelon getEchelon() {
        return unit.getEchelon();
    }
}
