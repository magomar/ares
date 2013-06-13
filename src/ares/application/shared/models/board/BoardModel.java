package ares.application.shared.models.board;

import ares.platform.model.RoleMediatedModel;
import ares.platform.model.UserRole;
import ares.platform.scenario.board.Board;
import ares.platform.scenario.board.Tile;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardModel extends RoleMediatedModel {

    private final Board board;
    private final TileModel[][] mapModel;

    public BoardModel(Board board, UserRole role) {
        super(role);
        this.board = board;
        Tile[][] tiles = board.getMap();
        mapModel = new TileModel[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                mapModel[i][j] = tiles[i][j].getModel(role);
            }
        }
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
