package ares.engine.action;

import ares.scenario.forces.Unit;
import ares.scenario.Clock;
import ares.scenario.forces.OpState;
import java.util.Comparator;

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
     * Action comparator used to sort actions by finish time
     */
    public static final Comparator<Action> ACTION_FINISH_COMPARATOR = new ActionFinishComparator();
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
        finish = TIME_UNKNOWN;
        state = ActionState.CREATED;
        id = ActionCounter.count();
    }

    @Override
    public boolean canBeStarted() {
        return checkTimetoStart() && checkPrecondition();
    }

    /**
     * Checks if the planned start time has been reached or surpassed.<p>
     * This way, an action would never start before the planned start time.
     *
     * @return
     */
    protected final boolean checkTimetoStart() {
//        return (start < Clock.INSTANCE.getCurrentTime() + Clock.INSTANCE.getMINUTES_PER_TICK());
        return start <= Clock.INSTANCE.getTick();
    }

    /**
     * Checks if the acting unit has the right operational state to start this action ({@link OpState})
     *
     * @return
     */
    public boolean checkPrecondition() {
        // TODO try to make this method protected...
        return unit.getOpState() == type.getPrecondition();
    }

    /**
     * Checks if the action can be completed during the next time tick
     *
     * @return whether the estimated completion time is reached during the current time tick
     */
    protected boolean canBeCompleted() {
//        return (timeToComplete <= Clock.INSTANCE.getMINUTES_PER_TICK());
        return (timeToComplete <= 1);
    }

    /**
     * Checks if the remaining endurance is enough to execute the action during the next time tick
     *
     * @return
     */
    protected final boolean checkEndurance() {
//        int duration = Math.min(timeToComplete, Clock.INSTANCE.getMINUTES_PER_TICK());
//        int requiredEndurance = type.getRequiredEndurace(duration);
        return (unit.getEndurance() >= type.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK()));
    }

    @Override
    public void start() {
        state = ActionState.ONGOING;
//        start = Math.max(start, Clock.INSTANCE.getCurrentTime() - Clock.INSTANCE.getMINUTES_PER_TICK());
        start = Clock.INSTANCE.getTick();
        unit.setOpState(type.getEffectWhile());
    }

    /**
     * Returns true if the unit has enough endurance to perform the action. The answer depends of the current endurance
     * of the unit as well as the type of action.
     *
     * @param actionType
     * @return
     */
    public boolean canExecute() {
        return unit.getEndurance() >= type.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    @Override
    public void execute() {
        if (checkFeasibility()) {
            if (checkEndurance()) {
                if (canBeCompleted()) {
                    complete();
                } else {
                    resume();
                }
            } else {
                delay();
            }
        } else {
            abort();
        }
    }

    @Override
    public void commit() {
        // TODO
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Changes the status of the action to {@link ActionState#DELAYED}. The action is delayed for the entire time tick,
     * the unit behaves as if it was executing a {@link WaitAction}, with the corresponding wear.
     */
    private void delay() {
        state = ActionState.DELAYED;
        wear(ActionType.WAIT.getWearRate());
    }

    /**
     * Changes the status of the action to {@link ActionState#ABORTED}. The action is aborted, this time tick the unit
     * behaves as if it was executing a {@link WaitAction}, with the corresponding wear.
     */
    private void abort() {
        state = ActionState.ABORTED;
        wear();
        unit.setOpState(type.getPrecondition());
        finish = Clock.INSTANCE.getTick();
    }

    /**
     * The action is executed for the entire time tick.
     */
    private void resume() {
        timeToComplete -= 1;
        applyOngoingEffects();
        wear();
    }

    /**
     * Changes the status of the action to {@link ActionState.COMPLETED} and determines the actual finish time, which
     * may differ from the planned finish time. This method should be invoked only after checking the time to complete
     * with {@link #checkTimeToComplete})
     */
    private void complete() {
        timeToComplete = 0;
        applyOngoingEffects();
        state = ActionState.COMPLETED;
        finish = Clock.INSTANCE.getTick();
        wear();
        applyEffects();
    }

    private void wear() {
        unit.changeEndurance(type.getWearRate() * Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    private void wear(int rate) {
        unit.changeEndurance(rate * Clock.INSTANCE.getMINUTES_PER_TICK());
    }

//    protected void wear(int duration) {
//        unit.changeEndurance(type.getWearRate() * duration);
//    }

//    protected void wear(ActionType type, int duration) {
//        unit.changeEndurance(type.getWearRate() * duration);
//    }
//    protected void wear(int rate, int duration) {
//        unit.changeEndurance(rate * duration);
//    }
    /**
     * Apply the effects of this action after completion: set the proper
     * <code>OperationalState</code> for the unit executing this action
     */
    private void applyEffects() {
        unit.setOpState(type.getEffectAfter());
    }

    /**
     * Apply the effects of an action while it is being executed.
     */
    protected abstract void applyOngoingEffects();

    /**
     * Checks if the actions satisfies the requirements of this action's type.
     *
     * @return
     */
    protected abstract boolean checkFeasibility();

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

    private static class ActionFinishComparator implements Comparator<Action> {

        @Override
        public int compare(Action o1, Action o2) {
            return o1.getFinish() - o2.getFinish();
        }
    }

    @Override
    public String toString() {
        return "[" + Clock.INSTANCE + "] #" + id + " > " + state + '{' + type + ": " + unit + '}';
    }
}
