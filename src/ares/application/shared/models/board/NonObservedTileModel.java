package ares.application.shared.models.board;

import ares.application.shared.models.forces.UnitModel;
import ares.platform.engine.knowledge.KnowledgeCategory;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.SurfaceUnit;

import java.util.ArrayList;
import java.util.Collection;

/**
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

    @Override
    public String getOwner() {
        return "UNKNOWN";
    }

    @Override
    public Collection<SurfaceUnit> getSurfaceUnits() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAlliedTerritory(String force) {
        return false;
    }

    @Override
    public boolean hasEnemies(String force) {
        return false;
    }
}
