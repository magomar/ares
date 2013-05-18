package ares.engine.algorithms.pathfinding;

import ares.application.models.board.TileModel;
import ares.scenario.board.Tile;
import java.util.ArrayList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Path {

    private Node last;
    private Node first;
    private int size;
    private ArrayList<Tile> openSetTiles;
    private ArrayList<Tile> closedSetTiles;

    /**
     * Creates a path connecting the {@code first} to the
     * {@code last}  {@link Node}}.
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

    public int size() {
        return size;
    }

    public Path subPath(Node node) {
        return new Path(node, last);
    }

    public Path subPath(TileModel from) {
        int tileIndex = from.getIndex();
        Node n;
        for (n = first; n != null; n = n.getNext()) {
            if (n.getTile().getIndex() == tileIndex) {
                return new Path(n, last);
            }
        }
        return null;
    }
    
    public void setOpenSetTiles(ArrayList<Tile> tiles) {
        openSetTiles = tiles;
    }
    
    public ArrayList<Tile> getOpenSetTiles() {
        return openSetTiles;
    }
    
    public void setClosedSetTiles(ArrayList<Tile> tiles) {
        closedSetTiles = tiles;
    }
    
    public ArrayList<Tile> getClosedSetTiles() {
        return closedSetTiles;
    }
}
