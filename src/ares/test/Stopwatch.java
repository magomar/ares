package ares.test;

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

    /**
     * @return
     */
    public long getTotalTime() {
        return stop - start;
    }

    /**
     * @return
     */
    public long getTimeFromLastStop() {
        return System.currentTimeMillis() - stop;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "   Time: " + Long.toString(getTotalTime()) + "ms"; // returns execution time
    }

}
