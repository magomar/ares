package ares.platform.util;

/**
 * @author Mario Gomez
 */
public class SingleThreadStopwatch {
    private long start;
    private long stop;

    /**
     *
     */
    public void start() {
        start = Timing.getCpuTime(); // start timing
        stop = start;
    }

    public void stop() {
        stop = Timing.getCpuTime();
    }


    public long getTotalTime() {
        return stop - start;
    }

    @Override
    public String toString() {
        return "   Time: " + Long.toString(getTotalTime()) + "ms"; // returns execution time
    }

}
