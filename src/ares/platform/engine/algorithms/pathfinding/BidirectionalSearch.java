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
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class BidirectionalSearch extends AbstractPathfinder {
    public BidirectionalSearch() {
    }

    public BidirectionalSearch(Heuristic heuristic, CostFunction costFunction) {
        super(heuristic, costFunction);
    }

    @Override
    public Path getPath(Tile origin, Tile destination, Unit unit) {
        if (origin.equals(destination)) {
            return null;
        }
        // Create data structures
        Map<Integer, Node> closedSetForward = new HashMap<>();
        Map<Integer, InvertedNode> closedSetBackwards = new HashMap<>();
        OpenSet openSetForward = new OpenSet();
        InvertedOpenSet openSetBackwards = new InvertedOpenSet();
        Node firstNodeForward = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
        InvertedNode firstNodeBackwards = new InvertedNode(destination, Direction.C, null, 0, heuristic.getCost(destination, origin, unit));
        openSetForward.add(firstNodeForward);
        openSetBackwards.add(firstNodeBackwards);
        Node bestNodeForward = null;
        InvertedNode bestNodeBackwards = null;

        while (!openSetForward.isEmpty() || !openSetBackwards.isEmpty()) {
            // Obtain next best node from openSet and add it to the closed in the 
            if (!openSetForward.isEmpty()) {
                bestNodeForward = openSetForward.poll();
                int bestNodeForwardIndex = bestNodeForward.getIndex();
                closedSetForward.put(bestNodeForwardIndex, bestNodeForward);
            }
            if (!openSetBackwards.isEmpty()) {
                bestNodeBackwards = openSetBackwards.poll();
                int bestNodeBackwardsIndex = bestNodeBackwards.getIndex();
                closedSetBackwards.put(bestNodeBackwardsIndex, bestNodeBackwards);
            }
            
            // Check for termination
            // If forward search reaches destination
            if (bestNodeForward.getTile().equals(destination)) {
                ExtendedPath path = new ExtendedPath(firstNodeForward, bestNodeForward,
                        openSetForward.list, closedSetForward.values());
                return path;
            }
            
            boolean closedForwardContainsBest = false;
            boolean closedBackwardsContainsBest = false;
            for (Node node : closedSetForward.values()) {
                if (node.getTile().equals(bestNodeBackwards.getTile())) {
                    closedForwardContainsBest = true;
                    break;
                }
            }
            for (InvertedNode node : closedSetBackwards.values()) {
                if (node.getTile().equals(bestNodeForward.getTile())) {
                    closedBackwardsContainsBest = true;
                    break;
                }
            }
            
            // If both searches collide in the same tile or
            // backwards search reaches origin or
            // backwards search collides with a forward's closed set node or
            // forward search collides with a backwards' closed set node
            if (bestNodeForward.getTile().equals(bestNodeBackwards.getTile()) ||
                    bestNodeBackwards.getTile().equals(origin) ||
                    closedForwardContainsBest || closedBackwardsContainsBest) {
                InvertedNode currentNode;
                // Start from the starting node
                if (bestNodeBackwards.getTile().equals(origin)) {
                    bestNodeForward = firstNodeForward;
                }// Start reverting from the node which backwards search collided with
                else if (closedForwardContainsBest) {
                    bestNodeForward = closedSetForward.get(bestNodeBackwards.getIndex());
                }
                // Start reverting from the node which forward search collided with
                if (closedBackwardsContainsBest && !closedForwardContainsBest) {
                    currentNode = closedSetBackwards.get(bestNodeForward.getIndex());
                }
                else {
                    currentNode = bestNodeBackwards;
                }
                
                while (!currentNode.getTile().equals(destination)) {
                    currentNode = currentNode.getNext();
                    Tile neighbor = currentNode.getTile();
                    Direction toDir = Direction.C;
                    for (Direction direction : neighbor.getNeighbors().keySet()) {
                        if (neighbor.getNeighbor(direction).equals(bestNodeForward.getTile())) {
                            toDir = direction;
                            break;
                        }
                    }
                    double localCost = costFunction.getCost(toDir, neighbor, unit);
                    Node neighborNode = new Node(neighbor, toDir, bestNodeForward,
                            bestNodeForward.getG()+localCost, 0);
                    bestNodeForward = neighborNode;
                }
                
                Path path = new Path(firstNodeForward, bestNodeForward);
                return path;
            }
            
            // Expand best node (Generate successors)
            for (Map.Entry<Direction, Tile> entry : bestNodeForward.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Direction toDir = fromDir.getOpposite();
                Tile neighbor = entry.getValue();
                int neighborIndex = neighbor.getIndex();
                double localCost = costFunction.getCost(toDir, neighbor, unit);
                // Skip impassable neighbors
                if (localCost >= MovementCost.IMPASSABLE) {
                    continue;
                }
                double tentative_g = bestNodeForward.getG() + localCost;
                Node neighborNode;
                if (closedSetForward.containsKey(neighborIndex)) {
                    neighborNode = closedSetForward.get(neighborIndex);
                    if (tentative_g >= neighborNode.getG()) {
                        continue;
                    }
                }
                if (openSetForward.contains(neighborIndex)) {
                    neighborNode = openSetForward.get(neighborIndex);
                    if (tentative_g < neighborNode.getG()) {
                        neighborNode.setPrev(toDir, bestNodeForward, tentative_g);
                    }
                } else {
                    neighborNode = new Node(neighbor, toDir, bestNodeForward, tentative_g, 
                            heuristic.getCost(neighbor, destination, unit));
                    openSetForward.add(neighborNode);
                }
            }
            
            for (Map.Entry<Direction, Tile> entry : bestNodeBackwards.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Tile neighbor = entry.getValue();
                int neighborIndex = neighbor.getIndex();
                double localCost = costFunction.getCost(fromDir, bestNodeBackwards.getTile(), unit);
                // Skip impassable neighbors
                if (localCost >= MovementCost.IMPASSABLE) {
                    continue;
                }// TODO backtrack and update current path 
                double tentative_g = bestNodeBackwards.getG() + localCost;
                InvertedNode neighborNode;
                if (closedSetBackwards.containsKey(neighborIndex)) {
                    neighborNode = closedSetBackwards.get(neighborIndex);
                    if (tentative_g >= neighborNode.getG()) {
                        continue;
                    }
                }
                if (openSetBackwards.contains(neighborIndex)) {
                    neighborNode = openSetBackwards.get(neighborIndex);
                    if (tentative_g < neighborNode.getG()) {
                        neighborNode.setNext(fromDir, bestNodeBackwards, tentative_g);
                    }
                } else {
                    neighborNode = new InvertedNode(neighbor, fromDir, bestNodeBackwards, tentative_g, 
                            heuristic.getCost(neighbor, origin, unit));
                    openSetBackwards.add(neighborNode);
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
        // Create data structures
        Map<Integer, Node> closedSetForward = new HashMap<>();
        Map<Integer, InvertedNode> closedSetBackwards = new HashMap<>();
        OpenSet openSetForward = new OpenSet();
        InvertedOpenSet openSetBackwards = new InvertedOpenSet();
        Node firstNodeForward = new Node(origin, Direction.C, null, 0, heuristic.getCost(origin, destination, unit));
        InvertedNode firstNodeBackwards = new InvertedNode(destination, Direction.C, null, 0, heuristic.getCost(destination, origin, unit));
        openSetForward.add(firstNodeForward);
        openSetBackwards.add(firstNodeBackwards);
        Node bestNodeForward = null;
        InvertedNode bestNodeBackwards = null;

        while (!openSetForward.isEmpty() || !openSetBackwards.isEmpty()) {
            // Obtain next best node from openSet and add it to the closed in the 
            if (!openSetForward.isEmpty()) {
                bestNodeForward = openSetForward.poll();
                int bestNodeForwardIndex = bestNodeForward.getIndex();
                closedSetForward.put(bestNodeForwardIndex, bestNodeForward);
            }
            if (!openSetBackwards.isEmpty()) {
                bestNodeBackwards = openSetBackwards.poll();
                int bestNodeBackwardsIndex = bestNodeBackwards.getIndex();
                closedSetBackwards.put(bestNodeBackwardsIndex, bestNodeBackwards);
            }
            
            // Check for termination
            // If forward search reaches destination
            if (bestNodeForward.getTile().equals(destination)) {
                ExtendedPath path = new ExtendedPath(firstNodeForward, bestNodeForward,
                        openSetForward.list, closedSetForward.values());
                return path;
            }
            
            boolean closedForwardContainsBest = false;
            boolean closedBackwardsContainsBest = false;
            for (Node node : closedSetForward.values()) {
                if (node.getTile().equals(bestNodeBackwards.getTile())) {
                    closedForwardContainsBest = true;
                    break;
                }
            }
            for (InvertedNode node : closedSetBackwards.values()) {
                if (node.getTile().equals(bestNodeForward.getTile())) {
                    closedBackwardsContainsBest = true;
                    break;
                }
            }
            
            // If both searches collide in the same tile or
            // backwards search reaches origin or
            // backwards search collides with a forward's closed set node or
            // forward search collides with a backwards' closed set node
            if (bestNodeForward.getTile().equals(bestNodeBackwards.getTile()) ||
                    bestNodeBackwards.getTile().equals(origin) ||
                    closedForwardContainsBest || closedBackwardsContainsBest) {
                InvertedNode currentNode;
                // Start from the starting node
                if (bestNodeBackwards.getTile().equals(origin)) {
                    bestNodeForward = firstNodeForward;
                }// Start reverting from the node which backwards search collided with
                else if (closedForwardContainsBest) {
                    bestNodeForward = closedSetForward.get(bestNodeBackwards.getIndex());
                }
                // Start reverting from the node which forward search collided with
                if (closedBackwardsContainsBest && !closedForwardContainsBest) {
                    currentNode = closedSetBackwards.get(bestNodeForward.getIndex());
                }
                else {
                    currentNode = bestNodeBackwards;
                }
                
                while (!currentNode.getTile().equals(destination)) {
                    currentNode = currentNode.getNext();
                    Tile neighbor = currentNode.getTile();
                    Direction toDir = Direction.C;
                    for (Direction direction : neighbor.getNeighbors().keySet()) {
                        if (neighbor.getNeighbor(direction).equals(bestNodeForward.getTile())) {
                            toDir = direction;
                            break;
                        }
                    }
                    double localCost = costFunction.getCost(toDir, neighbor, unit);
                    Node neighborNode = new Node(neighbor, toDir, bestNodeForward,
                            bestNodeForward.getG()+localCost, 0);
                    bestNodeForward = neighborNode;
                }
                
                for (InvertedNode invertedNode : openSetBackwards.list) {
                    if (!openSetForward.list.contains(invertedNode)) {
                        openSetForward.list.add(invertedNode);
                    }
                }
                
                for (InvertedNode invertedNode : closedSetBackwards.values()) {
                    if (!closedSetForward.values().contains(invertedNode)) {
                        closedSetForward.put(invertedNode.getIndex(), invertedNode);
                    }
                }
                
                ExtendedPath path = new ExtendedPath(firstNodeForward, bestNodeForward,
                        openSetForward.list, closedSetForward.values());
                return path;
            }
            
            // Expand best node (Generate successors)
            for (Map.Entry<Direction, Tile> entry : bestNodeForward.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Direction toDir = fromDir.getOpposite();
                Tile neighbor = entry.getValue();
                int neighborIndex = neighbor.getIndex();
                double localCost = costFunction.getCost(toDir, neighbor, unit);
                // Skip impassable neighbors
                if (localCost >= MovementCost.IMPASSABLE) {
                    continue;
                }
                double tentative_g = bestNodeForward.getG() + localCost;
                Node neighborNode;
                if (closedSetForward.containsKey(neighborIndex)) {
                    neighborNode = closedSetForward.get(neighborIndex);
                    if (tentative_g >= neighborNode.getG()) {
                        continue;
                    }
                }
                if (openSetForward.contains(neighborIndex)) {
                    neighborNode = openSetForward.get(neighborIndex);
                    if (tentative_g < neighborNode.getG()) {
                        neighborNode.setPrev(toDir, bestNodeForward, tentative_g);
                    }
                } else {
                    neighborNode = new Node(neighbor, toDir, bestNodeForward, tentative_g, 
                            heuristic.getCost(neighbor, destination, unit));
                    openSetForward.add(neighborNode);
                }
            }
            
            for (Map.Entry<Direction, Tile> entry : bestNodeBackwards.getTile().getNeighbors().entrySet()) {
                Direction fromDir = entry.getKey();
                Tile neighbor = entry.getValue();
                int neighborIndex = neighbor.getIndex();
                double localCost = costFunction.getCost(fromDir, bestNodeBackwards.getTile(), unit);
                // Skip impassable neighbors
                if (localCost >= MovementCost.IMPASSABLE) {
                    continue;
                }// TODO backtrack and update current path 
                double tentative_g = bestNodeBackwards.getG() + localCost;
                InvertedNode neighborNode;
                if (closedSetBackwards.containsKey(neighborIndex)) {
                    neighborNode = closedSetBackwards.get(neighborIndex);
                    if (tentative_g >= neighborNode.getG()) {
                        continue;
                    }
                }
                if (openSetBackwards.contains(neighborIndex)) {
                    neighborNode = openSetBackwards.get(neighborIndex);
                    if (tentative_g < neighborNode.getG()) {
                        neighborNode.setNext(fromDir, bestNodeBackwards, tentative_g);
                    }
                } else {
                    neighborNode = new InvertedNode(neighbor, fromDir, bestNodeBackwards, tentative_g, 
                            heuristic.getCost(neighbor, origin, unit));
                    openSetBackwards.add(neighborNode);
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
    
    class InvertedOpenSet {

        final Queue<InvertedNode> list;
        final Map<Integer, InvertedNode> map;

        InvertedOpenSet() {
            list = new PriorityQueue<>();
            map = new HashMap<>();
        }

        void add(InvertedNode node) {
            list.add(node);
            map.put(node.getIndex(), node);
        }

        InvertedNode poll() {
            return list.poll();
        }

        boolean contains(int index) {
            return map.containsKey(index);
        }

        InvertedNode get(int index) {
            return map.get(index);
        }

        boolean isEmpty() {
            return list.isEmpty();
        }
    }
}
