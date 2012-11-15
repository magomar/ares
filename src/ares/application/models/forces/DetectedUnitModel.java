package ares.application.models.forces;

import ares.scenario.Scale;
import ares.scenario.board.InformationLevel;
import ares.scenario.forces.*;
import java.awt.Point;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class DetectedUnitModel extends UnitModel {

    public DetectedUnitModel(Unit unit, Scale scale) {
        super(unit, scale, InformationLevel.POOR);
    }

    protected DetectedUnitModel(Unit unit, Scale scale, InformationLevel informationLevel) {
        super(unit, scale, informationLevel);
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
    public Point getLocation() {
        return new Point(unit.getLocation().getX()-1, unit.getLocation().getY()+1);
    }
}
