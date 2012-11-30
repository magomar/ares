package ares.application.views;

import ares.application.boundaries.view.BoardViewer;
import ares.application.gui_components.layers.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
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

    private JLayeredPane layeredPane;
    private AbstractImageLayer terrainLayer;
    private AbstractImageLayer unitsLayer;
    private AbstractImageLayer gridLayer;
    private AbstractImageLayer[] imageLayers = {terrainLayer, gridLayer, unitsLayer};
    private AbstractImageLayer[] dynamicLayers ={unitsLayer};
    private Thread[] layerThreads = new Thread[imageLayers.length];

    @Override
    protected JScrollPane layout() {
        
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        terrainLayer = new TerrainLayer();
        unitsLayer = new UnitsLayer();
        unitsLayer.setOpaque(false);
        gridLayer = new GridLayer();
        gridLayer.setOpaque(false);
        

        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        JScrollPane contentPane = new JScrollPane();
        contentPane.add(layeredPane);
        contentPane.setViewportView(layeredPane);
        contentPane.setBackground(Color.BLACK);
        contentPane.setVisible(true);
        contentPane.setOpaque(true);
        return contentPane;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    @Override
    public void loadScenario(final ScenarioModel scenario) {
        Dimension imageSize = new Dimension(BoardGraphicsModel.imageWidth, BoardGraphicsModel.imageHeight);
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);        
        for(int i=0; i<imageLayers.length; i++){
            final int f = i;
            layerThreads[f] = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    imageLayers[f].initialize(scenario);
                }
            });
        }
        for(int i=0; i<imageLayers.length; i++){
            layerThreads[i].start();
            imageLayers[i].setPreferredSize(imageSize);
            imageLayers[i].setSize(imageSize);
        }
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        for(AbstractImageLayer layer : dynamicLayers){
            layer.updateGlobalImage(scenario);
        }
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

    public void updateArrowPath(Object[] findArrowPath) {
        //Object[0] origin coordinates
        //Object[1] direction array
    }
}
