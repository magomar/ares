package ares.platform.engine.algorithms.pathfinding;

import java.util.Collection;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ExtendedPath extends Path {

    private final Collection<Node> openSetNodes;
    private final Collection<Node> closedSetNodes;
    private final int nodesVisited;

    public ExtendedPath(Node first, Node last, Collection<Node> openSetNodes, Collection<Node> closedSetNodes) {
        super(first, last);
        this.openSetNodes = openSetNodes;
        this.closedSetNodes = closedSetNodes;
        nodesVisited = openSetNodes.size() + closedSetNodes.size();
    }

    public Collection<Node> getOpenSetNodes() {
        return openSetNodes;
    }

    public Collection<Node> getClosedSetNodes() {
        return closedSetNodes;
    }

    public int getNumNodesVisited() {
        return nodesVisited;
    }
}
