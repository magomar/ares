package ares.application.models.forces;

import ares.application.gui_components.UnitColors;
import ares.application.models.board.TileModel;
import ares.platform.model.KnowledgeMediatedModel;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class UnitModel extends KnowledgeMediatedModel {

    protected final Unit unit;

    public UnitModel(Unit unit, KnowledgeLevel kLevel) {
        super(kLevel);
        this.unit = unit;
    }
    
    public abstract UnitColors getColor();

    public abstract int getIconId();

    //Unit's position varies depending on the information level
//    public abstract Point getCoordinates();
    
    public abstract TileModel getLocation();

//    public static UnitModel getUnitModel(Unit unit, KnowledgeLevel kLevel) {
//        switch (kLevel) {
//            case POOR:
//                return new DetectedUnitModel(unit);
//            case GOOD:
//                return new IdentifiedUnitModel(unit);
//            case COMPLETE:
//                return new KnownUnitModel(unit);
//            default:
//                return null;
//        }
//    }
}
