package ares.application.models.forces;

import ares.application.gui_components.UnitColors;
import ares.application.models.board.TileModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.platform.model.KnowledgeMediatedModel;
import ares.scenario.forces.Unit;

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
    
    public abstract UnitColors getColor();

    public abstract int getIconId();

    public abstract TileModel getLocation();
   
    public abstract String getFormation();
    
    public abstract String getDescription();
}
