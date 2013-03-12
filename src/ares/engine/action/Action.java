package ares.engine.action;

import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    void commit();

    void execute();

    /**
     * Changes the status of the action to {@link AresState#ONGOING} . Actions being selected for execution enter this
     * state prior to start execution.
     *
     */
    void start();
    
    void abort();
    
    void complete();
    
    void setState(ActionState state);

    /**
     * Checks if the action satisfies its completion criteria.
     *
     * @return true if the action can be completed
     */
    boolean isComplete();

    /**
     * Checks the conditions required to execute the action
     *
     * @return true if the appropriate conditions are met
     */
    boolean canBeExecuted();

    Unit getUnit();

    ActionType getType();

    int getStart();

    int getFinish();

    ActionState getState();

    int getTimeToComplete();
}
