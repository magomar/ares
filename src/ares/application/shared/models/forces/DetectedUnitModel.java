package ares.application.shared.models.forces;

import ares.platform.scenario.forces.UnitType;
import ares.platform.scenario.forces.Unit;
import ares.platform.scenario.forces.UnitsColor;
import ares.application.shared.models.board.TileModel;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.knowledge.KnowledgeCategory;

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
    public UnitsColor getColor() {
        return unit.getColor();
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

    @Override
    public TacticalMission getTacticalMission() {
        return null;
    }
}
