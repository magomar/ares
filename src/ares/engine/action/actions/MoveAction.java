package ares.engine.action.actions;

import ares.engine.action.AbstractAction;
import ares.engine.action.ActionType;
import ares.engine.actors.UnitActor;
import ares.model.board.Direction;
import ares.model.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class MoveAction extends AbstractAction {

    /**
     * Direction of the movement, relative to the origin of the movement
     */
    protected Direction fromDir;
    /**
     * Actual speed for this particular action, having into account not just the base speed and movement costs
     * occasioned by the terrain, which are all precomputed for every actor and tile respectively, but also the dinamic
     * aspects of the scenario, such as the traffic density (a function of the number of horses and vehicles) and the
     * presence of enemy units in the vicinity
     */
    protected int speed;

    public MoveAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start, Direction fromDir, int distance) {
        super(actor, type, origin, destination, start);
        this.fromDir = fromDir;
        int cost = origin.getMoveCost(fromDir).getActualCost(actor.getUnit(), destination, fromDir);
        speed = actor.getUnit().getSpeed() / cost;
        timeToComplete = (speed > 0 ? (int) (distance / speed) : Integer.MAX_VALUE);
    }


    /**
     *
     * @return the actual speed of the actor when performing this movement (m/m)
     */
    public int getSpeed() {
        return speed;
    }
}
