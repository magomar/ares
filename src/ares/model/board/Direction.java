package ares.model.board;

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
    C(0, 0, 0)
    ;
    private final int incI;
    private final int incJEven;
    private final int incJOdd;
    private Direction opposite;
    public final static Set<Direction> DIRECTIONS = EnumSet.range(Direction.N, Direction.NW);

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

}
