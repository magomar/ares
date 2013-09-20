package ares.platform.scenario.board;

import ares.platform.util.MathUtils;

import java.awt.*;
import java.util.EnumSet;
import java.util.Set;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum Direction {

    N(0, -1, -1),
    NE(1, 0, -1),
    SE(1, 1, 0),
    S(0, 1, 1),
    SW(-1, 1, 0),
    NW(-1, 0, -1),
    C(0, 0, 0);
    private final int incColumn;
    private final int incRowEven;
    private final int incRowOdd;
    private Direction opposite;
    /**
     * All real directions, that is all directions except {@link #C}
     */
    public final static Set<Direction> DIRECTIONS = EnumSet.range(Direction.N, Direction.NW);
    /**
     * All directions, which includes the 6 real directions plus {@link #C}
     */
    private final static Direction[] ALL_DIRECTIONS = Direction.values();

    static {
        N.opposite = S;
        NE.opposite = SW;
        SE.opposite = NW;
        S.opposite = N;
        SW.opposite = NE;
        NW.opposite = SE;
        C.opposite = C;
    }

    private Direction(final int incI, final int incJEven, final int incJOdd) {
        this.incColumn = incI;
        this.incRowEven = incJEven;
        this.incRowOdd = incJOdd;
    }

    /**
     * Obtains a bitmask for the combination of {@code directions} passed as parameter. The resulting bitmasks are
     * easily transformed into indexes to locate a terrain image in the terrain image file. Each direction is encoded as
     * a simple bitflag (the ordinal of Direction enum)
     * <b>Examples:
     * <b>"N" -> 000001
     * <b>"N NE" -> 000011
     * <b>"S SE" -> 001100
     *
     * @param directions
     * @return
     */
    public static int getBitmask(Set<Direction> directions) {
        int mask = 0;
        for (Direction dir : directions) {
            int bit = 1 << dir.ordinal();
            mask |= bit;
        }
        return mask;
    }

    public static Set<Direction> getDirections(int bitmask) {
        Set<Direction> directions = EnumSet.noneOf(Direction.class);
        for (int bit = 0; bit < ALL_DIRECTIONS.length; bit++) {
            if (testBitFlag(bitmask, bit)) {
                directions.add(ALL_DIRECTIONS[bit]);
            }
        }
        return directions;
    }

    private static boolean testBitFlag(int bitmask, int bit) {
        int flag = 1 << bit;
        return (bitmask & flag) != 0;
    }

    public int getIncColumn() {
        return incColumn;
    }

    public int getIncRowEven() {
        return incRowEven;
    }

    public int getIncRowOdd() {
        return incRowOdd;
    }

    public Direction getOpposite() {
        return opposite;
    }

    public Point getNeighborCoordinates(Point coordinates) {
        int column = coordinates.x + getIncColumn();
        int row = coordinates.y + (MathUtils.isEven(coordinates.x) ? getIncRowEven() : getIncRowOdd());
        return new Point(column,row);
    }
    /**
     * Get the direction between two neighbor tiles
     *
     * @param from    coordinates of a tile in the map
     * @param to    coordinates of another tile which is neighbor of the first one
     * @return the direction between the {@code from} and {@code to} tiles, relative to the {@code from}  tile
     */
    public static Direction getDirectionBetween(Point from, Point to) {
        int incX = to.x - from.x;
        int incY = to.y - from.y;
        if (MathUtils.isEven(from.x)) {
            for (Direction dir : Direction.values()) {
                if (dir.getIncColumn() == incX && dir.getIncRowEven() == incY) {
                    return dir;
                }
            }
        } else {
            for (Direction dir : Direction.values()) {
                if (dir.getIncColumn() == incX && dir.getIncRowOdd() == incY) {
                    return dir;
                }
            }
        }
        return null;
    }
}
