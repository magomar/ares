package ares.application.views;


import ares.application.gui_components.*;
import ares.engine.realtime.RealTimeEngine;
import ares.platform.view.AbstractView;
import ares.scenario.Scenario;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardView extends AbstractView<JScrollPane> {

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
    public void modelPropertyChange(PropertyChangeEvent evt) {
//       Logger.getLogger(BoardView.class.getName()).log(Level.INFO, evt.toString());
        switch (evt.getPropertyName()) {
            case RealTimeEngine.SCENARIO_PROPERTY:
                if (evt.getNewValue() != null) {
                    Scenario scenario = (Scenario) evt.getNewValue();
                    terrainPanel.initialize(scenario);
                    unitsPanel.initialize(scenario);
                    Dimension imageSize = new Dimension(scenario.getBoardInfo().getImageWidth(), scenario.getBoardInfo().getImageHeight());
                    layers.setPreferredSize(imageSize);
                    layers.setSize(imageSize);
                    terrainPanel.setPreferredSize(imageSize);
                    terrainPanel.setSize(imageSize);
                    unitsPanel.setPreferredSize(imageSize);
                    unitsPanel.setSize(imageSize);
                } else {
                    terrainLayer.flushLayer();
                    gridLayer.flushLayer();
                    unitsLayer.flushLayer();
                    getContentPane().setVisible(false);
            }
                break;
            case RealTimeEngine.CLOCK_EVENT_PROPERTY:
                unitsPanel.updateUnits();
                break;
        }        
    }
}