package ares.application.analyser.views;

import ares.application.shared.gui.views.AbstractBoardView;
import ares.application.shared.gui.views.layerviews.ArrowLayerView;
import ares.application.shared.gui.views.layerviews.GridLayerView;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class PathSearchBoardView extends AbstractBoardView {

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        TerrainLayerView terrainLayer = (TerrainLayerView) new TerrainLayerView().setViewport(v);
        GridLayerView gridLayer = (GridLayerView) new GridLayerView().setViewport(v);
        PathSearchLayerView pathSearchLayer = (PathSearchLayerView) new PathSearchLayerView().setViewport(v);
        ArrowLayerView arrowLayer = (ArrowLayerView) new ArrowLayerView().setViewport(v).setParenLayer(pathSearchLayer);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(pathSearchLayer).addLayerView(arrowLayer);
        return scrollPane;
    }
}
