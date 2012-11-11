package ares.engine.algorithms;

import ares.scenario.board.Tile;
import java.util.*;

/**
 *
 * @author Saúl Esteban
 */
public class Path {

//    protected Double g;
//    protected Double f;
    protected LinkedList<Tile> tiles;

    /**
     * Default constructor.
     */
    public Path() {
        tiles = new LinkedList<>();
//        g = f = 0.0;
    }

    /**
     * Constructor by copy another object.
     *
     * @param p The path object to clone.
     */
    public Path(Path p) {
        this();
//        g = p.g;
//        f = p.f;
        tiles = new LinkedList<>((Collection)p);
    }

    /**
     * Compare to another object using the total cost f.
     *
     * @param o The object to compare to.
     * @see Comparable#compareTo()
     * @return
     * <code>less than 0</code> This object is smaller than
     * <code>0</code>;
     * <code>0</code> Object are the same.
     * <code>bigger than 0</code> This object is bigger than o.
     */
//    public int compareTo(Path o) {
//        return (int) (f - o.f);
//    }

    /**
     * Get the first point on the path.
     *
     * @return The first point visited by the path.
     */
    public Tile getFirst() {
        return tiles.getFirst();
    }

    /**
     * Set the first point on the path.
     */
    public void setFirst(Tile t) {
        tiles.addFirst(t);
    }
    
    public LinkedList<Tile> getTiles() {
        return tiles;
    }
}
