package ares.scenario.board;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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
    private final int incI;
    private final int incJEven;
    private final int incJOdd;
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
        this.incI = incI;
        this.incJEven = incJEven;
        this.incJOdd = incJOdd;
    }

    public int getIncI() {
        return incI;
    }

    public int getIncJEven() {
        return incJEven;
    }

    public int getIncJOdd() {
        return incJOdd;
    }

    public Direction getOpposite() {
        return opposite;
    }
//
//    public static int convertDirectionsToBitMask(List<Direction> directions) {
//        int mask = 0;
//        for (Direction dir : directions) {
//            // TODO Direction.C should not appear here !!
//            if (dir != Direction.C) {
//                int bit = 1 << dir.ordinal();
//                mask |= bit;
//            }
//        }
//        return mask;
//    }

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

    public static List<Direction> convertBitMaskToDirections(int mask) {
        List<Direction> directions = new ArrayList<>();
        // TODO if possible, return an EnumSet instead of a list (I haven't been able yet)
        for (int bit = 0; bit < ALL_DIRECTIONS.length; bit++) {
            if (testBitFlag(mask, bit)) {
                directions.add(ALL_DIRECTIONS[bit]);
            }
        }
        return directions;
    }

    private static boolean testBitFlag(int mask, int bit) {
        int flag = 1 << bit;
        boolean bitIsSet = (mask & flag) != 0;
        return bitIsSet;
    }
}
