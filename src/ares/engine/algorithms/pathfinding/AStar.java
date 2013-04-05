package ares.engine.algorithms.pathfinding;

import ares.engine.movement.MovementCost;
import ares.scenario.board.Board;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.*;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AStar extends AbstractPathFinder {

    private static final int OPEN_SET_INITIAL_CAPACITY_DIVISOR = 4;
    private int length;

    public AStar() {
        this(100);
    }

    public AStar(int length) {
        this.length = length;
    }

    @Override
    public Path getPath(Tile origin, Tile destination, Unit unit) {

        if (origin.equals(destination)) {
            return null;
        }

        BitSet closedSet = new BitSet(length);
        // Map is used to have constant cost when getting neighbours
        Map<Integer, Node> map = new HashMap<>();
        // openSet is ordered by F, lowest node.getF() will be at the top
        int initialCapacity = (int) (length / OPEN_SET_INITIAL_CAPACITY_DIVISOR);
        Queue<Node> openSet = new PriorityQueue<>(initialCapacity);

        Node first = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination));
        map.put(origin.getIndex(), first);
        openSet.add(first);

        Node goal = new Node(destination);
        Node current;
        while (!openSet.isEmpty()) {
            // Take next node in openSet
            current = openSet.remove();
            if (current.equals(goal)) {
                // Goal reached, returning path from first to current node
                return new Path(first, current);
            }
            // mark current as closed
            closedSet.set(current.getTile().getIndex());
            // Expand node
            for (Map.Entry<Direction, Tile> entry : current.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Direction toDir = fromDir.getOpposite();
                Tile tile = entry.getValue();
                int index = tile.getIndex();
                if (closedSet.get(index)) {
                    // if current tile was already visited by the algorithm skip it
                    continue;
                }
                MovementCost mc = tile.getMoveCost(toDir);
                double tentativeG = current.getG() + mc.getEstimatedCost(unit);

                Node neighbour = map.get(index);
                if (neighbour == null) {
                    neighbour = new Node(tile, toDir, current, tentativeG, tentativeG + heuristic.getCost(tile, destination));
                    map.put(index, neighbour);
                    if (tentativeG < MovementCost.IMPASSABLE) {
                        openSet.add(neighbour);
                    }
                } else if (neighbour.getG() > tentativeG) {
                    Tile fromTile = current.getTile();
                    Tile toTile = neighbour.getTile();
                    // Obtain direction relative to toTile
                    Direction dir = Board.getDirBetween(toTile, fromTile);
                    neighbour.setDirection(dir);
                    neighbour.setPrev(current);
                    neighbour.setG(tentativeG);
                    neighbour.setF(tentativeG
                            + heuristic.getCost(neighbour.getTile(), destination));
                }
            }
            //assert current != null;
        }
        return null;
    }
}
