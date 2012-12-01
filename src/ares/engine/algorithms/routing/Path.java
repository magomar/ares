package ares.engine.algorithms.routing;

import ares.scenario.board.Tile;
import java.util.*;

/**
 *
 * @author Saúl Esteban
 */
public class Path {

    protected LinkedList<Tile> tiles;

    /**
     * Default constructor.
     */
    public Path() {
        tiles = new LinkedList<>();
    }

    /**
     * Constructor by copy another object.
     *
     * @param p The path object to clone.
     */
    
    public Path(Path p) {
        this();
        tiles = new LinkedList<>(p.getTiles());
    }

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

    @Override
    public String toString() {
        return "Path{" + tiles + '}';
    }
    
    
}
