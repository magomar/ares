package ares.application.models.forces;

import ares.application.models.board.TileModel;
import ares.platform.model.UserRole;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.*;
import java.awt.Point;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class DetectedUnitModel extends UnitModel {

    public DetectedUnitModel(Unit unit) {
        super(unit, KnowledgeLevel.POOR);
    }

    protected DetectedUnitModel(Unit unit, KnowledgeLevel kLevel) {
        super(unit, kLevel);
    }

    public UnitType getUnitType() {
        return unit.getType();
    }

    @Override
    public int getColor() {
        return unit.getColor();
    }

    @Override
    public int getIconId() {
        return unit.getIconId();
    }

    @Override
    public Point getPosition() {
        //TODO why x and y are modified ?
        return new Point(unit.getLocation().getX() - 1, unit.getLocation().getY() + 1);
    }

    @Override
    public TileModel getLocation() {
        return unit.getLocation().getModel(kLevel);
    }
}
