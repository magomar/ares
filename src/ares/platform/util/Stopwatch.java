package ares.platform.util;

/**
 * @author Mario Gomez
 */
public class Stopwatch {
    private long start;
    private long stop;

    /**
     *
     */
    public void start() {
        start = System.currentTimeMillis(); // start timing
        stop = start;
    }

    public void stop() {
        stop = System.currentTimeMillis();
    }


    public long getTotalTime() {
        return stop - start;
    }

    public long getTimeFromLastStop() {
        return System.currentTimeMillis() - stop;
    }

    @Override
    public String toString() {
        return "   Time: " + Long.toString(getTotalTime()) + "ms"; // returns execution time
    }

}
