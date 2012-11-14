package ares.application.models;

import ares.scenario.board.Direction;
import ares.scenario.board.Terrain;
import ares.scenario.board.Tile;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class TileModel  {

    private final Tile tile;

    public TileModel(Tile tile) {
        this.tile = tile;
    }
    
    public Map<Direction, Set<Terrain>> getSideTerrain() {
        return Collections.unmodifiableMap(tile.getSideTerrain());
    }
}
