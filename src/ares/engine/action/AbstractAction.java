package ares.engine.action;

import ares.engine.action.actions.RestAction;
import ares.engine.action.actions.WaitAction;
import ares.engine.actors.Actor;
import ares.engine.actors.UnitActor;
import ares.engine.realtime.Clock;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.Comparator;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractAction implements Action {

    public static final Comparator<Action> ACTION_START_COMPARATOR = new ActionStartComparator();
    public static final Comparator<Action> ACTION_FINISH_COMPARATOR = new ActionFinishComparator();
    protected UnitActor actor;
    protected Tile location;
    protected Tile destination;
    protected ActionType type;

    /**
     * Before starting the action, this attribute holds the estimated time to start performing the action, specified in
     * minutes since the beginning of the scenario. Thereafter it holds the actual starting time
     */
    protected int start;
    /**
     * Before finishing the action, this attribute holds the estimated time to complete the action, specified in minutes
     * since the scenario begun. Thereafter it holds the actual finishing time
     */
    protected int finish;
    /**
     * Estimated remaining time to complete the action in minutes
     */
    protected int timeToComplete;
    protected ActionState state;

    public AbstractAction(UnitActor actor, ActionType type, Tile origin, Tile destination, int start) {
        this.actor = actor;
        this.type = type;
        this.start = start;
        this.location = origin;
        this.destination = destination;
        finish = Integer.MAX_VALUE;
        timeToComplete = finish - start;
        state = ActionState.CREATED;
    }

//    public AbstractAction(Unit actor, ActionType type, Tile origin, Tile destination, Clock clock) {
//        this(actor, type, origin, destination, clock.getCurrentTime(), clock);
//    }
//
//    public AbstractAction(Unit actor, ActionType type, Tile destination, int start, Clock clock) {
//        this(actor, type, destination, destination, start, clock);
//    }
//
//    public AbstractAction(Unit actor, ActionType type, Tile destination, Clock clock) {
//        this(actor, type, destination, destination, clock.getCurrentTime(), clock);
//    }
    protected boolean checkPreconditions(Clock clock) {
        Unit unit = actor.getUnit();
        int time = clock.getCurrentTime();
        if (unit.getEndurance() <= 0) {
            state = ActionState.DELAYED;
            actor.addAction(this);
            Action newAction = new RestAction(actor, destination, time);
            newAction.execute(clock);
            return false;
        }
        if (state == ActionState.CREATED && unit.getOpState() != type.getPrecondition()) {
            state = ActionState.DELAYED;
            actor.addAction(this);
            Action newAction;
            if (unit.getEndurance() >= ActionType.WAIT.getWearRate()) {
                newAction = new WaitAction(actor, destination, time);
            } else {
                newAction = new RestAction(actor, destination, time);
            }
            newAction.execute(clock);
            return false;
        }
        if (unit.getLocation() != location) {
            Action newAction;
            if (unit.getEndurance() >= ActionType.WAIT.getWearRate()) {
                newAction = new WaitAction(actor, destination, time);
            } else {
                newAction = new RestAction(actor, destination, time);
            }
            newAction.execute(clock);
            return false;
        }

        if (state == ActionState.CREATED || state == ActionState.DELAYED) {
            state = ActionState.INITIATED;
            start = Math.max(start, clock.getCurrentTime() - clock.MINUTES_PER_TICK);
            unit.setOpState(type.getEffectWhile());
        }
        return true;
    }

    @Override
    public void setStart(int s) {
        start = s;
        timeToComplete = finish - start;
    }

    @Override
    public ActionState getState() {
        return state;
    }

    @Override
    public Tile getOrigin() {
        return location;
    }

    @Override
    public Tile getDestination() {
        return destination;
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getFinish() {
        return finish;
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public Actor getActor() {
        return actor;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    @Override
    public String toString() {
        return "Action{" + "actor=" + actor + ", type=" + type + ", start=" + start + '}';
    }

    private static class ActionStartComparator implements Comparator<Action> {

        @Override
        public int compare(Action o1, Action o2) {
            int start1 = o1.getStart();
            int start2 = o2.getStart();
            int diff = start1 - start2;
            return (diff == 0 ? o1.getFinish() - o2.getStart() : diff);
        }
    }

    private static class ActionFinishComparator implements Comparator<Action> {

        @Override
        public int compare(Action o1, Action o2) {
            int finish1 = o1.getFinish();
            int finish2 = o2.getFinish();
            int diff = finish1 - finish2;
            return (diff == 0 ? o1.getStart() - o2.getStart() : diff);
        }
    }
}
