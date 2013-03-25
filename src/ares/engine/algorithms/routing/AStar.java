package ares.engine.algorithms.routing;

import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import java.awt.Point;
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
    public Path getPath(Tile origin, Tile destination) {

        if (origin.equals(destination)) {
            return null;
        }

        BitSet closedSet = new BitSet(length);
        // Map is used to have constant cost when getting neighbours
        Map<Integer, Node> map = new HashMap<>();
        // openSet is ordered by F, lowest node.getF() will be at the top
        Point from = origin.getCoordinates();
        Point to = destination.getCoordinates();
        int distance = DistanceCalculator.getCost(from, to, algorithm);
        Queue<Node> openSet = new PriorityQueue<>((int)(distance * 1.5));

        Node start;
        Node goal = new Node(destination);
        Node current = new Node(origin);
        current.setPrev(null);
        current.setG(0);
        current.setF(heuristic.getCost(origin.getCoordinates(), destination.getCoordinates()));
        current.setFrom(Direction.C);
        map.put(current.getTile().getIndex(), current);
        openSet.offer(current);

        start = current; // save it for the double linked Path

        while (!openSet.isEmpty()) {

            //Update current node
            current = openSet.poll();

            if (current.equals(goal)) {
                return new Path(start, current);
            }

            closedSet.set(current.getTile().getIndex());
            for (Map.Entry<Direction, Tile> iter : current.getTile().getNeighbors().entrySet()) {
                int index = iter.getValue().getIndex();
                if (closedSet.get(index)) {
                    continue;
                }
                double tentativeG = current.getG() + current.getTile().getMoveCost(iter.getKey()).getActualCost(origin.getTopUnit(), iter.getValue(), iter.getKey().getOpposite(), avoidingEnemies(), getPathType() == SHORTEST);

                Node neighbour = map.get(index);
                if (neighbour == null) {
                    neighbour = new Node(iter.getValue(), current);
                    neighbour.setFrom(iter.getKey().getOpposite());
                    neighbour.setG(tentativeG);
                    neighbour.setF(tentativeG + heuristic.getCost(neighbour.getTile().getCoordinates(), destination.getCoordinates()));
                    map.put(index, neighbour);
                    if (tentativeG < Integer.MAX_VALUE) {
                        openSet.add(neighbour);
                    }
                } else if (neighbour.getG() > tentativeG) {
                    Tile fromTile = current.getTile();
                    Tile toTile = neighbour.getTile();
                    Direction dir = Board.getDirBetween(fromTile, toTile);
                    neighbour.setFrom(dir.getOpposite());
                    neighbour.setPrev(current);
                    neighbour.setG(tentativeG);
                    neighbour.setF(tentativeG + heuristic.getCost(neighbour.getTile().getCoordinates(), destination.getCoordinates()));
                }
            }
            //assert current != null;
        }
        return null;
    }
}
