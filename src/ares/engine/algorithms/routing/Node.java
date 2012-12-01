package ares.engine.algorithms.routing;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import ares.scenario.board.Tile;
import java.util.Objects;

/**
 *
 * @author Saúl Esteban
 */
public class Node {
    
    private TileModel tile;
    // 
    private Node parent;
    // Cost from the start to this node
    private double g;
    // Estimated cost from this node to the goal
    private double f;
    
    public Node(TileModel t) {
        tile = t;
    }
    
    public Node(TileModel t, Node p) {
        tile = t;
        parent = p;
    }
    
    public void setTile(TileModel t) {
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
    
    public TileModel getTile() {
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
    
    // Tile map index
    public int getIndex(){
        return BoardGraphicsModel.tileMapIndex(tile.getCoordinates());
    }
    
    @Override
    public boolean equals(Object n){
        if(n instanceof Node) {
            return this.tile.getCoordinates() == ((Node)n).getTile().getCoordinates();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 53 * hash + Objects.hashCode(this.parent);
        return hash;
    }
}
