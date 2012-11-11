package ares.engine.algorithms;
import ares.scenario.board.Tile;

/**
 *
 * @author Saúl Esteban
 */
public class Node {
    
    private Tile tile;
    private Node parent;
    private double g;
    private double f;
    
    public Node(Tile t) {
        tile = t;
    }
    
    public Node(Tile t, Node p) {
        tile = t;
        parent = p;
    }
    
    public void setTile(Tile t) {
        tile = t;
    }
    
    public void setParent(Node p) {
        parent = p;
    }
    
    public void setG(double newG) {
        g = newG;
    }
    
    public void setF(double newF) {
        f = newF;
    }
    
    public Tile getTile() {
        return tile;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public double getG() {
        return g;
    }
    
    public double getF() {
        return f;
    }
}
