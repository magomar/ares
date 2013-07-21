package ares.platform.engine.action;

import ares.platform.engine.time.Clock;
import ares.platform.scenario.forces.OpState;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractAction implements Action {

    /**
     * Unique identifier for this action. Since actions are created dynamically, the identifier can be obtained by
     * automatic generation. For that purpose, the class {@link ActionCounter} includes a static method
     * ({@link ares.platform.engine.action.ActionCounter#count()}) that return successive integer values when invoked.
     */
    protected final int id;
    /**
     * The unit to perform this action
     */
    protected final Unit unit;
    /**
     * The type of action
     *
     * @see ActionType
     */
    protected final ActionType actionType;
    /**
     * Before starting the action, this attribute holds the estimated time to start performing the action, specified in
     * time ticks passed since the beginning of the scenario. Thereafter it holds the actual starting time. If (start ==
     * AS_SOON_AS_POSSIBLE) then the action will be executed as soon as possible
     */
    protected int start;
    /**
     * Before finishing the action, this attribute holds the estimated finishing time, specified in time ticks since the
     * beginning of the scenario. Thereafter it holds the actual finishing time
     */
    protected int finish;
    /**
     * Estimated remaining time to finish the action in time ticks. If (timeToComplete == TIME_UNKNOWN) then the
     * action will be executed indefinitely, until it is aborted or terminated because of some event
     */
    protected int timeToComplete;
    /**
     * The current state of the action
     *
     * @see ActionState
     */
    protected ActionState state;
//    /**
//     * Global action space; it is used to handle action interactions among multiple actors (eg. combat)
//     */
//    protected final ActionSpace actionSpace;

//    public AbstractAction(ActionType actionType, Unit unit, ActionSpace actionSpace) {
//        this(actionType, unit, AS_SOON_AS_POSSIBLE, TIME_UNKNOWN, actionSpace);
//    }
//
//    public AbstractAction(ActionType actionType, Unit unit, int start, ActionSpace actionSpace) {
//        this(actionType, unit, start, TIME_UNKNOWN, actionSpace);
//    }

    public AbstractAction(ActionType actionType, Unit unit, int start, int duration) {
        id = ActionCounter.count();
        this.actionType = actionType;
        this.unit = unit;
        this.start = start;
        this.timeToComplete = duration;
        if (timeToComplete != TIME_UNKNOWN) {
            finish = start + timeToComplete;
        } else {
            finish = TIME_UNKNOWN;
        }
        state = ActionState.CREATED;
    }

    /**
     * Resolves if the action can be executed by checking all the necessary conditions: if the action was already
     * started then it performs two checks: {@link #checkEndurance()} and {@link #isFeasible()}, otherwise it performs
     * the two former checks plus {@link #checkStartTime()} and {@link #checkPreconditions()}.
     *
     * @return true if the action can be executed
     */
    @Override
    public final boolean canBeExecuted() {
        if (state == ActionState.CREATED) {
            return checkStartTime() && checkPreconditions() && checkEndurance() && isFeasible();
        } else {
            return checkEndurance() && isFeasible();
        }
    }

    /**
     * Checks if the action satisfies its completion criteria. {@link AbstractAction} provides a default implementation
     * that only verifies time completion. Subclasses may overwrite this method with specific implementations.
     *
     * @return whether the estimated completion time is reached during the current time tick
     */
    @Override
    public boolean isComplete() {
        return timeToComplete <= 0;
    }

    /**
     * Starts executing the action. Changes the status of the action to {@link ActionState#ONGOING} and sets the
     * {@link #start} time to the current time tick. This method should be used to start the execution of an action for
     * the first time, not thereafter.
     */
    @Override
    public final void start() {
        state = ActionState.ONGOING;
        start = Clock.INSTANCE.getTick();
        unit.setOpState(actionType.getEffectWhile());
    }

    /**
     * Completes the action. Changes the status of the action to {@link ActionState#COMPLETED}, determines the actual
     * {@link #finish} time, which may differ from the estimated finish time, and applies completion effects on the
     * operational state of the {@link #unit}
     */
    @Override
    public final void finish() {
        state = ActionState.COMPLETED;
        finish = Clock.INSTANCE.getTick();
        unit.setOpState(actionType.getEffectAfter());
    }

    /**
     * Executes the action for the current time tick. If the the action is completed then finish it.
     */
    @Override
    public final void execute(ActionSpace actionSpace) {
        timeToComplete -= Clock.INSTANCE.getMINUTES_PER_TICK();
        wear();
        applyOngoingEffects();
        if (isComplete()) {
            finish();
        }
    }

    /**
     * Aborts the action. Changes the status of the action to {@link ActionState#ABORTED}.  Sets the finish time to the current time
     */
    @Override
    public final void abort() {
        state = ActionState.ABORTED;
        wear();
//        unit.setOpState(unitType.getPrecondition());
        finish = Clock.INSTANCE.getTick();
    }

    /**
     * Apply effects of a delay on the unit's {@link Unit#opState}
     *
     * @see OpState
     */
    @Override
    public void delay() {
        unit.setOpState(actionType.getPrecondition());
    }

    /**
     * Checks whether the planned start time of the action has been reached
     *
     * @return true if start time has been reached, false otherwise
     */
    private boolean checkStartTime() {
        return start <= Clock.INSTANCE.getTick();
    }

    /**
     * Checks whether the acting unit is in the right operational state
     *
     * @return true if the operational state is right, false otherwise
     * @see OpState
     */
    @Override
    public boolean checkPreconditions() {
        // TODO try to make this method protected...
        OpState precondition = actionType.getPrecondition();
        OpState unitState = unit.getOpState();
        return precondition == null || unitState.equals(precondition) || unitState.equals(actionType.getEffectWhile());
    }

    /**
     * @return whether the unit's remaining endurance is enough to execute the action during the next time tick
     */
    private boolean checkEndurance() {
        return unit.canEndure(actionType);
    }

    /**
     * Apply wear resulting of executing the action for one time tick, which reduces the endurance of the unit
     */
    private void wear() {
        unit.changeEndurance(-actionType.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK()));
    }

    /**
     * Apply the effects of an action while it is being executed. Derived classes can overwrite this method to add
     * specific effects
     */
    protected abstract void applyOngoingEffects();

    /**
     * Checks if this action satisfies its specific requirements, which may depend on the action's type ({@link #actionType})
     *
     * @return true if the requirements are satisfied
     */
    protected abstract boolean isFeasible();

    @Override
    public void setState(ActionState state) {
        this.state = state;
    }

    @Override
    public ActionState getState() {
        return state;
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
    public int getTimeToComplete() {
        return timeToComplete;
    }

    @Override
    public ActionType getActionType() {
        return actionType;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return actionType.name() + '_' + id + '(' + state + ", " + start + ", " + timeToComplete + ')';
    }

    public String toStringVerbose() {
        return "[" + Clock.INSTANCE + "] #" + id + " > " + state + '{' + actionType + ": " + unit + '}';
    }
}
