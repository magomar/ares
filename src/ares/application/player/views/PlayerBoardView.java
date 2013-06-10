package ares.application.player.views;

import ares.application.shared.boundaries.viewers.layerviewers.ArrowLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.gui.views.AbstractBoardView;
import ares.application.shared.boundaries.viewers.layerviewers.SelectionLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.gui.views.layerviews.ArrowLayerView;
import ares.application.shared.gui.views.layerviews.GridLayerView;
import ares.application.shared.gui.views.layerviews.SelectionLayerView;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;
import ares.application.shared.gui.views.layerviews.UnitsLayerView;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class PlayerBoardView extends AbstractBoardView {

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        TerrainLayerViewer terrainLayer = (TerrainLayerViewer) new TerrainLayerView().setViewport(v);
        UnitsLayerViewer unitsLayer = (UnitsLayerViewer) new UnitsLayerView().setViewport(v);
        GridLayerViewer gridLayer = (GridLayerViewer) new GridLayerView().setViewport(v);
        SelectionLayerViewer selectionLayer = (SelectionLayerViewer) new SelectionLayerView().setViewport(v);
        ArrowLayerViewer arrowLayer = (ArrowLayerViewer) new ArrowLayerView().setViewport(v).setParenLayer(selectionLayer);

        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(gridLayer).addLayerView(selectionLayer).addLayerView(arrowLayer).addLayerView(unitsLayer);
        return scrollPane;
    }

}
