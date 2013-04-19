package ares.engine.algorithms.pathfinding;

import ares.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.engine.algorithms.pathfinding.costfunctions.CostFunctions;
import ares.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.engine.movement.MovementCost;
import ares.scenario.Scenario;
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
    private final CostFunction costFunction = CostFunctions.FASTEST;

    public AStar(Heuristic heuristic, int length) {
        super(heuristic);
        this.length = length;
    }

    public AStar(Scenario scenario, Heuristic heuristic) {
        super(heuristic);
        this.length = scenario.getBoard().getWidth() * scenario.getBoard().getHeight();
    }

    @Override
    public Path getPath(Tile origin, Tile destination, Unit unit) {

        if (origin.equals(destination)) {
            return null;
        }

        BitSet closedSet = new BitSet(length);
        // Map is used to have constant cost when getting neighbors
        Map<Integer, Node> map = new HashMap<>();
        // openSet is ordered by F, lowest node.getF() will be at the top
        int initialCapacity = (int) (length / OPEN_SET_INITIAL_CAPACITY_DIVISOR);
        Queue<Node> openSet = new PriorityQueue<>(initialCapacity);

        Node first = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
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
                    // if current node has been fully explored (is in the closed set) then skip it
                    continue;
                }
                double tentativeG = current.getG() + costFunction.getCost(toDir, destination, unit);

                Node neighbor = map.get(index);
                if (neighbor == null) {
                    neighbor = new Node(tile, toDir, current, tentativeG,heuristic.getCost(tile, destination, unit));
                    map.put(index, neighbor);
                    if (tentativeG < MovementCost.IMPASSABLE) {
                        openSet.add(neighbor);
                    }
                } else if (neighbor.getG() > tentativeG) {
                    Tile fromTile = current.getTile();
                    Tile toTile = neighbor.getTile();
                    // Obtain direction relative to toTile
                    Direction dir = Board.getDirBetween(toTile, fromTile);
                    neighbor.setDirection(dir);
                    neighbor.setPrev(current);
                    neighbor.setCost(tentativeG, heuristic.getCost(neighbor.getTile(), destination, unit));
                }
            }
            //assert current != null;
        }
        return null;
    }
}
