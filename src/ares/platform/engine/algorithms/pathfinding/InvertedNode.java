package ares.platform.engine.algorithms.pathfinding;

import ares.platform.scenario.board.Direction;
import ares.platform.scenario.board.Tile;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class InvertedNode extends Node {
    private InvertedNode invertedNext;

    public InvertedNode(Tile tile, Direction direction, InvertedNode next, double g, double h) {
        super(tile, direction, null, g, h);
        this.invertedNext = next;
    }
    
    public void setNext(Direction direction, InvertedNode next, double g) {
        this.direction = direction;
        this.invertedNext = next;
        this.g = g;
        f = g + h;
    }
    
    @Override
    public InvertedNode getNext() {
        return invertedNext;
    }
}
