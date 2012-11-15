package ares.application.models.board;

import ares.platform.model.AbstractModel;
import ares.scenario.board.Board;
import ares.scenario.board.InformationLevel;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardModel extends AbstractModel {

    private final Board board;
    private final TileModel[][] mapModel;

    public BoardModel(Board board, InformationLevel informationLevel) {
        super(informationLevel);
        Tile[][] tiles = board.getMap();
        mapModel = new TileModel[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                mapModel[i][j] = tiles[i][j].getCompleteModel();
            }
        }
        this.board = board;
    }

    public TileModel[][] getMapModel() {
        return mapModel;
    }

    public int getWidth() {
        return board.getWidth();
    }

    public int getHeight() {
        return board.getHeight();
    }
}
