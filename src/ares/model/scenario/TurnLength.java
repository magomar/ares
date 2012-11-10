package ares.model.scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum TurnLength {

    SIX_HOURS(6, 1),
    HALF_DAY(12, 2),
    FULL_DAY(24, 4),
    HALF_WEEK(84, 14),
    FULL_WEEK(168, 28);
    /**
     * Number of hours per turn
     */
    private final int hoursPerTurn;
    /**
     * Number of minutes per turn
     */
    private final int minutesPerTurn;
    /**
     * Number of minutes per tick
     */
    private final int minutesPerTick;

    private TurnLength(final int hours, final int minutesPerTick) {
        this.hoursPerTurn = hours;
        minutesPerTurn = hours * 60;
        this.minutesPerTick = minutesPerTick;
    }

    public int getHoursPerTurn() {
        return hoursPerTurn;
    }

    public int getMinutesPerTurn() {
        return minutesPerTurn;
    }

    public int getMinutesPerTick() {
        return minutesPerTick;
    }
}
