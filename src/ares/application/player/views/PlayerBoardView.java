package ares.application.player.views;

import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.views.layerviews.AbstractBoardView;
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
        ImageLayerViewer terrainLayer = new TerrainLayerView().setViewport(v);
        ImageLayerViewer unitsLayer = new UnitsLayerView().setViewport(v);
        ImageLayerViewer gridLayer =  new GridLayerView().setViewport(v);
        ImageLayerViewer placesLayer =  new PlacesLayerView().setViewport(v).setParenLayer(gridLayer);
        ImageLayerViewer selectionLayer = new SelectionLayerView().setViewport(v);
        ImageLayerViewer arrowLayer = new ArrowLayerView().setViewport(v).setParenLayer(selectionLayer);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(placesLayer).addLayerView(selectionLayer).addLayerView(arrowLayer).addLayerView(unitsLayer);
        return scrollPane;
    }

}
