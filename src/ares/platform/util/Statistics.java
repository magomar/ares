package ares.platform.util;

import java.util.List;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class Statistics {
    private Statistics() {
    }

    public static SummaryStatistics computeSummaryStatistics(List<Double> data) {
        long n = 0;
        double mean = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0.0;
        for (Double x : data) {
            n++;
            double delta = x - mean;
            mean += delta / n;
            sum += delta * (x - mean);
            max = Math.max(max, x);
            min = Math.min(min, x);
        }
        double variance = sum / n;
        double stDev = Math.sqrt(variance);
        return new SummaryStatistics(n, sum, mean, stDev, max, min);
    }

    public static SummaryStatistics computeSummaryStatistics(double[] data) {
        long n = 0;
        double mean = 0;
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double sum = 0.0;
        for (double x : data) {
            n++;
            double delta = x - mean;
            mean += delta / n;
            sum += delta * (x - mean);
            max = Math.max(max, x);
            min = Math.min(min, x);
        }
        double variance = sum / n;
        double stDev = Math.sqrt(variance);
        return new SummaryStatistics(n, sum, mean, stDev, max, min);
    }

    /**
     * @param x array of data for variable x
     * @param y array of data for variable y
     * @return the Pearson's correlation of x and y
     */
    public static double correlation(double[] x, double[] y) {
        double result = 0;
        double sum_sq_x = 0;
        double sum_sq_y = 0;
        double sum_coproduct = 0;
        double mean_x = x[0];
        double mean_y = y[0];
        for (int i = 2; i < x.length + 1; i++) {
            double sweep = Double.valueOf(i - 1) / i;
            double delta_x = x[i - 1] - mean_x;
            double delta_y = y[i - 1] - mean_y;
            sum_sq_x += delta_x * delta_x * sweep;
            sum_sq_y += delta_y * delta_y * sweep;
            sum_coproduct += delta_x * delta_y * sweep;
            mean_x += delta_x / i;
            mean_y += delta_y / i;
        }
        double pop_sd_x = (double) Math.sqrt(sum_sq_x / x.length);
        double pop_sd_y = (double) Math.sqrt(sum_sq_y / x.length);
        double cov_x_y = sum_coproduct / x.length;
        result = cov_x_y / (pop_sd_x * pop_sd_y);
        return result;
    }

    /**
     * @param population an array of data
     * @return the variance
     */
    public static double variance(double[] population) {
        long n = 0;
        double mean = 0;
        double s = 0.0;
        for (double x : population) {
            n++;
            double delta = x - mean;
            mean += delta / n;
            s += delta * (x - mean);
        }
        // if you want to calculate std deviation
        // of a sample change this to (s/(n-1))
        return (s / n);
    }

    /**
     * @param population an array of data
     * @return the standard deviation
     */
    public static double standardDeviation(double[] population) {
        return Math.sqrt(variance(population));
    }


}

