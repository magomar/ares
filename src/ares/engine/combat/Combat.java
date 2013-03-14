package ares.engine.combat;

import ares.engine.action.Action;
import ares.engine.action.actions.CombatAction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Combat {

    protected static final double ASSAULT_KILLING_RATIO = 1.0;
    protected static final double ATTACK_KILLING_RATIO = 0.66;
    protected static final double SUPPORT_KILLING_RATIO = 0.66;
    protected static final double DEFENSE_KILLING_RATIO = 1;
    protected Collection<Unit> attackers;
    protected Collection<Unit> defenders;
    protected Tile location;

    public Combat(CombatAction action) {
        this.attackers = new HashSet<>();
        this.defenders = new HashSet<>();
    }

    public void addUnit(Unit unit) {
        Action action = unit.getMission().getCurrentAction();
//        switch (action.getType()) {
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

    public void execute() {

    }
}
