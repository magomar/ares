package ares.platform.engine.combat;

import ares.platform.engine.action.AbstractInteraction;
import ares.platform.engine.action.Action;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Combat extends AbstractInteraction {

    protected static final double ASSAULT_KILLING_RATIO = 1.0;
    protected static final double ATTACK_KILLING_RATIO = 0.66;
    protected static final double SUPPORT_KILLING_RATIO = 0.66;
    protected static final double DEFENSE_KILLING_RATIO = 1;
    protected final Collection<Unit> attackers;
    protected final Collection<Unit> defenders;
    protected Tile location;


    public Combat(Action action) {
        super(action);
        this.attackers = new ArrayList<>();
        this.defenders = new ArrayList<>();
    }

    @Override
    public void addAction(Action action) {
        super.addAction(action);
        attackers.add(action.getUnit());
    }

    public void addUnit(Unit unit) {
        Action action = unit.getMission().getCurrentAction();
//        switch (action.getUnitType()) {
//            case ASSAULT:
//                assaultUnits.add(unit);
//                break;
//            case ATTACK_BY_FIRE:
//                attacktUnits.add(unit);
//                break;
//            case SUPPORT_BY_FIRE:
//                supportUnits.add(unit);
//                break;
//            default:
//                defenseUnits.add(unit);
//        }
    }

    @Override
    public void execute() {
        for (Action action : actions) {
            Unit unit = action.getUnit();
        }
    }
}
