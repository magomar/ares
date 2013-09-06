package ares.platform.engine.algorithms.pathfinding;

import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.movement.MovementCost;
import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class AStar extends AbstractPathfinder {

    public AStar(Heuristic heuristic, CostFunction costFunction) {
        super(heuristic, costFunction);
    }

    @Override
    public Path findPath(Tile origin, Tile destination, Unit unit) {
        if (origin.equals(destination)) {
            return null;
        }
        // Create and initialize data structures
        // The set of nodes already evaluated.
        Map<Integer, Node> closedSet = new HashMap<>();
        // The set of tentative nodes to be evaluated, initially containing the initial node
        OpenSet openSet = new OpenSet();
        Node firstNode = Node.createInitialNode(origin, heuristic.getCost(origin, destination, unit));
        openSet.add(firstNode);

        while (!openSet.isEmpty()) {
            // Obtain best node and remove from open set
            Node bestNode = openSet.poll();
            // Add best node to closed set
            closedSet.put(bestNode.getIndex(), bestNode);
            // Check for termination
            if (bestNode.getTile().equals(destination)) {
                Path path = new Path(firstNode, bestNode);
                return path;
            }
            // Expand best node (Generate successors)
            Map<Direction, Tile> neighbors = bestNode.getTile().getNeighbors();
            for (Map.Entry<Direction, Tile> entry : neighbors.entrySet()) {
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
                    neighborNode = Node.createNode(neighbor, toDir, bestNode, tentative_g, heuristic.getCost(neighbor, destination, unit));
                    openSet.add(neighborNode);
                }
            }
        }
        return null;
    }

    @Override
    public CostFunction getCostFunction() {
        return costFunction;
    }

    @Override
    public Heuristic getHeuristic() {
        return heuristic;
    }

    @Override
    public ExtendedPath getExtendedPath(Tile origin, Tile destination, Unit unit) {
        if (origin.equals(destination)) {
            return null;
        }
        // Create and initialize data structures
        // The set of nodes already evaluated.
        Map<Integer, Node> closedSet = new HashMap<>();
        // The set of tentative nodes to be evaluated, initially containing the initial node
        OpenSet openSet = new OpenSet();
        Node firstNode = Node.createInitialNode(origin, heuristic.getCost(origin, destination, unit));
        openSet.add(firstNode);
        while (!openSet.isEmpty()) {
            // Obtain best node and remove from open set
            Node bestNode = openSet.poll();
            // Add best node to closed set
            closedSet.put(bestNode.getIndex(), bestNode);
            // Check for termination
            if (bestNode.getTile().equals(destination)) {
                ExtendedPath path = new ExtendedPath(firstNode, bestNode, openSet.list, closedSet.values());
                return path;
            }
            // Expand best node (Generate successors)
            Map<Direction, Tile> neighbors = bestNode.getTile().getNeighbors();
            for (Map.Entry<Direction, Tile> entry : neighbors.entrySet()) {
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
                    neighborNode = Node.createNode(neighbor, toDir, bestNode, tentative_g, heuristic.getCost(neighbor, destination, unit));
                    openSet.add(neighborNode);
                }
            }
        }
        return null;
    }

    class OpenSet {

        final Queue<Node> list;
        final Map<Integer, Node> map;

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
