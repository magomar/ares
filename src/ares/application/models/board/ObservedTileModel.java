package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class ObservedTileModel extends TileModel {

    public ObservedTileModel(Tile tile, KnowledgeLevel kLevel) {
        super(tile, kLevel);
    }

    public UnitModel getTopUnit() {
        Unit unit = tile.getTopUnit();
        return unit.getModel(kLevel);
    }

    public int getNumUnits() {
        return tile.getNumStackedUnits();
    }
}
