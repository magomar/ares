package ares.engine.algorithms.routing;

import java.util.*;

/**
 *
 * @author Saúl Esteban
 */
public class Path {

    private Node last;
    private LinkedList<Node> nodes;

    /**
     * Default constructor.
     */
    public Path() {
        nodes = new LinkedList<>();
    }

    /**
     * Constructor by copy another object.
     *
     * @param p The path object to clone.
     */
    public Path(Path p) {
        this();
        nodes = new LinkedList<>(p.getNodes());
    }

    /**
     * Sets n as the last node in the path
     * 
     * @param n 
     */
    public Path(Node n) {
        last = n;
    }

    /**
     * Get the first point on the path.
     *
     * @return The first point visited by the path.
     */
    public Node getFirst() {
        return nodes.getFirst();
    }

    /**
     * Set the first point on the path.
     */
    public void setFirst(Node t) {
        nodes.addFirst(t);
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    /**
     * 
     * @return true if path could be generated,
     *          false if already generated
     */
    private boolean generatePath() {
        if (nodes.isEmpty()) {
            Node current = last;
            for (; current.getParent() != null; current = current.getParent()) {
                this.setFirst(current);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Path{" + nodes + '}';
    }

    public boolean isEmpty() {
        return nodes.isEmpty();
    }
}
