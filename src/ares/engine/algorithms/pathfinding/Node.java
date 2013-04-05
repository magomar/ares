package ares.engine.algorithms.pathfinding;

import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.util.Objects;

/**
 *
 * @author Saúl Esteban
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Node implements Comparable {

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
    private double f;

    public Node(Tile tile) {
        this.tile = tile;
    }

    public Node(Tile tile, Direction direction, Node prev, double g, double f) {
        this.tile = tile;
        this.direction = direction;
        this.prev = prev;
        this.g = g;
        this.f = f;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return direction.name() + tile;
    }
//    @Override
//    public String toString() {
//        return direction.name() + tile + "(g=" + g + ", f=" + f + ')';
//    }

    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append(" From: ");
        sb.append(direction.name());
        sb.append(" to (");
        sb.append(tile.getCoordinates().x);
        sb.append(",");
        sb.append(tile.getCoordinates().y);
        sb.append(") ");
        sb.append(" F:(");
        sb.append(f);
        sb.append(")");
        sb.append(" G:(");
        sb.append(g);
        sb.append(")");
        sb.append(" ]\n");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.tile);
        return hash;
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
    public int compareTo(Object o) {
        Node n = (Node) o;

        if (f < n.f) {
            return -1;
        }
        if (f > n.f) {
            return 1;
        }
        return 0;
    }
}
