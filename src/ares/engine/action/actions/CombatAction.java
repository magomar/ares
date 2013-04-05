package ares.engine.action.actions;

import ares.engine.action.ActionType;
import ares.engine.algorithms.pathfinding.Path;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatAction extends MoveAction {

    public CombatAction(Unit unit, ActionType type, Path path) {
        super(unit, type, path);
    }
    @Override
    public boolean isFeasible() {
        return true;
    }
}
