package ares.application.models.forces;

import ares.platform.scenario.forces.UnitsColor;
import ares.application.models.board.TileModel;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.engine.movement.MovementType;
import ares.platform.model.KnowledgeMediatedModel;
import ares.platform.model.UserRole;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class UnitModel extends KnowledgeMediatedModel {

    protected final Unit unit;

    public UnitModel(Unit unit, KnowledgeCategory kLevel) {
        super(kLevel);
        this.unit = unit;
    }

    public abstract String getName();

    public abstract UnitsColor getColor();

    public abstract int getIconId();

    public abstract TileModel getLocation();

    public abstract String getFormation();

    public abstract String getForce();

    public abstract String getDescription();

    public abstract TacticalMission getTacticalMission();

    public MovementType getMovement() {
        return unit.getMovement();
    }

    public FormationModel getFormationModel(UserRole role) {
        return unit.getFormation().getModel(role);
    }

    @Override
    public String toString() {
        return getName();
    }
    
    
}
