package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.board.Tile;
import ares.scenario.forces.SurfaceUnit;
import java.util.*;

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

    /**
     *
     * @return
     */
    @Override
    public String getOwner() {
        return "UNKNOWN";
    }

    /**
     *
     * @return empty arraylist
     */
    @Override
    public Collection<SurfaceUnit> getSurfaceUnits() {
        return new ArrayList<>();
    }

    /**
     * Checks if tile owner is an ally of {
     *
     * @param force} It is assumed that if the tile is non observed the it mustn't be ally territory
     *
     * @param force force name to check
     * @return true if owner is ally
     */
    @Override
    public boolean isAlliedTerritory(String force) {
        return false;
    }

    /**
     * Checks if tile has enemies
     *
     * @param force
     * @return
     */
    @Override
    public boolean hasEnemies(String force) {
        return false;
    }
}
