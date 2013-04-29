package ares.engine.algorithms.pathfinding;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Path {

    private Node last;
    private Node first;
    private int size;

    /**
     * Creates a path connecting the {@code first} to the {@code last}  {@link Node}}.
     *
     * @param first
     * @param last
     */
    public Path(Node first, Node last) {
        this.last = last;
        size = 1;
        Node current = last;
        Node prev = current.getPrev();
        while (prev != null) {
            prev.setNext(current);
            current = prev;
            prev = current.getPrev();
            size++;
            if (current.equals(first)) {
                this.first = current;
                return;
            }
        }
        throw new IllegalArgumentException("First node not reachable from last node");

    }

    public Node getFirst() {
        return first;
    }

    public Node getLast() {
        return last;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Path{");
        Node l;
        for (l = last; l.getPrev() != null; l = l.getPrev()) {
            sb.append(l.getTile().toString());
        }
        sb.append(l.getTile().toString());
        sb.append("}");
        sb.append(size);
        return sb.toString();
    }

    public boolean isEmpty() {
        return last == null;
    }

    public int size() {
        return size;
    }

    public Path subPath(Node node) {
        return new Path(node, last);
    }
}
