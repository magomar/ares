package ares.engine.algorithms;

import ares.model.board.Board;
import ares.model.board.Direction;
import ares.model.board.Tile;
import ares.model.forces.Unit;
import java.util.LinkedList;
import java.util.List;

/**
 * A* algorithm implementation adapted
 * for pathfinding in an hexagonal tiles map.
 *
 * @author SaÃºl Esteban
 */
public class PathFinder {

    private Node start;
    private Node goal;
    private Unit unit;
    private Path result;
    
    public PathFinder() {
        result = new Path();
    }
    
    /**
     * Find the shortest path to a goal starting from the start node.
     *
     * @return A built path of tiles from the initial point the goal,
     * or null if a path doesn't exist.
     */
    public Path findPath(Tile s, Tile go, Unit u) {
        if(s.equals(go)) {
            return null;
        }
        start = new Node(s);
        goal = new Node(go);
        unit = u;
        result = new Path();
        LinkedList<Node> open = new LinkedList<>();
        LinkedList<Node> closed = new LinkedList<>();
        Node current;
        
        start.setG(0);
        start.setF(start.getG() + estimateCost(start));
        open.add(start);
        
        while(!open.isEmpty()) {
            current = open.getFirst();
            for(Node node : open) {
                if(node.getF() < current.getF()) {
                    current = node;
                }
            }
            if(isGoal(current)) {
                open.clear();
                closed.clear();
                return buildPath(current);
            }
            open.remove(current);
            closed.add(current);
            for(Direction dir : current.getTile().getNeighbors().keySet()) {
                boolean isInOpen = false;
                boolean isInClosed = false;
                Node neighbour = new Node(current.getTile().getNeighbors().get(dir));
                
                for(Node node : closed) {
                    if(node.getTile().getX() == neighbour.getTile().getX() && node.getTile().getY() == neighbour.getTile().getY()) {
                        isInClosed = true;
                        break;
                    }
                }
                if(isInClosed) {
                    continue;
                }
                
                double tentative_g = current.getG() + current.getTile().getMoveCost(dir).getActualCost(unit, neighbour.getTile(), dir.getOpposite());
                
                for(Node node : open) {
                    if(node.getTile().getX() == neighbour.getTile().getX() && node.getTile().getY() == neighbour.getTile().getY()) {
                        neighbour = node;
                        isInOpen = true;
                    }
                }
                
                if(!isInOpen) {
                    neighbour.setParent(current);
                    neighbour.setG(tentative_g);
                    neighbour.setF(tentative_g + estimateCost(neighbour));
                    if(tentative_g < Integer.MAX_VALUE) {
                        open.add(neighbour);
                    }
                }
                else {
                    if(tentative_g < neighbour.getG()) {
                        neighbour.setParent(current);
                        neighbour.setG(tentative_g);
                        neighbour.setF(tentative_g + estimateCost(neighbour));
                    }
                }
            }
        }
        return null;
    }
    
    public Path findPath(Tile s, Tile go, Unit u, List<Tile> avoiding) {
        if(s.equals(go)) {
            return null;
        }
        start = new Node(s);
        goal = new Node(go);
        unit = u;
        result = new Path();
        LinkedList<Node> open = new LinkedList<>();
        LinkedList<Node> closed = new LinkedList<>();
        Node current;
        
        start.setG(0);
        start.setF(start.getG() + estimateCost(start));
        open.add(start);
        
        while(!open.isEmpty()) {
            current = open.getFirst();
            for(Node node : open) {
                if(node.getF() < current.getF()) {
                    current = node;
                }
            }
            if(isGoal(current)) {
                open.clear();
                closed.clear();
                return buildPath(current);
            }
            open.remove(current);
            closed.add(current);
            for(Direction dir : current.getTile().getNeighbors().keySet()) {
                boolean isInOpen = false;
                boolean isInClosed = false;
                Node neighbour = new Node(current.getTile().getNeighbors().get(dir));
                
                if(avoiding.contains(neighbour.getTile())) {
                    continue;
                }
                
                for(Node node : closed) {
                    if(node.getTile().getX() == neighbour.getTile().getX() && node.getTile().getY() == neighbour.getTile().getY()) {
                        isInClosed = true;
                        break;
                    }
                }
                if(isInClosed) {
                    continue;
                }
                
                double tentative_g = current.getG() + neighbour.getTile().getMoveCost(dir).getPrecomputedCost(unit.getMovement());//+ cost between current & neghbour 
                
                for(Node node : open) {
                    if(node.getTile().getX() == neighbour.getTile().getX() && node.getTile().getY() == neighbour.getTile().getY()) {
                        neighbour = node;
                        isInOpen = true;
                    }
                }
                
                if(!isInOpen) {
                    neighbour.setParent(current);
                    neighbour.setG(tentative_g);
                    neighbour.setF(tentative_g + estimateCost(neighbour));
                    open.add(neighbour);
                }
                else {
                    if(tentative_g < neighbour.getG()) {
                        neighbour.setParent(current);
                        neighbour.setG(tentative_g);
                        neighbour.setF(tentative_g + estimateCost(neighbour));
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Check if the current node is a goal for the problem.
     *
     * @param n The node to check.
     * @return true if it is a goal, false otherwise.
     */
    protected boolean isGoal(Node n) {
        return goal.getTile().equals(n.getTile());
    }
    
    /**
     * Calculates the absolute distance between a tile and the goal.
     * 
     * @param current The node wich tile is going to be used.
     * @return the distance between the tile and the goal.
     */
    protected int estimateCost(Node c) {
        return Board.getDistanceInTilesBetween(c.getTile(), goal.getTile());
    }

    /**
     * Fills result with the tiles that form the shortest path
     * 
     * @return the best path between the starting tile and the goal
     */
    protected Path buildPath(Node current) {
        if(!current.equals(start)) {
            result.setFirst(current.getTile());
            buildPath(current.getParent());
        }
        else {
        	result.setFirst(start.getTile());
        }
        return result;
    }
}