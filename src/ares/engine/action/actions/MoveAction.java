package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionState;
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
    protected int timeToMove;
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
        timeToMove = (speed > 0 ? (int) (Scale.INSTANCE.getTileSize() / speed) : Integer.MAX_VALUE);
    }

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
            timeToMove = (speed > 0 ? (int) (Scale.INSTANCE.getTileSize() / speed) : Integer.MAX_VALUE);
        } else { // this was the last partial movement, so movement action is complete
            int duration = timeToComplete;
            timeToComplete = 0;
            state = ActionState.COMPLETED;
            finish = Clock.INSTANCE.getCurrentTime() - Clock.INSTANCE.getMINUTES_PER_TICK() + duration;
            unit.setOpState(type.getEffectAfter());
//            int wear = (int) (type.getWearRate() * duration); // wear already applied
//            unit.changeEndurance(wear);
            applyEffects();
        }
    }

    @Override
    public void applyOngoingEffects() {
        if (timeToMove <= 0) {
            if (timeToMove == 0) {
                completePartialMove();
            } else {
                int remainingTime = timeToMove; // negative value
                completePartialMove();
                timeToMove += remainingTime; //
            }
        } else {
            timeToMove -= Clock.INSTANCE.getMINUTES_PER_TICK();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " from " + path.getFirst() + " to " + path.getLast() + " at " + (speed * 60.0 / 1000) + " km/h";
    }
}
