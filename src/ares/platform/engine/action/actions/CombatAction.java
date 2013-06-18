package ares.platform.engine.action.actions;

import ares.platform.engine.action.ActionType;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.scenario.forces.Unit;

/**
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
