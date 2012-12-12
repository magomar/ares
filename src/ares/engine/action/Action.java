package ares.engine.action;

import ares.engine.actors.Actor;
import ares.engine.realtime.Clock;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    public void execute(Clock clock);

    public Actor getActor();

    public ActionType getType();

    public int getStart();

    public int getFinish();

    public ActionState getState();

    public int getTimeToComplete();

    public String toString(Clock clock);

    /**
     * Checks if the planned start time is reached during the current time tick
     *
     * @param clock
     * @return
     */
    public boolean checkTimetoStart(Clock clock);

    /**
     * Checks if the estimated completion time is reached during the current time tick
     *
     * @param clock
     * @return
     */
    public boolean checkTimeToComplete(Clock clock);

    /**
     * Checks if the remaining endurance is enough to execute the action during the next time tick
     *
     * @param clock
     * @return
     */
    public boolean checkEndurance(Clock clock);

    /**
     * Changes the status of the action to {@link AresState.STARTED} and determines the actual start time, which may
     * differ from the planned start time. This method should be invoked only after checking the start time with
     * {@link checkTimeToStart})
     *
     * @param clock
     */
    public void start(Clock clock);

    /**
     * Changes the status of the action to {@link AresState.COMPLETED} and determines the actual finish time, which may
     * differ from the planned finish time. This method should be invoked only after checking the time to complete with
     * {@link checkTimeToComplete})
     *
     * @param clock
     */
    public void complete(Clock clock);

    /**
     * Checks if the acting unit has the right operational state to start this action ({@link OpState})
     *
     * @return
     */
    public boolean checkPrecondition();

    /**
     * Checks if the acting unit can be executed
     *
     * @return
     */
    public boolean checkFeasibility();


}
