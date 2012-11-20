package ares.application.models.forces;

import ares.application.gui_components.UnitColors;
import ares.application.models.board.TileModel;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.*;

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
    public String getName() {
        return unit.getName();
    }
    
    @Override
    public UnitColors getColor() {
        return UnitColors.values()[unit.getColor()];
    }

    @Override
    public int getIconId() {
        return unit.getIconId();
    }

//    @Override
//    public Point getCoordinates() {
//        //TODO why x and y are modified ?
          //Heine: It's a cheap way to move them from their actual location
          //I was trying to add confusion :P DetectedUnits might not be in the exact position, right?
          //This was faster than waiting for the engine to be finished
//        return new Point(unit.getLocation().getX() - 1, unit.getLocation().getY() + 1);
//    }

    @Override
    public TileModel getLocation() {
        return unit.getLocation().getModel(kLevel);
    }
}
