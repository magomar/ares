package ares.engine.action;

import ares.engine.actors.Actor;
import ares.engine.realtime.Clock;
import ares.model.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    public void execute(Clock clock);
    
    public void setStart(int s);

    public Actor getActor();

    public ActionType getType();

    public int getStart();

    public int getFinish();

    public Tile getOrigin();

    public Tile getDestination();

    public ActionState getState();
}
