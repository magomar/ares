package ares.engine.action;

import ares.engine.actors.Actor;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    public void execute();

    public Actor getActor();

    public ActionType getType();

    public int getStart();

    public int getFinish();

    public ActionState getState();

    public int getTimeToComplete();

    /**
     * Checks if the planned start time is reached during the current time tick
     *
     * @return
     */
    public boolean checkTimetoStart();

    /**
     * Checks if the estimated completion time is reached during the current time tick
     *
     * @return
     */
    public boolean checkTimeToComplete();

    /**
     * Checks if the remaining endurance is enough to execute the action during the next time tick
     *
     * @return
     */
    public boolean checkEndurance();

    /**
     * Changes the status of the action to {@link AresState.STARTED} and determines the actual start time, which may
     * differ from the planned start time. This method should be invoked only after checking the start time with
     * {@link checkTimeToStart})
     *
     */
    public void start();

    /**
     * Changes the status of the action to {@link AresState.COMPLETED} and determines the actual finish time, which may
     * differ from the planned finish time. This method should be invoked only after checking the time to complete with
     * {@link checkTimeToComplete})
     *
     */
    public void complete();

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
