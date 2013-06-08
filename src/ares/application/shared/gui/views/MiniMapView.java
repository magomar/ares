package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;
import ares.application.shared.gui.views.layerviews.UnitsLayerView;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapView extends AbstractBoardView {

    private TerrainLayerViewer terrainLayerView;
    private UnitsLayerViewer unitsLayerView;

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        terrainLayerView = (TerrainLayerViewer) new TerrainLayerView().setViewport(v);
        unitsLayerView = (UnitsLayerViewer) new UnitsLayerView().setViewport(v);
        // Add independent layers pane
        addLayerView(terrainLayerView).addLayerView(unitsLayerView);
        return scrollPane;
    }
}
