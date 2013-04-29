package ares.engine.algorithms.pathfinding;

import ares.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.engine.algorithms.pathfinding.costfunctions.CostFunctions;
import ares.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.engine.movement.MovementCost;
import ares.scenario.board.Direction;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AStar extends AbstractPathFinder {

//    private static int nodes;
    private final CostFunction costFunction = CostFunctions.FASTEST;

    public AStar(Heuristic heuristic) {
        super(heuristic);

    }

    @Override
    public Path getPath(Tile origin, Tile destination, Unit unit) {
        // Create data structures
        Map<Integer, Node> closedSet = new HashMap<>();
        OpenSet openSet = new OpenSet();
        Node firstNode = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
//        nodes = 1;
        openSet.add(firstNode);

        while (!openSet.isEmpty()) {
            // Obtain next best node from openSet and add it to the closed in the 
            Node bestNode = openSet.poll();
            int bestNodeIndex = bestNode.getIndex();
            closedSet.put(bestNodeIndex, bestNode);
            // Check for termination
            if (bestNode.getTile().equals(destination)) {
                Path path = new Path(firstNode, bestNode);
                return path;
            }
            // Expand best node (Generate successors)
            for (Map.Entry<Direction, Tile> entry : bestNode.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Direction toDir = fromDir.getOpposite();
                Tile neighbor = entry.getValue();
                int neighborIndex = neighbor.getIndex();
                double localCost = costFunction.getCost(toDir, neighbor, unit);
                // Skip impassable neighbors
                if (localCost >= MovementCost.IMPASSABLE) {
                    continue;
                }
                double tentative_g = bestNode.getG() + localCost;
                Node neighborNode;
                if (closedSet.containsKey(neighborIndex)) {
                    neighborNode = closedSet.get(neighborIndex);
                    if (tentative_g >= neighborNode.getG()) {
                        continue;
                    }
                }
                if (openSet.contains(neighborIndex)) {
                    neighborNode = openSet.get(neighborIndex);
                    if (tentative_g < neighborNode.getG()) {
                        neighborNode.setPrev(toDir, bestNode, tentative_g);
                    }
                } else {
                    neighborNode = new Node(neighbor, toDir, bestNode, tentative_g, heuristic.getCost(neighbor, destination, unit));
                    openSet.add(neighborNode);
//                    nodes++;
                }
            }
        }
        return null;
    }

    class OpenSet {

        Queue<Node> list;
        Map<Integer, Node> map;

        OpenSet() {
            list = new PriorityQueue<>();
            map = new HashMap<>();
        }

        void add(Node node) {
            list.add(node);
            map.put(node.getIndex(), node);
        }

        Node poll() {
            return list.poll();
        }

        boolean contains(int index) {
            return map.containsKey(index);
        }

        Node get(int index) {
            return map.get(index);
        }

        boolean isEmpty() {
            return list.isEmpty();
        }
    }
}
