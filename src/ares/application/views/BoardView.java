package ares.application.views;

import ares.application.boundaries.view.BoardViewer;
import ares.application.gui_components.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardView extends AbstractView<JScrollPane> implements BoardViewer {

    private JLayeredPane layers;
    private TerrainLayer terrainLayer;
    private UnitsLayer unitsLayer;
    private GridLayer gridLayer;

    @Override
    protected JScrollPane layout() {

        // TODO set black background
        layers = new JLayeredPane();
        terrainLayer = new TerrainLayer();
        unitsLayer = new UnitsLayer();
        gridLayer = new GridLayer();
        gridLayer.setOpaque(false);
        unitsLayer.setOpaque(false);

        layers.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layers.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layers.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        JScrollPane contentPane = new JScrollPane();
        contentPane.add(layers);
        contentPane.setViewportView(layers);
        contentPane.setVisible(true);
        return contentPane;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    @Override
    public void loadScenario(ScenarioModel scenario) {
        terrainLayer.initialize(scenario);
        unitsLayer.initialize(scenario);
        Dimension imageSize = new Dimension(scenario.getBoardGraphicsModel().getImageWidth(), scenario.getBoardGraphicsModel().getImageHeight());
        layers.setPreferredSize(imageSize);
        layers.setSize(imageSize);
        terrainLayer.setPreferredSize(imageSize);
        terrainLayer.setSize(imageSize);
        gridLayer.setPreferredSize(imageSize);
        gridLayer.setSize(imageSize);
        unitsLayer.setPreferredSize(imageSize);
        unitsLayer.setSize(imageSize);

    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        unitsLayer.updateGlobalImage(scenario);
    }

    @Override
    public void closeScenario() {
        terrainLayer.flushLayer();
        gridLayer.flushLayer();
        unitsLayer.flush();
        getContentPane().setVisible(false);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateTile(TileModel tile) {
        unitsLayer.paintByTile(tile);
    }
}
