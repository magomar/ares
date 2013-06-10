package ares.application.analyser.views;

import ares.application.shared.boundaries.viewers.layerviewers.ArrowLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.PathSearchLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
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
        TerrainLayerViewer terrainLayer = (TerrainLayerViewer) new TerrainLayerView().setViewport(v);
        GridLayerViewer gridLayer = (GridLayerViewer) new GridLayerView().setViewport(v);
        PathSearchLayerViewer pathSearchLayer = (PathSearchLayerViewer) new PathSearchLayerView().setViewport(v);
        ArrowLayerViewer arrowLayer = (ArrowLayerViewer) new ArrowLayerView().setViewport(v).setParenLayer(pathSearchLayer);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(pathSearchLayer).addLayerView(arrowLayer);
        return scrollPane;
    }
}
