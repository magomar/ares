package ares.application.models.board;

import ares.platform.model.KnowledgeMediatedModel;
import ares.scenario.board.Direction;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.board.Terrain;
import ares.scenario.board.TerrainFeatures;
import ares.scenario.board.Tile;
import java.awt.Point;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class TileModel extends KnowledgeMediatedModel {

    protected final Tile tile;

    public TileModel(Tile tile, KnowledgeLevel kLevel) {
        super(kLevel);
        this.tile = tile;
    }

    public Map<Direction, Set<Terrain>> getSideTerrain() {
        return Collections.unmodifiableMap(tile.getSideTerrain());
    }

    public Set<TerrainFeatures> getTerrainFeatures() {
        return Collections.unmodifiableSet(tile.getTerrainFeatures());
    }

    public Point getCoordinates() {
        return tile.getCoordinates();
    }
    
}
