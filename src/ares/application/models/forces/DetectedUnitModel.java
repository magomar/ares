package ares.application.models.forces;

import ares.application.gui.UnitIcons;
import ares.application.models.board.TileModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.forces.*;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class DetectedUnitModel extends UnitModel {

    public DetectedUnitModel(Unit unit) {
        super(unit, KnowledgeCategory.POOR);
    }

    protected DetectedUnitModel(Unit unit, KnowledgeCategory kLevel) {
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
    public UnitIcons getColor() {
        return UnitIcons.values()[unit.getColor()];
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
    public String getForce() {
        return unit.getForce().getName();
    }

    @Override
    public String getDescription() {
        return unit.getType().name();
    }
}
