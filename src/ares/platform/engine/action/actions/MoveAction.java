package ares.platform.engine.action.actions;

import ares.platform.engine.action.AbstractAction;
import ares.platform.engine.action.ActionSpace;
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
 * Generic movement action.
 * A single movement action may comprise movement along a sequence of adjacent tiles. The sequence of movements is
 * represented by a {@link Path} object.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */

public abstract class MoveAction extends AbstractAction {

    /**
     * Path to follow while performing this action. A path contains a sequence of tiles in the map
     */
    protected final Path path;
    /**
     * Current node in the {@link #path} of this action. A node references a tile in the map.
     */
    protected Node currentNode;
    /**
     * Time to finish next partial move, in minutes
     */
    protected int timeToNextMovement;
    /**
     * Actual speed for this movement, having into account not just the base speed and movement costs
     * occasioned by the terrain, which are all precomputed for every unit and tile respectively, but also dynamic
     * aspects such as the traffic density (a function of the number of horses and vehicles) and the
     * presence of enemy units in the vicinity.
     */
    protected int speed;

    public MoveAction(ActionType actionType, Unit unit, Path path, ActionSpace actionSpace) {
        this(actionType, unit, AS_SOON_AS_POSSIBLE, path, actionSpace);
    }

    public MoveAction(ActionType actionType, Unit unit, int start, Path path, ActionSpace actionSpace) {
        this(actionType, unit, start, TIME_UNKNOWN, path, actionSpace);
    }
    public MoveAction(ActionType actionType, Unit unit, int start, int duration, Path path, ActionSpace actionSpace) {
        super(actionType, unit, start, duration, actionSpace);
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
     * again. Once computed, it is adjusted to reflect the part of the time tick not really necessary to finish a
     * partial move, that is, the amount of time left is subtracted from the new {@link #timeToNextMovement}. This
     * adjustment avoids the precision error (due to the conversion between minutes and ticks) being accumulated per
     * each partial movement (the maximum precision error will be bounded by a single time tick for the entire movement action)
     *
     * @see #applyOngoingEffects()
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
            // If remaining timeToNextMovement <= 0 we add it to the new timeToNextMovement (actually it is subtracted)
            // as a way to not propagate the precision error for each partial movement
            timeToNextMovement = (speed > 0 ? Scale.INSTANCE.getTileSize() / speed + timeToNextMovement : Integer.MAX_VALUE);
        }
    }

    @Override
    public boolean isComplete() {
        return (currentNode == null && timeToNextMovement <= 0);
    }

    @Override
    protected void applyOngoingEffects() {
        timeToNextMovement -= Clock.INSTANCE.getMINUTES_PER_TICK();
        if (timeToNextMovement <= 0) {
            completePartialMove();
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
