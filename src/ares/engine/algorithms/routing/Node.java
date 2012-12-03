package ares.engine.algorithms.routing;
import ares.application.models.board.*;
import ares.scenario.board.Direction;
import java.util.Objects;

/**
 *
 * @author Saúl Esteban
 */
public class Node {
    
    private TileModel tile;
    private Node parent;
    // Where this node comes from
    private Direction from;
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

    public TileModel getTile() {
        return tile;
    }

    public void setTile(TileModel tile) {
        this.tile = tile;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        sb.append(" From: "); sb.append(from.name());
        sb.append(" to (");sb.append(tile.getCoordinates().x); sb.append(",");sb.append(tile.getCoordinates().y); sb.append(") ");
        sb.append(" F:("); sb.append(f); sb.append(")");
        sb.append(" G:("); sb.append(g); sb.append(")");
        sb.append(" ]\n");
        return sb.toString();
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
