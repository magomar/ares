package ares.scenario;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AresCalendar {

    public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    public static final SimpleDateFormat SINGLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public final int MINUTES_PER_TICK;
    public final int MINUTES_PER_TURN;
    private final GregorianCalendar begins;
    private final GregorianCalendar ends;
    private GregorianCalendar now;
    private int turn;
    private final int finalTurn;
    private final TurnLength turnLength;

    public AresCalendar(ares.data.jaxb.Calendar calendar) {
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
    }

    public void setNow(GregorianCalendar now) {
        this.now = now;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

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

    @Override
    public String toString() {
        return FULL_DATE_FORMAT.format(now.getTime()) + " (Turn " + turn + ")   Ends: "
                + FULL_DATE_FORMAT.format(ends.getTime()) + "(Turn " + finalTurn + ")";
    }
}
