package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.board.Tile;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class NonObservedTileModel extends TileModel {

    public NonObservedTileModel(Tile tile, KnowledgeCategory kLevel) {
        super(tile, kLevel);
    }

    @Override
    public UnitModel getTopUnit() {
        return null;
    }

    @Override
    public int getNumStackedUnits() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public String getDescription() {
        return tile.toStringMultiline();
    }
    
}
