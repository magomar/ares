package ares.engine.action;

import ares.scenario.forces.Unit;
import ares.scenario.Clock;
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
     * Action comparator used to sort actions by start time
     */
    public static final Comparator<Action> ACTION_START_COMPARATOR = new ActionStartComparator();
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
     * minutes since the beginning of the scenario. Thereafter it holds the actual starting time. If (start ==
     * AS_SOON_AS_POSSIBLE) then the action will be executed as soon as possible
     *
     */
    protected int start;
    /**
     * Before finishing the action, this attribute holds the estimated finishing time, specified in minutes since the
     * beginning of the scenario. Thereafter it holds the actual finishing time
     */
    protected int finish;
    /**
     * Estimated remaining time to complete the action in minutes. If (timeToComplete == TIME_UNKNOWN) then the action
     * will be executed indefinitely, until a completion condition holds
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
        this(unit, type, AS_SOON_AS_POSSIBLE, TIME_UNKNOWN);
    }

    public AbstractAction(Unit unit, ActionType type, int start, int timeToComplete) {
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
     * Checks if the planned start time is reached during the current time tick
     *
     * @return
     */
    public final boolean checkTimetoStart() {
        return (start < Clock.INSTANCE.getCurrentTime() + ares.scenario.Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    /**
     * Checks if the acting unit has the right operational state to start this action ({@link OpState})
     *
     * @return
     */
    public boolean checkPrecondition() {
        return unit.getOpState() == type.getPrecondition();
    }

    @Override
    public boolean canBeCompleted() {
        return checkTimeToComplete();
    }

    /**
     * Checks if the estimated completion time is reached during the current time tick
     *
     * @return
     */
    public final boolean checkTimeToComplete() {
        return (timeToComplete <= Clock.INSTANCE.getMINUTES_PER_TICK());
    }

    /**
     * Checks if the remaining endurance is enough to execute the action during the next time tick
     *
     * @return
     */
    public final boolean checkEndurance() {
        int duration = Math.min(timeToComplete, Clock.INSTANCE.getMINUTES_PER_TICK());
        int requiredEndurance = type.getRequiredEndurace(duration);
        return (unit.getEndurance() >= requiredEndurance);
    }

    @Override
    public void start() {
        state = ActionState.STARTED;
        start = Math.max(start, Clock.INSTANCE.getCurrentTime() - Clock.INSTANCE.getMINUTES_PER_TICK());
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
        // TODO improve considering the remaining time to complete
        return unit.getEndurance() > type.getRequiredEndurace(Clock.INSTANCE.getMINUTES_PER_TICK());
    }

//    @Override
//    public void execute() {
//        if (checkFeasibility()) {
//            if (checkEndurance()) {
//                resume();
//                if (canBeCompleted()) {
//                    complete();
//                }
//            } else {
//                delay();
//            }
//        } else {
//            abort();
//        }
//    }
    @Override
    public void execute() {
        if (checkFeasibility()) {
            if (checkEndurance()) {
                if (checkTimeToComplete()) {
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

    protected void delay() {
        state = ActionState.DELAYED;
        int duration = Clock.INSTANCE.getMINUTES_PER_TICK();
        int wear = (int) (type.getWearRate() * duration);
        unit.changeEndurance(wear);
    }

    protected void abort() {
        state = ActionState.ABORTED;
        int duration = Clock.INSTANCE.getMINUTES_PER_TICK();
        int wear = (int) (type.getWearRate() * duration);
        unit.changeEndurance(wear);
        unit.setOpState(type.getPrecondition());
        finish = Clock.INSTANCE.getCurrentTime();
    }

    protected void resume() {
        int duration = Clock.INSTANCE.getMINUTES_PER_TICK();
        timeToComplete -= duration;
        wear(duration);
        applyOngoingEffects();
    }

    /**
     * Changes the status of the action to {@link AresState.COMPLETED} and determines the actual finish time, which may
     * differ from the planned finish time. This method should be invoked only after checking the time to complete with
     * {@link checkTimeToComplete})
     *
     */
    public void complete() {
        int duration = Math.min(timeToComplete, Clock.INSTANCE.getMINUTES_PER_TICK());
        timeToComplete = 0;
        state = ActionState.COMPLETED;
        finish = Clock.INSTANCE.getCurrentTime() - Clock.INSTANCE.getMINUTES_PER_TICK() + duration;
        unit.setOpState(type.getEffectAfter());
        wear(duration);
        applyEffects();
        // TODO if the action is completed before the end of the time tick, do something, eg wait... or ask the TacAI
    }

    public void wear(int duration) {
        int wear = (int) (type.getWearRate() * duration);
        unit.changeEndurance(wear);
    }

    /**
     * Apply the effects of an action after completion
     */
    public void applyEffects() {
        unit.setOpState(type.getEffectAfter());
    }

    /**
     * Apply the effects of an action while it is being executed
     */
    protected void applyOngoingEffects() {
        // do nothing, to be overriden by subclasses
    }

    /**
     * Checks if the acting unit can be executed
     *
     * @return
     */
    public boolean checkFeasibility() {
        // do nothing, can be overriden by subclasses
        return true;
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

    @Override
    public String toString() {
        return "[" + Clock.INSTANCE + "] #" + id + " > " + state + '{' + type + ": " + unit + '}';
    }
}
