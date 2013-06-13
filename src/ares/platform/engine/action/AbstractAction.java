package ares.platform.engine.action;

import ares.platform.scenario.forces.Unit;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.forces.OpState;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractAction implements Action {

    /**
     * Start time value indicating that an action shall be started as soon as possible
     */
    public static final int AS_SOON_AS_POSSIBLE = 0;
    /**
     * Value indicating that the duration or time to complete an action is unknown
     */
    public static final int TIME_UNKNOWN = Integer.MAX_VALUE;
    /**
     * The unit to perform this action
     */
    protected Unit unit;
    /**
     * The type of the action
     *
     * @See ActionType
     */
    protected ActionType type;
    /**
     * Before starting the action, this attribute holds the estimated time to start performing the action, specified in
     * time ticks passed since the beginning of the scenario. Thereafter it holds the actual starting time. If (start ==
     * AS_SOON_AS_POSSIBLE) then the action will be executed as soon as possible
     *
     */
    protected int start;
    /**
     * Before finishing the action, this attribute holds the estimated finishing time, specified in time ticks since the
     * beginning of the scenario. Thereafter it holds the actual finishing time
     */
    protected int finish;
    /**
     * Estimated remaining time to complete the action in time ticks. If (timeToComplete == TIME_UNKNOWN) then the
     * action will be executed indefinitely, until it is aborted or terminated because of some event
     */
    protected int timeToComplete;
    /**
     * The current state of the action
     *
     * @See ActionState
     */
    protected ActionState state;
    protected int id;

    public AbstractAction(Unit unit, ActionType type) {
        this(unit, AS_SOON_AS_POSSIBLE, type, TIME_UNKNOWN);
    }

    public AbstractAction(Unit unit, int start, ActionType type) {
        this(unit, start, type, TIME_UNKNOWN);
    }

    public AbstractAction(Unit unit, ActionType type, int timeToComplete) {
        this(unit, AS_SOON_AS_POSSIBLE, type, timeToComplete);
    }

    public AbstractAction(Unit unit, int start, ActionType type, int timeToComplete) {
        this.unit = unit;
        this.type = type;
        this.start = start;
        this.timeToComplete = timeToComplete;
        if (timeToComplete != TIME_UNKNOWN) {
            finish = start + timeToComplete;
        } else {
            finish = TIME_UNKNOWN;
        }
        state = ActionState.CREATED;
        id = ActionCounter.count();
    }

    /**
     * Determines if the action can be executed by checking all the necessary conditions: if the action was already
     * started then it performs two checks: {@link #checkEndurance()} and {@link #isFeasible()}, otherwise it performs
     * the two former checks plus {@link #checkStartTime()} and {@link #checkPreconditions()}.
     *
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
        unit.setOpState(type.getEffectWhile());
    }

    /**
     * Completes the action. Changes the status of the action to {@link ActionState.COMPLETED}, determines the actual
     * {@link finish} time, which may differ from the estimated finish time, and applies completion effects on the
     * operational state of the {@link #unit}
     */
    @Override
    public final void complete() {
        state = ActionState.COMPLETED;
        finish = Clock.INSTANCE.getTick();
        unit.setOpState(type.getEffectAfter());
    }

    /**
     * Commits the action for execution
     */
    @Override
    public void commit() {
        // TODO commit
    }

    /**
     * Executes the action for the current time tick. If the the action is completed then complete it.
     */
    @Override
    public final void execute() {
        timeToComplete -= 1;
        wear();
        applyOngoingEffects();
        if (isComplete()) {
            complete();
        }
    }

    /**
     * Aborts the action. Changes the status of the action to {@link ActionState#ABORTED}.
     */
    @Override
    public final void abort() {
        state = ActionState.ABORTED;
        wear();
//        unit.setOpState(type.getPrecondition());
        finish = Clock.INSTANCE.getTick();
    }

    @Override
    public void delay() {
        unit.setOpState(type.getPrecondition());
    }

    /**
     * Checks if the planned start time has been reached or surpassed.<p>
     * This way, an action would never start before the planned start time.
     *
     * @return
     */
    private boolean checkStartTime() {
        return start <= Clock.INSTANCE.getTick();
    }

    /**
     * Checks if the acting unit has the right operational state to start this action ({@link OpState})
     *
     * @return
     */
    public boolean checkPreconditions() {
        // TODO try to make this method protected...
        OpState precondition = type.getPrecondition();
        OpState unitState = unit.getOpState();
        return precondition == null || unitState.equals(precondition) || unitState.equals(type.getEffectWhile());
    }

    /**
     * Checks if the remaining endurance is enough to execute the action during the next time tick
     *
     * @return
     */
    private boolean checkEndurance() {
        return unit.canEndure(type);
    }

    private void wear() {
        unit.changeEndurance(-type.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK()));
    }

    /**
     * Apply the effects of an action while it is being executed. Derived classes can overwrite this method to add
     * specific effects
     */
    protected abstract void applyOngoingEffects();

    /**
     * Checks if the actions satisfies the requirements of this action's type.
     *
     * @return
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
    public ActionType getType() {
        return type;
    }

    @Override
    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return type.name() + '_' + id + '(' + state + ", " + start + ", " + timeToComplete + ')';
    }

    public String toStringVerbose() {
        return "[" + Clock.INSTANCE + "] #" + id + " > " + state + '{' + type + ": " + unit + '}';
    }
}
