package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.board.Tile;
import ares.scenario.board.UnitsStack;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class ObservedTileModel extends NonObservedTileModel {

    private UnitsStack stack;

    public ObservedTileModel(Tile tile, KnowledgeCategory kLevel) {
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

    @Override
    public String getDescription() {
        return tile.toStringMultiline();
    }
}
