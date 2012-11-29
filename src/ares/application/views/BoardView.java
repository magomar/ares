package ares.application.views;

import ares.application.boundaries.view.BoardViewer;
import ares.application.gui_components.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.TileModel;
import ares.platform.view.AbstractView;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardView extends AbstractView<JScrollPane> implements BoardViewer {

    private JLayeredPane layers;
    private AbstractImageLayer terrainLayer;
    private AbstractImageLayer unitsLayer;
    private GridLayer gridLayer;

    @Override
    protected JScrollPane layout() {

        // TODO set black background
        layers = new JLayeredPane();
        layers.setOpaque(true);
        layers.setBackground(Color.BLACK);
        terrainLayer = new TerrainLayer();
        unitsLayer = new UnitsLayer();
        unitsLayer.setOpaque(false);
        gridLayer = new GridLayer();
        gridLayer.setOpaque(false);
        

        layers.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layers.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layers.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        JScrollPane contentPane = new JScrollPane();
        contentPane.add(layers);
        contentPane.setViewportView(layers);
        contentPane.setBackground(Color.BLACK);
        contentPane.setVisible(true);
        contentPane.setOpaque(true);
        return contentPane;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    @Override
    public void loadScenario(ScenarioModel scenario) {
        terrainLayer.initialize(scenario);
        gridLayer.initialize(scenario);
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
        terrainLayer.flush();
        gridLayer.flush();
        unitsLayer.flush();
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateTile(TileModel tile) {
        unitsLayer.paintTile(tile);
    }
}
