package ares.platform.engine.action;

import ares.platform.scenario.forces.OpState;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum ActionType {

    REST(-3, 0, null, null, null),
    WAIT(1, 0, null, null, null),
    DEPLOY(1.5, 0, OpState.MOBILE, OpState.DEPLOYING, OpState.DEPLOYED),
    DIG_IN(1.5, 0, OpState.DEPLOYED, OpState.DEPLOYED, OpState.DEPLOYED),
    EMBARK(1.5, 0, OpState.MOBILE, OpState.EMBARKING, OpState.EMBARKED),
    DISEMBARK(1.5, 0, OpState.EMBARKED, OpState.EMBARKING, OpState.MOBILE),
    TRAVEL(1, 1, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    TACTICAL_MARCH(1.5, 1, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    APPROACH_MARCH(2, 0.8, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    STEALTH_MARCH(2, 0.4, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    DISENGAGE(3, 0.4, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    FORCED_MARCH(3, 1.2, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    RECON(2, 0.6, OpState.MOBILE, OpState.MOVING, OpState.MOBILE),
    ASSAULT(3, 0.2, OpState.MOBILE, OpState.ASSAULTING, OpState.DEPLOYED),
    ATTACK_BY_FIRE(2, 0, OpState.DEPLOYED, OpState.ATTACKING, OpState.DEPLOYED),
    SUPPORT_BY_FIRE(2, 0, OpState.DEPLOYED, OpState.ATTACKING, OpState.DEPLOYED),
    BOMBARD(2, 0, OpState.DEPLOYED, OpState.ATTACKING, OpState.DEPLOYED),
    BUILD(2, 0, OpState.DEPLOYED, OpState.DEPLOYED, OpState.DEPLOYED),
    DESTROY(2, 0, OpState.DEPLOYED, OpState.DEPLOYED, OpState.DEPLOYED),
    REPAIR(1.5, 0, OpState.DEPLOYED, OpState.DEPLOYED, OpState.DEPLOYED),
    ASSEMBLE(1.5, 0, OpState.DEPLOYED, OpState.ASSEMBLING, OpState.MOBILE);
    /**
     * Measures the intensity of an action, the speed or rate at which the endurance of a unit is consumed when carrying
     * this type of action. It is specified in terms of endurance, that is, amount of seconds of low intensity activity
     * per minute The intensity of low intensity actions is 60, since 1 minute = 60 seconds. A intensity of 120 means
     * the action induces twice the fatigue induced by a low intensity action, and so on.
     */
    private final int wearRate;
    /**
     * Speed modifier expressed as a factor to multiply the current unit speed by
     */
    private final double speedModifier;
    private final OpState precondition;
    private final OpState effectWhile;
    private final OpState effectAfter;

    private ActionType(final double wearRate, final double speedModifier, final OpState precondition,
            final OpState effectWhile, final OpState effectAfter) {
        this.wearRate = (int) (wearRate * 60);
        this.speedModifier = speedModifier;
        this.precondition = precondition;
        this.effectWhile = effectWhile;
        this.effectAfter = effectAfter;
    }

    public int getWearRate() {
        return wearRate;
    }

    public double getSpeedModifier() {
        return speedModifier;
    }

    public OpState getEffectAfter() {
        return effectAfter;
    }

    public OpState getEffectWhile() {
        return effectWhile;
    }

    public OpState getPrecondition() {
        return precondition;
    }

    /**
     *
     * @param duration in minutes
     * @return the endurance required to execute this action type for the given {@link duration}
     */
    public int getRequiredEndurace(int duration) {
        return wearRate * duration;
    }
}
