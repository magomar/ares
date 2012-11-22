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
        return "";
    }
    
    @Override
    public UnitColors getColor() {
        return UnitColors.values()[unit.getColor()];
    }

    @Override
    public int getIconId() {
        return unit.getIconId();
    }

    @Override
    public TileModel getLocation() {
        return unit.getLocation().getModel(kLevel);
    }

    @Override
    public String getFormation() {
        return "";
    }

    @Override
    public String getDescription() {
        return unit.getType().name();
    }
}
