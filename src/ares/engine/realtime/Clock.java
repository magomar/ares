package ares.engine.realtime;

import ares.scenario.AresCalendar;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Clock {

    private final GregorianCalendar now;
    private final int finalTurn;
    public final int TICKS_PER_TURN;
    public final int TICKS_PER_DAY;
    public final int TICKS_PER_HOUR;
    public final int MINUTES_PER_TICK;
    public final int MINUTES_PER_TURN;
    /**
     * Elapsed minutes since the beginning of the scenario
     */
    private int currentTime;
    private int tick;
    private int turn;
    private RealTimeEngine engine;
    private static final Logger LOG = Logger.getLogger(Clock.class.getName());

    public Clock(AresCalendar calendar, RealTimeEngine engine) {
        this.engine = engine;
        MINUTES_PER_TICK = calendar.getTurnLength().getMinutesPerTick();
        MINUTES_PER_TURN = calendar.getTurnLength().getMinutesPerTurn();
        TICKS_PER_TURN = MINUTES_PER_TURN / MINUTES_PER_TICK;
        TICKS_PER_DAY = 1440 / MINUTES_PER_TICK;
        TICKS_PER_HOUR = 60 / MINUTES_PER_TICK;
        turn = calendar.getTurn();
        tick = TICKS_PER_TURN - 1;
        now = calendar.getNow();
        currentTime = turn * MINUTES_PER_TURN - MINUTES_PER_TICK;
        finalTurn = calendar.getFinalTurn();
    }

    public void tick() {
        SwingUtilities.invokeLater(new Tick(this));
    }

    public GregorianCalendar getNow() {
        return now;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getTurn() {
        return turn;
    }

    @Override
    public String toString() {
        return AresCalendar.FULL_DATE_FORMAT.format(now.getTime());
    }

    private class Tick implements Runnable {

        private Clock clock;

        public Tick(Clock clock) {
            this.clock = clock;
        }

        @Override
        public void run() {
            now.add(GregorianCalendar.MINUTE, MINUTES_PER_TICK);
            currentTime += MINUTES_PER_TICK;
            tick++;
            Set<ClockEventType> eventTypes = EnumSet.of(ClockEventType.TICK);
            // Check new turn condition
            if (tick == TICKS_PER_TURN) {
                tick = 0;
                turn++;
                eventTypes.add(ClockEventType.TURN);
                // Check end of scenario
                if (turn > finalTurn) {
                    eventTypes.add(ClockEventType.FINISHED);
                }
            }
            // Check new day condition
            if (now.get(GregorianCalendar.HOUR_OF_DAY) == 6 && now.get(GregorianCalendar.MINUTE) == 0) {
                eventTypes.add(ClockEventType.DAY);
                Logger.getLogger(Clock.class.getName()).log(Level.INFO, "New day ! Turn = {0}, Time = {1}", new Object[]{turn, toString()});
            }
            engine.update(new ClockEvent(clock, eventTypes));
        }
    }
}
