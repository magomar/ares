package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.scenario.board.KnowledgeLevel;
import ares.scenario.board.Tile;
import ares.scenario.board.UnitsStack;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class ObservedTileModel extends NonObservedTileModel {

    private UnitsStack stack;

    public ObservedTileModel(Tile tile, KnowledgeLevel kLevel) {
        super(tile, kLevel);
        stack = tile.getUnitsStack();
    }

    @Override
    public UnitModel getTopUnit() {
        return stack.getPointOfInterest().getModel(kLevel);
    }

    @Override
    public int getNumStackedUnits() {
        return stack.size();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
