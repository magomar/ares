package ares.application.models.forces;

import ares.platform.model.KnowledgeMediatedModel;
import ares.scenario.Scale;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.forces.Unit;
import java.awt.Point;

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

    public abstract int getColor();

    public abstract int getIconId();

    //Unit's position varies depending on the information level
    public abstract Point getLocation();

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
