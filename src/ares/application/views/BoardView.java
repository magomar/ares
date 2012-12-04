package ares.application.views;

import ares.application.boundaries.view.BoardViewer;
import ares.application.gui_components.layers.*;
import ares.application.models.ScenarioModel;
import ares.application.models.board.BoardGraphicsModel;
import ares.application.models.board.TileModel;
import ares.engine.algorithms.routing.Path;
import ares.platform.view.AbstractView;
import java.awt.*;
import java.awt.event.MouseListener;
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
    private AbstractImageLayer arrowLayer;
    
    /**
     * This matrix has all the layers sorted by depth and priority.
     *
     * First level (index 0) is the array with all the existing layers Next
     * indexes are set in such way that the bigger it is the "closer" is to the
     * user.
     * Layers are processed in index order (from left to right)
     */
    private final static int ALL = 0;
    private final static int LOW = 1;
    private final static int MID = 2;
    private final static int HIGH = 3;
    private final AbstractImageLayer[][] imageLayers = {
        // All layer names
        {terrainLayer, gridLayer, arrowLayer, unitsLayer},
        // Low level
        {terrainLayer},
        // Mid level
        {gridLayer, arrowLayer},
        // High level
        {unitsLayer}};
    private Thread[] layerThreads = new Thread[imageLayers[ALL].length];

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
        //Shares image with grid layer
        arrowLayer = new ArrowLayer(gridLayer);
        arrowLayer.setOpaque(false);
        

        // TODO why is imageLayers  here?
        // Add the last layer from each level to the layered pane
        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(arrowLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        JScrollPane contentPane = new JScrollPane();
        contentPane.add(layeredPane);
        contentPane.setViewportView(layeredPane);
        contentPane.setBackground(Color.BLACK);
        contentPane.setVisible(true);
        contentPane.setOpaque(true);
        contentPane.getVerticalScrollBar().setUnitIncrement(20);
        return contentPane;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    @Override
    public void loadScenario(final ScenarioModel scenario) {
        Dimension imageSize = new Dimension(BoardGraphicsModel.getImageWidth(), BoardGraphicsModel.getImageHeight());
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);
        // Prepare each layer's thread
        for (int index = 0; index < imageLayers[ALL].length; index++) {
            final int finalIndex = index;
            layerThreads[finalIndex] = new Thread(new Runnable() {
                @Override
                public void run() {
                    imageLayers[0][finalIndex].initialize(scenario);
                }
            });
        }
        // Initialize
        for (int index = 0; index < imageLayers[ALL].length; index++) {
            imageLayers[ALL][index].setPreferredSize(imageSize);
            imageLayers[ALL][index].setSize(imageSize);
            layerThreads[index].start();
        }
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        //Update all layers from the HIGH level
        for (int index = HIGH; index < imageLayers.length; index++) {
            for (AbstractImageLayer layer : imageLayers[index]) {
                layer.updateGlobalImage(scenario);
            }
        }
    }
    
    @Override
    public void closeScenario() {
        for(AbstractImageLayer ail : imageLayers[ALL])
            ail.flush();
        
    }

    @Override
    public void updateTile(TileModel tile) {
        unitsLayer.paintTile(tile);
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        contentPane.getViewport().getView().addMouseListener(listener);
    }

    @Override
    public void updateArrowPath(ScenarioModel s,Path path) {
        gridLayer.updateGlobalImage(s);
        ((ArrowLayer)arrowLayer).paintArrow(path);
    }
}
