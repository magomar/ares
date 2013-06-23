package ares.platform.engine.action;

import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    /**
     * Commits the action for execution
     */
    void commit();

    /**
     * Executes the action for the current time tick.
     */
    void execute();

    /**
     * Starts executing the action. Actions to be executed have to invoke this method before executing for the first
     * time.
     */
    void start();

    /**
     * Aborts the action. Once aborted, an action is completely abandoned
     */
    void abort();

    /**
     * Completes the action. Actions have to execute this method after completion.
     */
    void complete();

    /**
     * Delays the actions. Actions already started that can not be executed have to execute this method.
     */
    void delay();

    /**
     * Changes the state of the action to the {@code state} passed as parameter.
     *
     * @param state    state of the action
     */
    void setState(ActionState state);

    /**
     * Checks if the action satisfies its completion criteria.
     *
     * @return true if the action can be completed
     */
    boolean isComplete();

    /**
     * Determines if the action can be executed by checking all the necessary conditions
     *
     * @return true if the action can be executed
     */
    boolean canBeExecuted();

    /**
     * Get the time remaining to complete the action
     * @return  the remaining time
     */
    int getTimeToComplete();
    /**
     * Checks whether the acting unit is in the right operational state
     * @return true if the operational state is right, false otherwise
     * @see ares.platform.scenario.forces.OpState
     */
    boolean checkPreconditions();

    Unit getUnit();

    ActionType getType();

    int getStart();

    int getFinish();

    ActionState getState();
}
