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
     * Checks the conditions required to start executing the action
     * 
     * @return true if the appropriate conditions are met
     */
    public boolean canBeStarted();

    /**
     * Checks the conditions required to complete the executon of an action
     * 
     * @return true if the appropriate conditions are met
     */
    public boolean canBeCompleted();
    
    
    /**
     * Changes the status of the action to {@link AresState.STARTED} and determines the actual start time, which may
     * differ from the planned start time. This method should be invoked only after checking the start time with
     * {@link checkTimeToStart})
     *
     */
    public void start();



}
