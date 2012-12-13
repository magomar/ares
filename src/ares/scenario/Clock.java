package ares.scenario;

import ares.engine.realtime.ClockEvent;
import ares.engine.realtime.ClockEventType;
import ares.engine.realtime.RealTimeEngine;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.swing.SwingUtilities;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Clock {

    public static final Clock INSTANCE = new Clock();
    public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static final SimpleDateFormat SINGLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    private int MINUTES_PER_TICK;
    private int MINUTES_PER_TURN;
    private int TICKS_PER_TURN;
//    private int TICKS_PER_DAY;
//    private int TICKS_PER_HOUR;
    private GregorianCalendar begins;
    private GregorianCalendar ends;
    private GregorianCalendar now;
    private int turn;
    private int finalTurn;
    private TurnLength turnLength;
    /**
     * Elapsed minutes since the beginning of the scenario
     */
    private int currentTime;
    private int tick;
    private RealTimeEngine engine;

    private Clock() {
    }

    public void initialize(ares.data.jaxb.Calendar calendar) {
        turnLength = TurnLength.valueOf(calendar.getTurnLength().name());
        MINUTES_PER_TICK = turnLength.getMinutesPerTick();
        MINUTES_PER_TURN = turnLength.getMinutesPerTurn();
        GregorianCalendar gcal = calendar.getStartDate().toGregorianCalendar();
        gcal.set(GregorianCalendar.HOUR_OF_DAY, 6);
        gcal.set(GregorianCalendar.MINUTE, 0);
        begins = (GregorianCalendar) gcal.clone();
        turn = calendar.getCurrentTurn();
        gcal.add(GregorianCalendar.MINUTE, turn * turnLength.getMinutesPerTurn() - turnLength.getMinutesPerTick());
        now = (GregorianCalendar) gcal.clone();
        finalTurn = calendar.getFinalTurn();
        ends = (GregorianCalendar) begins.clone();
        ends.add(GregorianCalendar.MINUTE, finalTurn * turnLength.getMinutesPerTurn());
        TICKS_PER_TURN = MINUTES_PER_TURN / MINUTES_PER_TICK;
//        TICKS_PER_DAY = 1440 / MINUTES_PER_TICK;
//        TICKS_PER_HOUR = 60 / MINUTES_PER_TICK;
        tick = TICKS_PER_TURN - 1;
        currentTime = turn * MINUTES_PER_TURN - MINUTES_PER_TICK;
    }

    public void tick() {
        SwingUtilities.invokeLater(new Tick(this));
    }

    public void setEngine(RealTimeEngine engine) {
        this.engine = engine;
    }

    public void setNow(GregorianCalendar now) {
        this.now = now;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getMINUTES_PER_TICK() {
        return MINUTES_PER_TICK;
    }

    public int getMINUTES_PER_TURN() {
        return MINUTES_PER_TURN;
    }

    public int getTICKS_PER_TURN() {
        return TICKS_PER_TURN;
    }
//
//    public int getTICKS_PER_DAY() {
//        return TICKS_PER_DAY;
//    }
//
//    public int getTICKS_PER_HOUR() {
//        return TICKS_PER_HOUR;
//    }

    public GregorianCalendar getBegins() {
        return begins;
    }

    public GregorianCalendar getEnds() {
        return ends;
    }

    public GregorianCalendar getNow() {
        return now;
    }

    public int getTurn() {
        return turn;
    }

    public int getFinalTurn() {
        return finalTurn;
    }

    public TurnLength getTurnLength() {
        return turnLength;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getTick() {
        return tick;
    }

    public RealTimeEngine getEngine() {
        return engine;
    }

    public String toStringVerbose() {
        return FULL_DATE_FORMAT.format(now.getTime()) + " (Turn " + turn + ")   Ends: "
                + FULL_DATE_FORMAT.format(ends.getTime()) + "(Turn " + finalTurn + ")";
    }

    @Override
    public String toString() {
        return FULL_DATE_FORMAT.format(now.getTime());
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
            if (getNow().get(GregorianCalendar.HOUR_OF_DAY) == 6 && getNow().get(GregorianCalendar.MINUTE) == 0) {
                eventTypes.add(ClockEventType.DAY);
//                Logger.getLogger(Clock.class.getName()).log(Level.INFO, "New day ! Turn = {0}, Time = {1}", new Object[]{turn, toString()});
            }
            getEngine().update(new ClockEvent(eventTypes));
        }
    }
}
