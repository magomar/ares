package ares.engine.algorithms.pathfinding;

import java.util.*;

/**
 * Path for pathfinding algorithms, sort of a poor man's linked list.
 *
 * Use it at your own risk, this is not meant to be bug free. {@code first} can be null and {@code nodes} might be an
 * empty array even with a correct {@code last} node.
 *
 * If you still have faith run {@link Path#relink()} to correct some issues with your path
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class Path {
    private Node last;
    private Node first;
    // Like a back up collection, not really necessary
    private LinkedList<Node> nodes;
    

    /**
     * Returns a new instance with an empty {@link #nodes} list.
     */
    public Path() {
        nodes = new LinkedList<>();
    }

//    /**
//     * Constructor by copy another object.
//     *
//     * @param p The path object to clone.
//     */
//    public Path(Path p) {
//        this();
//        nodes = new LinkedList<>(p.getNodes());
//        last = nodes.getLast();
//        first = nodes.getFirst();
//    }

    /**
     * Returns a new instance having {@code node} as the last {@link Node} in {@link #nodes}.
     *
     * @param last
     */
    public Path(Node last) {
        this.last = last;
        this.last.setNext(null);
        nodes = new LinkedList<>();
    }

    /**
     * Returns a new instance having {@code first}  as the first {@link Node} and {@code last}  as the last {@link Node} in {@link #nodes}.
     *
     * @param first
     * @param last
     */
    public Path(Node first, Node last) {
        this.first = first;
        this.last = last;
        nodes = new LinkedList<>();
    }

    /**
     *
     * @return last node of the path
     */
    public Node getLast() {
        return last;
    }

    /**
     * Get the first point on the path. WARNING: can be null
     *
     * @return The first point visited by the path.
     * @see Path#relink()
     */
    public Node getFirst() {
        return first;
    }

    /**
     * Set the first point on the path.
     */
    public void setFirst(Node t) {
        if (first != null) {
            t.setNext(first);
        }
        first = t;
        first.setPrev(null);
        nodes.addFirst(first);
    }

    public void setLast(Node l) {
        l.setPrev(last);
        last = l;
        last.setNext(null);
        nodes.addLast(last);
    }

    /**
     * Get the nodes in the path. WARNING: can be empty even with nodes in the path.
     *
     * @return node list
     * @see Path#relink()
     */
    public LinkedList<Node> getNodes() {
        return nodes;
    }

    /**
     * Corrects path inconsistencies.
     *
     * Word of advice: stick to java.util.LinkedList
     *
     * @return 1 if path is corrected. 0 if there's no need to run this method (because path is ok). -1 something went
     * wrong.
     */
    public int relink() {

        // No nodes in here!
        if (last == null) {
            return 0;
        }

        if (nodes.isEmpty()) {
            // Seems like we have to add some nodes
            if (first == null) {
                Node current = last;
                // iterate through the nodes backwards
                while (current.getPrev() != null) {
                    setFirst(current);
                    current = current.getPrev();
                }
                first = current;
                first.setPrev(null);
                nodes.addFirst(first);
            } else {
                // If this happens then you should have used a real LinkedList instead
                // + but oh, well...                
                assert first != null;
                Node currentFirst = last;
                Node rightOfFirst = last;
                //Move backwards until currentFirst has no prev
                while (currentFirst.getPrev() != null) {
                    nodes.addFirst(currentFirst);
                    currentFirst.setNext(rightOfFirst);
                    rightOfFirst = currentFirst;
                    currentFirst = currentFirst.getPrev();
                }
                // Final touches
                last.setNext(null);
                currentFirst.setNext(rightOfFirst);


                int index = 0;
                Node currentLast = first;
                Node leftOfLast = first;
                //Same as before but moving forward
                while (currentLast.getNext() != null && currentLast != nodes.getFirst().getNext()) {
                    nodes.add(index, currentLast);
                    currentLast.setPrev(leftOfLast);
                    leftOfLast = currentLast;
                    currentLast = currentLast.getNext();
                    index++;
                }
                first.setPrev(null);
                currentLast.setPrev(leftOfLast);

                // Finally linking the two loose ends
                currentFirst.setNext(currentLast);
            }
            return 1;

        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Path{" + nodes + '}';
    }

    public String toStringIterator() {
        StringBuilder sb = new StringBuilder("Path {");
        Node l;
        for (l = last; l.getPrev() != null; l = l.getPrev()) {
            sb.append(l.getTile().toString());
        }
        sb.append(l.getTile().toString());
        sb.append("}");
        return sb.toString();
    }

    public boolean isEmpty() {
        return last == null;
    }
}
