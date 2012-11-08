package ares.engine.realtime;

import ares.engine.Engine;
import ares.scenario.AresCalendar;
import java.util.*;
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
//    public final int MINUTES_PER_DAY = 24 * 60 * 60;
    public final int TICKS_PER_TURN;
    public final int TICKS_PER_DAY;
    public final int TICKS_PER_HOUR;
    public final int MINUTES_PER_TICK;
    public final int MINUTES_PER_TURN;
//    public final int MILLISEC_PER_TICK;
//    public final int MILLISEC_PER_TURN;
//    private ScheduledExecutorService scheduledExecutorService;
    /**
     * Minutes transcurred since the beginning of the scenario
     */
    private int currentTime;
    /**
     * Delay between ticks
     */
//    private long period = 100;
    private int tick;
    private int turn;
    private boolean isRunning;
    private Engine engine;

    public Clock(AresCalendar calendar, Engine engine) {
        this.engine = engine;
        MINUTES_PER_TICK = calendar.getTurnLength().getMinutesPerTick();
        MINUTES_PER_TURN = calendar.getTurnLength().getMinutesPerTurn();
//        MILLISEC_PER_TICK = MINUTES_PER_TICK * 60000;
//        MILLISEC_PER_TURN = MINUTES_PER_TURN * 60000;
        TICKS_PER_TURN = MINUTES_PER_TURN / MINUTES_PER_TICK;
        TICKS_PER_DAY = 1440 / MINUTES_PER_TICK;
        TICKS_PER_HOUR = 60 / MINUTES_PER_TICK;
        turn = calendar.getTurn();
        tick = TICKS_PER_TURN - 1;
        now = calendar.getNow();
        currentTime = turn * MINUTES_PER_TURN - MINUTES_PER_TICK;
        finalTurn = calendar.getFinalTurn();
//        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            Logger.getLogger(Clock.class.getName()).log(Level.INFO, "*** Clock Started", AresCalendar.FULL_DATE_FORMAT.format(now.getTime()));
            Logger.getLogger(Clock.class.getName()).log(Level.INFO, "*** Current time in minutes =  {0}", currentTime);
            isRunning = true;
            tick();
            //ScheduledFuture scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, 0, period, TimeUnit.MILLISECONDS);
        }
    }

    public void tick() {
        SwingUtilities.invokeLater(new Tick(this));
    }

    public void stop() {
        if (!isRunning) {
//            try {
//                scheduledExecutorService.awaitTermination(period, TimeUnit.MILLISECONDS);
//                isRunning = false;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Clock.class.getName()).log(Level.SEVERE, null, ex);
//            }
            isRunning = false;
        }
        Logger.getLogger(Clock.class.getName()).log(Level.INFO, "*** Clock Stopped", AresCalendar.FULL_DATE_FORMAT.format(now.getTime()));
    }

//    public void setPeriod(long period) {
//        this.period = period;
//    }
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
                    Logger.getLogger(Clock.class.getName()).log(Level.INFO, "*** End of scenario ***", toString());
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
