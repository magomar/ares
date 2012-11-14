package ares.application.models;

import ares.scenario.board.Board;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardModel {
    private final Board board;

    public BoardModel(Board board) {
        this.board = board;
    }
    
    public TileModel[][] getTiles() {
        Tile[][] tiles = board.getMap();
        TileModel[][] mapModel = new TileModel[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                mapModel[i][j] = tiles[i][j].getModel(null);
            }
        }
        return mapModel;
    }
    
    public int getWidth() {
        return board.getWidth();
    }
    
    public int getHeight() {
        return board.getHeight();
    }
}
