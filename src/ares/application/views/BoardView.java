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
public class BoardView extends AbstractView<JPanel> implements BoardViewer {

    private JLayeredPane layerPane;
    private AbstractImageLayer terrainLayer;
    private AbstractImageLayer unitsLayer;
    private AbstractImageLayer gridLayer;
    private AbstractImageLayer arrowLayer;
    private AbstractImageLayer[] imageLayers = {terrainLayer, unitsLayer, gridLayer, arrowLayer};
    private AbstractImageLayer[] dynamicLayers = {unitsLayer, arrowLayer};

    @Override
    protected JPanel layout() {

        BorderLayout bLayout = new BorderLayout();
        JPanel contentPane = new JPanel(bLayout);
        layerPane = new JLayeredPane();
        layerPane.setOpaque(true);
        layerPane.setBackground(Color.BLACK);
        terrainLayer = new TerrainLayer();
        unitsLayer = new UnitsLayer();
        unitsLayer.setOpaque(false);
        gridLayer = new GridLayer();
        gridLayer.setOpaque(false);
        arrowLayer = new ArrowLayer(gridLayer);

        layerPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layerPane.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layerPane.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        
        contentPane.add(layerPane, BorderLayout.NORTH);
        contentPane.setBackground(Color.BLACK);
        contentPane.setVisible(true);
        contentPane.setOpaque(true);
        return contentPane;
    }

    public JLayeredPane getLayers() {
        return layerPane;
    }

    @Override
    public void loadScenario(ScenarioModel scenario) {
        
        Dimension imageSize = new Dimension(scenario.getBoardGraphicsModel().getImageWidth(), scenario.getBoardGraphicsModel().getImageHeight());
        layerPane.setPreferredSize(imageSize);
        layerPane.setSize(imageSize);
        
        for(AbstractImageLayer img : imageLayers){
            img.setPreferredSize(imageSize);
            img.setSize(imageSize);
            img.initialize(scenario);
        }
        

    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        unitsLayer.updateGlobalImage(scenario);
    }

    @Override
    public void closeScenario() {
        for(AbstractImageLayer img : imageLayers) img.flush();
        getContentPane().setVisible(false);
    }

    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals("ViewportChanged")){
            for(AbstractImageLayer img : imageLayers){
                img.paintViewportChanges((Integer)evt.getOldValue(),(String)evt.getNewValue());
            }
        }
    }

    @Override
    public void updateTile(TileModel tile) {
        for(AbstractImageLayer img : dynamicLayers) img.paintTileInViewport(tile);
    }
}
