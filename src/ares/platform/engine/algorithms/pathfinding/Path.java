package ares.platform.engine.algorithms.pathfinding;

import ares.application.shared.models.board.TileModel;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Path {

    private final Node last;
    private final Node first;
    private int size;

    /**
     * Creates a path connecting the {@code first} to the
     * {@code last}  {@link Node}}.
     *
     * @param first the first node in the path
     * @param last  the last node in the path
     */
    public Path(Node first, Node last) {
        this.first = first;
        this.last = last;
        size = 1;
        if (first.equals(last)) {
            return;
        }
        Node current = last;
        Node prev = current.getPrev();
        while (prev != null) {
            prev.setNext(current);
            current = prev;
            prev = current.getPrev();
            size++;
            if (current.equals(first)) {
//                this.first = current;
                return;
            }
        }
        throw new IllegalArgumentException("First node not reachable last node");

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
        Node n;
        for (n = first; n != null; n = n.getNext()) {
            sb.append(n.getTile().toString());
        }
//        sb.append(n.getTile().toString());
        sb.append("}");
        sb.append(size);
        return sb.toString();
    }

    public boolean isEmpty() {
        return last == null;
    }

    /**
     * Gets the number of nodes (or tiles) in this path
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * Gets a subpath of this path from the given {@code node}
     *
     * @param node
     * @return
     */
    public Path subPathFrom(Node node) {
        return new Path(node, last);
    }
    /**
     * Gets a subpath of this path from the {@link #first} {@link Node} to the given {@code node}
     *
     * @param node
     * @return
     */
    public Path subPathTo(Node node) {
        return new Path(first, node);
    }

    /**
     * Gets a subpath of the this path from the given {@code tile}
     *
     * @param tile
     * @return
     */
    public Path subPath(TileModel tile) {
        int tileIndex = tile.getIndex();
        Node node;
        for (node = first; node != null; node = node.getNext()) {
            if (node.getTile().getIndex() == tileIndex) {
                return subPathFrom(node);
            }
        }
        return null;
    }
}
