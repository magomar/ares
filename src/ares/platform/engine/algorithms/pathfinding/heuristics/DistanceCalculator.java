package ares.platform.engine.algorithms.pathfinding.heuristics;

import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;

import java.awt.*;

/**
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum DistanceCalculator {

    DELTA {
        @Override
        public int getCost(Tile origin, Tile destination) {
            return deltaDistance(origin.getCoordinates(), origin.getCoordinates());
        }
    },
    DELTABITWISE {
        @Override
        public int getCost(Tile origin, Tile destination) {
            return deltaBitwiseDistance(origin.getCoordinates(), origin.getCoordinates());
        }
    },
    EUCLIDEAN {
        @Override
        public int getCost(Tile origin, Tile destination) {
            return euclideanDistance(origin.getCoordinates(), origin.getCoordinates());
        }
    },
    HEINIAN {
        @Override
        public int getCost(Tile origin, Tile destination) {
            return heinianDistance(origin.getCoordinates(), origin.getCoordinates());
        }
    };

    /**
     * @param from location of the origin
     * @param to   location of the destination
     * @return the distance in tiles
     */
    private static int euclideanDistance(Point from, Point to) {
        int dx = to.x - from.x;
        int dy = to.y - from.y;
        return (int) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * @param from location of the origin
     * @param to   location of the destination
     * @return the distance in tiles
     */
    private static int deltaDistance(Point from, Point to) {
        int cost;
        int dy = to.y - from.y, dx = to.x - from.x;
        if (Math.signum(dx) == Math.signum(dy)) {
            cost = Math.max(Math.abs(dx), Math.abs(dy));
        } else {
            cost = Math.abs(dx) + Math.abs(dy);
        }
        return cost;
    }

    /**
     * Adapted from <a href="http://www-cs-students.stanford.edu/~amitp/Articles/HexLOS.html">Amit's article</a>
     *
     * @param from location of the origin
     * @param to   location of the destination
     * @return the distance in tiles
     */
    private static int deltaBitwiseDistance(Point from, Point to) {
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

    /**
     * This is an exact method for computing minimum distance between two hexes in an hexagonal map.
     * This method is slower than other methods due because it requires to iterate through some hexes
     *
     * @param from location of the origin
     * @param to   location of the destination
     * @return the distance in tiles
     */
    private static int heinianDistance(Point from, Point to) {
        int cost = 0;
        // If points are on the same column
        if (from.x == to.x) {
            return Math.abs(to.y - from.y);
        }
        // same row
        if (from.y == to.y) {
            return Math.abs(to.x - from.x);
        }

        boolean oddColumn = ((from.x & 1) == 1);

        // Get the direction
        Direction dir;
        int sy = (int) Math.signum(to.y - from.y);


        if (Math.signum(to.x - from.x) > 0) {
            //destination is on the right side of origin
            if (sy > 0) {
                //to is below the from
                dir = Direction.SE;

            } else {
                //to is above the from
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

        Point current = new Point(from);
        while (true) {
            cost++;
            current.x += dir.getIncColumn();
            current.y += (oddColumn) ? dir.getIncRowOdd() : dir.getIncRowEven();
            oddColumn = !oddColumn;
            if (current.x == to.x) {
                return cost + Math.abs(to.y - current.y);
            }
            if (current.y == to.y) {
                return cost + Math.abs(to.x - current.x);
            }
        }
    }

    abstract int getCost(Tile origin, Tile destination);
}
