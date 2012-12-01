package ares.engine.algorithms.routing;

import java.awt.Point;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class DistanceCalculator implements Heuristic{

    public final static int DELTA = 0;
    public final static int EUCLIDEAN = 1;
    
    private static int algo;
    
    public DistanceCalculator(){
        this(EUCLIDEAN);
    }
    
    public DistanceCalculator(int algorithm){
        this.algo = algorithm;
    }
        
    public void setAlgorithm(int algorithm){
        this.algo = algorithm;
    }

    @Override
    public int getCost(Point orig, Point dest) {
        switch (algo) {
            case DELTA:
                return deltaDistance(orig, dest);
            case EUCLIDEAN:
                return euclidean(orig, dest);
            default:
                throw new AssertionError("Assertion failed: unkown algorithm");
        }
    }

    public int getCost(Point orig, Point dest, int algorithm) {
        switch(algorithm){
            case DELTA:
                return deltaDistance(orig, dest);
            case EUCLIDEAN:
                return euclidean(orig, dest);
            default:
                throw new AssertionError("Assertion failed: unkown algorithm");
        }
    }

    private int euclidean(Point orig, Point dest) {
        int dx = dest.x - orig.x;
        int dy = dest.y - orig.y;
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    private int deltaDistance(Point orig, Point dest) {
        int cost = 0;
        int dy = dest.y - orig.y, dx = dest.x - orig.x;
        if (Math.signum(dx) == Math.signum(dy)) {
            cost = Math.max(Math.abs(dx), Math.abs(dy));
        } else {
            cost = Math.abs(dx) + Math.abs(dy);
        }

        return cost;
    }
}
