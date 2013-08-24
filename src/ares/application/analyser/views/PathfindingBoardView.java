package ares.application.analyser.views;

import ares.application.shared.gui.views.layerviews.AbstractBoardView;
import ares.application.shared.gui.views.layerviews.ArrowLayerView;
import ares.application.shared.gui.views.layerviews.GridLayerView;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;

import javax.swing.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class PathfindingBoardView extends AbstractBoardView {

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        TerrainLayerView terrainLayer = (TerrainLayerView) new TerrainLayerView().setViewport(v);
        GridLayerView gridLayer = (GridLayerView) new GridLayerView().setViewport(v);
        PathfindingLayerView pathSearchLayer = (PathfindingLayerView) new PathfindingLayerView().setViewport(v);
        ArrowLayerView arrowLayer = (ArrowLayerView) new ArrowLayerView().setViewport(v);
//        SearchCostsLayerView costsLayerView = (SearchCostsLayerView) new SearchCostsLayerView().setViewport(v);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(pathSearchLayer).addLayerView(arrowLayer);
        return scrollPane;
    }
}
