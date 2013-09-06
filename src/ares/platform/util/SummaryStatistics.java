package ares.platform.util;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class SummaryStatistics {
    private long n = 0;
    private double sum = 0;
    private double mean = 0;
    private double stDev;
    private double max = Double.MIN_VALUE;
    private double min = Double.MAX_VALUE;

    public SummaryStatistics(long n, double sum, double mean, double stDev, double max, double min) {
        this.n = n;
        this.sum = sum;
        this.mean = mean;
        this.stDev = stDev;
        this.max = max;
        this.min = min;
    }

    public long getN() {
        return n;
    }

    public double getSum() {
        return sum;
    }

    public double getMean() {
        return mean;
    }

    public double getStDev() {
        return stDev;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    @Override
    public String toString() {
        return String.format("%.2f", mean);
    }

    public String toStringVerbose() {
        return "{n=" + n +
                ", mean=" + mean +
                ", stDev=" + stDev +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}