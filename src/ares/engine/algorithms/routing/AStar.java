package ares.engine.algorithms.routing;

import ares.application.models.board.*;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.util.*;

/**
 * 
 * @author Heine <heisncfr@inf.upv.es>
 */
public class AStar extends AbstractPathFinder {

    private int length;

    public AStar(int length) {
        this.length = length;
    }

    @Override
    public Path getPath(Tile orig, Tile dest) {

        if (orig.equals(dest)) {
            return null;
        }

        BitSet closedSet = new BitSet(length);
        // Map is used to have constant cost when getting neighbours
        Map<Integer, Node> map = new HashMap<>();
        // openSet is ordered by F, lowest node.getF() will be at the top
        PriorityQueue<Node> openSet = new PriorityQueue<>(16, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getF() < o2.getF()) {
                    return -1;
                }
                if (o1.getF() > o2.getF()) {
                    return 1;
                }
                return 0;
            }
        });

        Node start;
        Node goal = new Node(dest);
        Node current = new Node(orig);
        current.setPrev(null);
        current.setG(0);
        current.setF(heuristic.getCost(orig.getCoordinates(), dest.getCoordinates()));
        current.setFrom(Direction.C);
        map.put(current.getTile().getIndex(), current);
        openSet.add(current);
        
        start = current; // save it for the double linked Path
        
        while (!openSet.isEmpty()) {

            //Update current node
            current = openSet.poll();

            if (current.equals(goal)) {
                return new Path(start,current);
            }

            closedSet.set(current.getTile().getIndex());
            for (Map.Entry<Direction, Tile> iter : current.getTile().getNeighbors().entrySet()) {
                int index = iter.getValue().getIndex();
                if (closedSet.get(index)) {
                    continue;
                }
                double tentativeG = current.getG() + current.getTile().getMoveCost(iter.getKey()).getActualCost(orig.getTopUnit(), iter.getValue(), iter.getKey().getOpposite(), avoidingEnemies(), getPathType()==SHORTEST);

                Node neighbour = map.get(index);
                if (neighbour == null) {
                    neighbour = new Node(iter.getValue(), current);
                    neighbour.setFrom(iter.getKey().getOpposite());
                    neighbour.setG(tentativeG);
                    neighbour.setF(tentativeG + heuristic.getCost(neighbour.getTile().getCoordinates(), dest.getCoordinates()));
                    map.put(index, neighbour);
                    if (tentativeG < Integer.MAX_VALUE) {
                        openSet.add(neighbour);
                    }
                } else if (neighbour.getG() > tentativeG) {
                    neighbour.setPrev(current);
                    neighbour.setG(tentativeG);
                    neighbour.setF(tentativeG + heuristic.getCost(neighbour.getTile().getCoordinates(), dest.getCoordinates()));
                }
            }
            //assert current != null;
        }
        return null;
    }
}
