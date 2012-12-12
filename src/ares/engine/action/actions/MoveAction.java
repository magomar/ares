package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.engine.algorithms.routing.Node;
import ares.engine.algorithms.routing.Path;
import ares.engine.movement.MovementCost;
import ares.platform.model.UserRole;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class MoveAction extends AbstractAction {

    protected Path path;
    private Node currentNode;
    private int timeToMove;
    /**
     * Actual speed for this particular action, having into account not just the base speed and movement costs
     * occasioned by the terrain, which are all precomputed for every actor and tile respectively, but also the dinamic
     * aspects of the scenario, such as the traffic density (a function of the number of horses and vehicles) and the
     * presence of enemy units in the vicinity
     */
    protected int speed;
    protected int tileSize;

    public MoveAction(UnitActor actor, ActionType type, Path path, int tileSize) {
        super(actor, type);
        this.path = path;
        currentNode = path.getFirst().getNext();
        this.tileSize = tileSize;
        Tile destination = currentNode.getTile();
        Direction fromDir = currentNode.getFrom().getOpposite();
        MovementCost moveCost = actor.getUnit().getLocation().getMoveCost(fromDir);
        int cost = moveCost.getActualCost(actor.getUnit(), destination, fromDir);
        speed = actor.getUnit().getSpeed() / cost;
        timeToMove = (speed > 0 ? (int) (tileSize / speed) : Integer.MAX_VALUE);
        //FIXME Problems derived from the use of tile models and unit models instead of tiles and units. Discuss with Heine
    }

    protected void nextPathNode() {
        currentNode = currentNode.getNext();
        Tile destination = currentNode.getTile();
        Direction fromDir = currentNode.getFrom();
        actor.getUnit().move(fromDir);
        MovementCost moveCost = actor.getUnit().getLocation().getMoveCost(fromDir);
        int cost = moveCost.getActualCost(actor.getUnit(), destination, fromDir);
        speed = actor.getUnit().getSpeed() / cost;
        timeToMove = (speed > 0 ? (int) (tileSize / speed) : Integer.MAX_VALUE);
    }

    @Override
    public void applyOngoingEffects() {
        if (timeToMove <= 0) {
            if (timeToMove == 0) {
                nextPathNode();
            } else {
                int remainingTime = timeToMove; // negative value
                nextPathNode();
                timeToMove += remainingTime; //
            }
        }
    }

    @Override
    public String toString() {
        return super.toString() + " from " + path.getFirst() + " to " + path.getLast() + " at " + (speed * 60.0 / 1000) + " km/h";
    }
}
