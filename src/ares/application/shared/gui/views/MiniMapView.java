package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;
import ares.application.shared.gui.views.layerviews.UnitsLayerView;
import ares.application.shared.models.ScenarioModel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class MiniMapView extends AbstractBoardView {

    private TerrainLayerViewer terrainLayer;
    private UnitsLayerViewer unitsLayer;

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = super.layout();
        JViewport v = scrollPane.getViewport();
        terrainLayer = (TerrainLayerViewer) new TerrainLayerView().setViewport(v);
        unitsLayer = (UnitsLayerViewer) new UnitsLayerView().setViewport(v);
        // Add independent layers pane
        addLayerView(terrainLayer).addLayerView(unitsLayer);
        return scrollPane;
    }
    
        @Override
    public void loadScenario(ScenarioModel scenarioModel) {
        // Render board: paint terrain and units
        terrainLayer.updateScenario(scenarioModel);
        unitsLayer.updateScenario(scenarioModel);
    }
}
