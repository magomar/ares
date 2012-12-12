package ares.engine.algorithms.routing;

import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.util.Objects;

/**
 *
 * @author Saúl Esteban
 */
public class Node {

    private Tile tile;
    private Node prev;
    private Node next;
    /**
     * Direction from the previous node to reach this node
     */
    private Direction from;
    /**
     * Cost from the start to this node
     */
    private double g;
    /**
     * Estimated cost from this node to the goal
     */
    private double f;

    public Node(Tile t) {
        tile = t;
    }

    public Node(Tile t, Node p) {
        tile = t;
        prev = p;
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

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
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
        return from.name() + tile;
    }

    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append(" From: ");
        sb.append(from.name());
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
    public boolean equals(Object n) {
        if (n instanceof Node) {
            return this.tile.getCoordinates() == ((Node) n).getTile().getCoordinates();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 53 * hash + Objects.hashCode(this.prev);
        return hash;
    }
}
