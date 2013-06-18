package ares.application.player.views;

import ares.application.shared.gui.views.AbstractBoardView;
import ares.application.shared.gui.views.layerviews.*;

import javax.swing.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class PlayerBoardView extends AbstractBoardView {

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        TerrainLayerView terrainLayer = (TerrainLayerView) new TerrainLayerView().setViewport(v);
        UnitsLayerView unitsLayer = (UnitsLayerView) new UnitsLayerView().setViewport(v);
        GridLayerView gridLayer = (GridLayerView) new GridLayerView().setViewport(v);
        SelectionLayerView selectionLayer = (SelectionLayerView) new SelectionLayerView().setViewport(v);
        ArrowLayerView arrowLayer = (ArrowLayerView) new ArrowLayerView().setViewport(v);
//                .setParenLayer(selectionLayer);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(selectionLayer).addLayerView(arrowLayer).addLayerView(unitsLayer);
        return scrollPane;
    }

}
