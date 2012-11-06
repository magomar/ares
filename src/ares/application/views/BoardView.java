package ares.application.views;

import ares.application.gui_components.TerrainPanel;
import ares.application.gui_components.UnitsPanel;
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
    private TerrainPanel terrainPanel;
    private UnitsPanel unitsPanel;

    @Override
    protected JScrollPane layout() {
        layers = new JLayeredPane();
        terrainPanel = new TerrainPanel();
        unitsPanel = new UnitsPanel();
        unitsPanel.setOpaque(false);
        layers.add(terrainPanel, JLayeredPane.DEFAULT_LAYER);
        layers.add(unitsPanel, JLayeredPane.DRAG_LAYER);
        JScrollPane contentPane = new JScrollPane();
        contentPane.add(layers);
        contentPane.setViewportView(layers);
        contentPane.setVisible(false);
        return contentPane;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
//       Logger.getLogger(BoardView.class.getName()).log(Level.INFO, evt.toString());
        if (evt.getPropertyName().equals(RealTimeEngine.SCENARIO_PROPERTY)) {
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
                getContentPane().setVisible(true);
            } else {
                getContentPane().setVisible(false);
            }
        }
    }
}