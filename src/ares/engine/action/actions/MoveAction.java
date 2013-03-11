package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.algorithms.routing.Node;
import ares.engine.algorithms.routing.Path;
import ares.engine.movement.MovementCost;
import ares.scenario.Clock;
import ares.scenario.Scale;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

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
        Direction fromDir = currentNode.getDirection().getOpposite();
        MovementCost moveCost = unit.getLocation().getMoveCost(fromDir);
        int cost = moveCost.getActualCost(unit, destination, fromDir);
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
        // move to next node in path
        Direction fromDir = currentNode.getDirection().getOpposite();
        unit.move(fromDir);
        // start moving to the next node
        currentNode = currentNode.getNext();
        if (currentNode != null) {
            Tile destination = currentNode.getTile();
            fromDir = currentNode.getDirection().getOpposite();
            MovementCost moveCost = unit.getLocation().getMoveCost(fromDir);
            int cost = moveCost.getActualCost(unit, destination, fromDir);
            speed = unit.getSpeed() / cost;
            // timeToNextMovement <= 0. If it is negative we can add it to the new timeToNextMovement as a way to not propagate the precision error each movement segment
            timeToNextMovement = (speed > 0 ? (int) (Scale.INSTANCE.getTileSize() / speed) + timeToNextMovement : Integer.MAX_VALUE);
        } 
//        else { // this was the last partial movement, so movement action is complete
//            timeToNextMovement = 0;
//            timeToComplete = 0;
//        }
    }

    @Override
    protected boolean canBeCompleted() {
        return (currentNode.getNext()==null && timeToNextMovement <= Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    @Override
    public void applyOngoingEffects() {
        if (timeToNextMovement <= 0) {
            completePartialMove();
        } else {
            timeToNextMovement -= Clock.INSTANCE.getMINUTES_PER_TICK();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " from " + path.getFirst() + " to " + path.getLast() + " at " + (speed * 60.0 / 1000) + " km/h";
    }
}
