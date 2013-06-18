package ares.platform.engine.algorithms.pathfinding;

import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;

import java.util.Objects;

/**
 * @author Saúl Esteban
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Node implements Comparable<Node> {

    private Tile tile;
    private Node prev;
    private Node next;

    /**
     * Direction used to reach this node from previous node (ie. relative to this node)
     */
    private Direction direction;
    /**
     * Cost from the start to this node
     */
    private double g;
    /**
     * Estimated cost from this node to the goal
     */
    private double h;
    /**
     * Estimated cost from start to the goal (g + h)
     */
    private double f;

    public Node(Tile tile) {
        this.tile = tile;
    }

    public Node(Tile tile, Direction direction, Node prev) {
        this(tile);
        this.direction = direction;
        this.prev = prev;
    }

    public Node(Tile tile, Direction direction, Node prev, double g, double h) {
        this(tile, direction, prev);
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    public Tile getTile() {
        return tile;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Direction direction, Node prev, double g) {
        this.direction = direction;
        this.prev = prev;
        this.g = g;
        f = g + h;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public int getIndex() {
        return tile.getIndex();
    }

    public Direction getDirection() {
        return direction;
    }

    public Node getNext() {
        return next;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    @Override
    public String toString() {
        return direction.name() + tile;
    }

    public String toStringVerbose() {
        return direction.name() + tile.toString() + "(g=" + g + ", f=" + f + ')';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (!Objects.equals(this.tile, other.tile)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.tile);
        return hash;
    }


    @Override
    public int compareTo(Node o) {
        if (f < o.f) {
            return -1;
        }
        if (f > o.f) {
            return 1;
        }
        return 0;
    }
}
