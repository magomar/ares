package ares.platform.engine.action.actions;

import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.action.ActionType;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.movement.MovementCost;
import ares.platform.scenario.Scale;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class CombatAction extends SurfaceMoveAction {
    protected boolean engaging = false;

    public CombatAction(ActionType actionType, Unit unit, Path path) {
        this(actionType, unit, AS_SOON_AS_POSSIBLE, path);
    }

    public CombatAction(ActionType actionType, Unit unit, int start, Path path) {
        this(actionType, unit, start, TIME_UNKNOWN, path);
    }

    public CombatAction(ActionType actionType, Unit unit, int start, int combatDuration, Path path) {
        super(actionType, unit, start, combatDuration, path);
    }

    /**
     * Change the location of the acting unit to the location of the currentNode in the movement path and moves the
     * reference to currentNode to the next node (if there is a next node). If there are no more nodes the movement has
     * ended, so {@link #timeToNextMovement} is set to 0. Otherwise {@link #timeToNextMovement} has to be computed
     * again. Once computed, it is adjusted to reflect the part of the time tick not really necessary to finish a
     * partial move, that is, the amount of time left is subtracted from the new {@link #timeToNextMovement}. This
     * adjustment avoids the precision error (due to the conversion between minutes and ticks) being accumulated per
     * each partial movement (the maximum precision error will be bounded by a single time tick for the entire movement action)
     */
    protected void completePartialMove(ActionSpace actionSpace) {
        Tile tile = currentNode.getTile();
        if (tile.hasEnemies(unit.getForce())) {
            engaging = true;
            System.out.println("Engaging !");
            actionSpace.putAction(tile, this);
            return;
        }

        unit.move(currentNode.getDirection().getOpposite());
        // start moving to the next node
        currentNode = currentNode.getNext();
        if (currentNode != null) {
            Tile destination = currentNode.getTile();
            MovementCost moveCost = destination.getEnterCost(currentNode.getDirection());
            int cost = moveCost.getActualCost(unit);
            speed = unit.getSpeed() / cost;
            // timeToNextMovement <= 0. If it is negative we can add it to the new timeToNextMovement as a way to not propagate the precision error each movement segment
            timeToNextMovement = (speed > 0 ? Scale.INSTANCE.getTileSize() / speed + timeToNextMovement : Integer.MAX_VALUE);
        }
    }

    @Override
    public boolean isFeasible() {
        return true;
    }
}
