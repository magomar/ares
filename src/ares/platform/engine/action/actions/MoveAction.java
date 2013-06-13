package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionType;
import ares.platform.engine.algorithms.pathfinding.Node;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.movement.MovementCost;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.Scale;
import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class MoveAction extends AbstractAction {

    protected Path path;
    protected Node currentNode;
    /**
     * Time to complete next partial move
     */
    protected int timeToNextMovement;
    /**
     * Actual speed for this particular action, having into account not just the base speed and movement costs
     * occasioned by the terrain, which are all precomputed for every unit and tile respectively, but also the dinamic
     * aspects of the scenario, such as the traffic density (a function of the number of horses and vehicles) and the
     * presence of enemy units in the vicinity
     */
    protected int speed;

    public MoveAction(Unit unit, ActionType type, Path path) {
        super(unit, type);
        this.path = path;
        currentNode = path.getFirst().getNext();
        Tile destination = currentNode.getTile();
        Direction direction = currentNode.getDirection();
        MovementCost moveCost = destination.getEnterCost(direction);
        int cost = moveCost.getActualCost(unit);
        speed = unit.getSpeed() / cost;
        // TODO avoid creating move actions for static units (speed=0) !
        timeToNextMovement = (speed > 0 ? Scale.INSTANCE.getTileSize() / speed : Integer.MAX_VALUE);
    }

    /**
     * Change the location of the acting unit to the location of the currentNode in the movement path and moves the
     * reference to currentNode to the next node (if there is a next node). If there are no more nodes the movement has
     * ended, so {@link #timeToNextMovement} is set to 0. Otherwise {@link #timeToNextMovement} has to be computed
     * again. Once computed, it is adjusted to reflect the part of the time tick not really necessary to complete a
     * partial move, that is, the amount of time left is substracted from the new {@link #timeToNextMovement}. This
     * adjustment avoids the precission error (due to the conversion between minutes and ticks) being accumulated per
     * each partial movement (the precission error only happens once for the entire movement)
     */
    protected void completePartialMove() {
        unit.move(currentNode.getDirection().getOpposite());
        // start moving to the next node
        currentNode = currentNode.getNext();
        if (currentNode != null) {
            Tile destination = currentNode.getTile();
            MovementCost moveCost = destination.getEnterCost(currentNode.getDirection());
            int cost = moveCost.getActualCost(unit);
            speed = unit.getSpeed() / cost;
            // timeToNextMovement <= 0. If it is negative we can add it to the new timeToNextMovement as a way to not propagate the precision error each movement segment
            timeToNextMovement = (speed > 0 ? (int) (Scale.INSTANCE.getTileSize() / speed) + timeToNextMovement : Integer.MAX_VALUE);
        }
    }

    @Override
    public boolean isComplete() {
        return (currentNode.getNext() == null && timeToNextMovement <= Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    @Override
    protected void applyOngoingEffects() {
        if (timeToNextMovement <= 0) {
            completePartialMove();
        } else {
            timeToNextMovement -= Clock.INSTANCE.getMINUTES_PER_TICK();
        }
    }

    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return super.toString() + "\n From " + path.getFirst() + " to " + path.getLast() + " at " + (speed * 60.0 / 1000) + " km/h";
    }
}
