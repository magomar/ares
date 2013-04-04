package ares.engine.algorithms.routing;

import ares.scenario.board.Direction;
import java.awt.Point;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class DistanceCalculator implements Heuristic {

    public final static int DELTA = 0;
    public final static int DELTABITWISE = 1;
    public final static int EUCLIDEAN = 2;
    public final static int HEINIAN = 3;
    private static int algo;

    public DistanceCalculator() {
        this(HEINIAN);
    }

    public DistanceCalculator(int algorithm) {
        algo = algorithm;
    }

    public void setAlgorithm(int algorithm) {
        algo = algorithm;
    }

    @Override
    public int getCost(Point orig, Point dest) {
        switch (algo) {
            case HEINIAN:
                return heinian(orig, dest);
            case DELTA:
                return deltaDistance(orig, dest);
            case DELTABITWISE:
                return deltaBitwiseDistance(orig, dest);
            case EUCLIDEAN:
                return euclidean(orig, dest);
            default:
                throw new AssertionError("Assertion failed: unkown algorithm");
        }
    }

    public static int getCost(Point orig, Point dest, int algorithm) {
        switch (algorithm) {
            case HEINIAN:
                return heinian(orig, dest);
            case DELTA:
                return deltaDistance(orig, dest);
            case DELTABITWISE:
                return deltaBitwiseDistance(orig, dest);
            case EUCLIDEAN:
                return euclidean(orig, dest);
            default:
                throw new AssertionError("Assertion failed: unkown algorithm");
        }
    }

    private static int euclidean(Point orig, Point dest) {
        int dx = dest.x - orig.x;
        int dy = dest.y - orig.y;
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    private static int deltaDistance(Point orig, Point dest) {
        int cost;
        int dy = dest.y - orig.y, dx = dest.x - orig.x;
        if (Math.signum(dx) == Math.signum(dy)) {
            cost = Math.max(Math.abs(dx), Math.abs(dy));
        } else {
            cost = Math.abs(dx) + Math.abs(dy);
        }
        return cost;
    }

    private static int deltaBitwiseDistance(Point from, Point to) {
        // adapted from http://www-cs-students.stanford.edu/~amitp/Articles/HexLOS.html
        int x1 = from.x;
        int y1 = from.y;
        int x2 = to.x;
        int y2 = to.y;
        int ax = y1 - Ceil2(x1);
        int ay = y1 + Floor2(x1);
        int bx = y2 - Ceil2(x2);
        int by = y2 + Floor2(x2);
        int dx = bx - ax;
        int dy = by - ay;
        return Math.abs(dx) + Math.abs(dy);
    }

    private static int Floor2(int val) {
        return (val >> 1);
    }

    private static int Ceil2(int val) {
        return ((val + 1) >> 1);
    }

    private static int heinian(Point orig, Point dest) {
        int cost = 0;
        // If points are on the same column
        if (orig.x == dest.x) {
            return Math.abs(dest.y - orig.y);
        }
        // same row
        if (orig.y == dest.y) {
            return Math.abs(dest.x - orig.x);
        }

        boolean oddColumn = ((orig.x & 1) == 1);

        // Get the direction
        Direction dir;
        int sy = (int) Math.signum(dest.y - orig.y);


        if (Math.signum(dest.x - orig.x) > 0) {
            //destination is on the right side of origin
            if (sy > 0) {
                //dest is below the orig
                dir = Direction.SE;

            } else {
                //dest is above the orig
                dir = Direction.NE;
            }
        } else {
            //destination is on the left side of origin
            if (sy > 0) {
                dir = Direction.SW;
            } else {
                dir = Direction.NW;
            }
        }

        Point current = new Point(orig);
        while (true) {
            cost++;
            current.x += dir.getIncColumn();
            current.y += (oddColumn) ? dir.getIncRowOdd() : dir.getIncRowEven();
            oddColumn = !oddColumn;
            if (current.x == dest.x) {
                return cost + Math.abs(dest.y - current.y);
            }
            if (current.y == dest.y) {
                return cost + Math.abs(dest.x - current.x);
            }
        }
    }
}
