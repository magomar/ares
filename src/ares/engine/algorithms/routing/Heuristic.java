package ares.engine.algorithms.routing;

import java.awt.Point;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface Heuristic {

    public int getCost(Point orig, Point dest);
}
