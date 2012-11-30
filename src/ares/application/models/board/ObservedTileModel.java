package ares.application.models.board;

import ares.application.models.forces.UnitModel;
import ares.engine.knowledge.KnowledgeCategory;
import ares.scenario.board.Tile;
import ares.scenario.board.UnitsStack;
import java.util.NoSuchElementException;

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
        try {
            return stack.getPointOfInterest().getModel(kLevel);
        } catch (NoSuchElementException e) {
            return null;
        }
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
