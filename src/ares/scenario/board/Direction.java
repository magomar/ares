package ares.scenario.board;

import java.util.EnumSet;
import java.util.Set;

/**
 *
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
    public final static Set<Direction> DIRECTIONS = EnumSet.range(Direction.N, Direction.NW);
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

 

    public static int convertDirectionsToBitMask(Set<Direction> directions) {
        int mask = 0;
        for (Direction dir : directions) {
            // TODO Direction.C should not appear here !!
            if (dir != Direction.C) {
                int bit = 1 << dir.ordinal();
                mask |= bit;
            }
        }
        return mask;
    }

    public static Set<Direction> convertBitmaskToDirections(int bitmask) {
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
        boolean bitIsSet = (bitmask & flag) != 0;
        return bitIsSet;
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
}
