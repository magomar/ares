package ares.application.models.forces;

import ares.scenario.Scale;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.*;
import java.awt.Point;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class IdentifiedUnitModel extends DetectedUnitModel {

    public IdentifiedUnitModel(Unit unit) {
        super(unit, KnowledgeLevel.GOOD);
    }

    protected IdentifiedUnitModel(Unit unit, KnowledgeLevel kLevel) {
        super(unit, kLevel);
    }

    public String getName() {
        return unit.getName();
    }

    public int getAttackStrength() {
//        return (int) (unit.getEfficacy() * (unit.getAntiTank() + unit.getAntiPersonnel() / scale.getArea()));
        return unit.getEfficacy() * (unit.getAntiTank() + unit.getAntiPersonnel());
    }

    public int getDefenseStrength() {
//        return (int) ((unit.getEfficacy() * unit.getDefense() / scale.getArea()));
        return unit.getEfficacy() * unit.getDefense();
    }

    public int getHealth() {
        return (int) (unit.getEfficacy() - 1 / 20);
    }

    public Echelon getEchelon() {
        return unit.getEchelon();
    }

    @Override
    public Point getPosition() {
        return new Point(unit.getLocation().getX(), unit.getLocation().getY());
    }

}
