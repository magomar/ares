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
 *
 * 
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class BeamSearch extends AbstractPathfinder {

    public BeamSearch() {
    }

    public BeamSearch(Heuristic heuristic, CostFunction costFunction) {
        super(heuristic, costFunction);
    }
    
    @Override
    public Path getPath(Tile origin, Tile destination, Unit unit) {
        if (origin.equals(destination)) {
            return null;
        }
        // Create data structures
        Map<Integer, Node> closedSet = new HashMap<>();
        BeamSearch.OpenSet openSet = new BeamSearch.OpenSet(20);
        Node firstNode = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
        
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
                }
            }
        }
        return null;
    }

    @Override
    public ExtendedPath getExtendedPath(Tile origin, Tile destination, Unit unit) {
          if (origin.equals(destination)) {
            return null;
        }
        // Create data structures
        Map<Integer, Node> closedSet = new HashMap<>();
        BeamSearch.OpenSet openSet = new BeamSearch.OpenSet(20);
        Node firstNode = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
        
        openSet.add(firstNode);

        while (!openSet.isEmpty()) {
            // Obtain next best node from openSet and add it to the closed in the 
            Node bestNode = openSet.poll();
            int bestNodeIndex = bestNode.getIndex();
            closedSet.put(bestNodeIndex, bestNode);
            // Check for termination
            if (bestNode.getTile().equals(destination)) {
                ExtendedPath path = new ExtendedPath(firstNode, bestNode, openSet.list, closedSet.values());
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
                }
            }
        }
        return null;
    }

    @Override
    public Heuristic getHeuristic() {
        return heuristic;
    }

    @Override
    public CostFunction getCostFunction() {
        return costFunction;
    }

    
    private class OpenSet {

        int nodeLimit;
        Queue<Node> list;
        Map<Integer, Node> map;

        OpenSet(int nodeLimit) {
            this.nodeLimit = nodeLimit;
            list = new PriorityQueue<>();
            map = new HashMap<>();
        }

        void add(Node node) {
            if (list.size() == nodeLimit) {
                Node lastNode = pollLast();
                
                if (node.compareTo(lastNode) < 0) {
                    list.add(node);
                }
                else {
                    list.add(lastNode);
                }
            }
            else {
                list.add(node);
                map.put(node.getIndex(), node);
            }
        }

        Node poll() {
            return list.poll();
        }
        
        Node pollLast() {
            int queueSize = list.size();
            Node lastNode;
            PriorityQueue<Node> newQueue = new PriorityQueue<>();
            
            for (int i=0; i<queueSize-1; i++) {
                newQueue.add(list.poll());
            }
            
            lastNode = list.poll();
            
            list = newQueue;
            
            return lastNode;
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
