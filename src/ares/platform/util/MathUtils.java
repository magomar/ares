package ares.platform.util;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MathUtils {

    public static int setBounds(int value, int lowerBound, int upperBound) {
        return Math.max(Math.min(value, upperBound), lowerBound);
    }

    public static int setUpperBound(int value, int upperBound) {
        return Math.min(value, upperBound);
    }

    public static int setLowerBound(int value, int lowerBound) {
        return Math.max(value, lowerBound);
    }
}
