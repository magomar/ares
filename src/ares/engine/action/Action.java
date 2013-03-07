package ares.engine.action;

import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface Action {

    void commit();

    void execute();

    Unit getUnit();

    ActionType getType();

    int getStart();

    int getFinish();

    ActionState getState();

    int getTimeToComplete();

    /**
     * Checks the conditions required to start executing the action
     *
     * @return true if the appropriate conditions are met
     */
    boolean canBeStarted();

//    /**
//     * Checks the conditions required to complete the executon of an action
//     *
//     * @return true if the appropriate conditions are met
//     */
//    boolean canBeCompleted();


    /**
     * Changes the status of the action to {@link AresState#SCHEDULED} . Actions being selected for execution enter this
     * state prior to start execution.
     *
     */
    void start();
}
