package ares.application.views;

import ares.application.gui.GraphicsModel;
import ares.application.boundaries.view.BoardViewer;
import ares.application.gui.ImageLayer;
import ares.application.gui.layers.ArrowLayer;
import ares.application.gui.layers.GridLayer;
import ares.application.gui.layers.SelectionLayer;
import ares.application.gui.layers.TerrainLayer;
import ares.application.gui.layers.PathSearchLayer;
import ares.application.gui.layers.UnitsLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.engine.algorithms.pathfinding.Path;
import ares.engine.command.tactical.TacticalMission;
import ares.platform.view.AbstractView;
import ares.scenario.board.Tile;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardView extends AbstractView<JScrollPane> implements BoardViewer {

    private JLayeredPane layeredPane;
    private TerrainLayer terrainLayer;
    private UnitsLayer unitsLayer;
    private GridLayer gridLayer;
    private SelectionLayer selectionLayer;
    private ArrowLayer arrowLayer;
    private PathSearchLayer pathSearchLayer;
    private final ImageLayer[] allLayers = {terrainLayer, gridLayer, selectionLayer, arrowLayer, pathSearchLayer, unitsLayer};
//    private final static int LOW = 1;
//    private final static int MID = 2;
//    private final static int HIGH = 3;
    /**
     * This matrix has all the layers sorted by depth and priority.
     *
     * First level (index 0) is the array with all the existing layers Next indexes are set in such way that the bigger
     * it is the "closer" is to the user. Layers are processed in index order (from left to right)
     */
    private final ImageLayer[][] imageLayersMatrix = {
        // Low level
        {terrainLayer},
        // Mid level
        {gridLayer, selectionLayer, arrowLayer, pathSearchLayer},
        // High level
        {unitsLayer}};
//    private final Thread[] layerThreads;

    public BoardView() {
//        layerThreads = new Thread[imageLayers.length];
    }

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = new JScrollPane();
        //Create layered pane to hold all the layers
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        // Create layers
        JViewport viewport = scrollPane.getViewport();
        terrainLayer = new TerrainLayer(viewport);
        unitsLayer = new UnitsLayer(viewport);
        gridLayer = new GridLayer(viewport);
        selectionLayer = new SelectionLayer(viewport);
        //Shares image with selection layer
        arrowLayer = new ArrowLayer(viewport, selectionLayer);
        pathSearchLayer = new PathSearchLayer(viewport, selectionLayer);

        // Add the last layer from each level to the layered pane
        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(arrowLayer, JLayeredPane.MODAL_LAYER);
        layeredPane.add(pathSearchLayer, JLayeredPane.MODAL_LAYER);
        layeredPane.add(unitsLayer, JLayeredPane.POPUP_LAYER);

        // Create and set up scroll pane as the content pane
        scrollPane.add(layeredPane);
        scrollPane.setViewportView(layeredPane);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(50);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(50);
        return scrollPane;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    @Override
    public void loadScenario(final ScenarioModel scenario) {
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getImageWidth(), GraphicsModel.INSTANCE.getImageHeight());
        layeredPane.setPreferredSize(imageSize);
//        layeredPane.setSize(imageSize);
        // Prepare each layer's thread
//        for (int depth = 0; depth < imageLayers.length; depth++) {
//            final int depthLevel = depth;
//            layerThreads[depth] = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int priority = 0; priority < imageLayers[depthLevel].length; priority++) {
//                        imageLayers[depthLevel][priority].initialize();
//                    }
//                }
//            });
//        }
        // Setup laye sizes and the start threads
        for (int depthLevel = 0; depthLevel < imageLayersMatrix.length; depthLevel++) {
            for (int priority = 0; priority < imageLayersMatrix[depthLevel].length; priority++) {
                imageLayersMatrix[depthLevel][priority].setPreferredSize(imageSize);
                imageLayersMatrix[depthLevel][priority].setSize(imageSize);
                imageLayersMatrix[depthLevel][priority].initialize();
            }
//            layerThreads[depthLevel].start();
        }
        // Render board: paint terrain and units
        terrainLayer.paintTerrain(scenario);
        unitsLayer.paintAllUnits(scenario);
        gridLayer.paintGrid(scenario);
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        unitsLayer.paintAllUnits(scenario);
    }

    @Override
    public void closeScenario() {
        for (int layer = 0; layer < imageLayersMatrix.length; layer++) {
            for (int subLayer = 0; subLayer < imageLayersMatrix[layer].length; subLayer++) {
                imageLayersMatrix[layer][subLayer].flush();
            }
        }
    }

    @Override
    public void addMouseListener(MouseListener listener) {
        contentPane.getViewport().getView().addMouseListener(listener);
    }

    @Override
    public void addMouseMotionListener(MouseMotionListener listener) {
        contentPane.getViewport().getView().addMouseMotionListener(listener);
    }

    @Override
    public void updateCurrentOrders(Path path) {
        arrowLayer.paintActiveOrders(path);
    }

    @Override
    public void updateLastOrders(Path path) {
        arrowLayer.paintLastOrders(path);
    }

    @Override
    public void updateLastPathSearch(Collection<Tile> openSet, Collection<Tile> closedSet) {
        pathSearchLayer.paintPathfindingProcess(openSet, closedSet);
    }

    @Override
    public void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation, ForceModel selectedForce) {
        selectionLayer.paintSelectedUnit(selectedUnit, selectedFormation);
        if (selectedUnit == null) {
            arrowLayer.paintPlannedOrders(null, null, null);
            return;
        }
        List<Path> forcePaths = new ArrayList<>();
        for (FormationModel fModel : selectedForce.getFormationModels()) {
            if (!fModel.equals(selectedFormation)) {
                for (UnitModel uModel : fModel.getUnitModels()) {
                    TacticalMission mission = uModel.getTacticalMission();
                    Path path = mission.getPath();
                    if (path != null) {
                        forcePaths.add(path.subPath(uModel.getLocation()));
                    }
                }
            }
        }
        List<Path> formationPaths = new ArrayList<>();
        for (UnitModel uModel : selectedFormation.getUnitModels()) {
            if (!uModel.equals(selectedUnit)) {
                TacticalMission mission = uModel.getTacticalMission();
                Path path = mission.getPath();
                if (path != null) {
                    formationPaths.add(path.subPath(uModel.getLocation()));
                }
            }
        }
        Path path = selectedUnit.getTacticalMission().getPath();
        Path selectedUnitPath = null;
        if (path != null) {
            selectedUnitPath = path.subPath(selectedUnit.getLocation());
        }
        arrowLayer.paintPlannedOrders(selectedUnitPath, formationPaths, forcePaths);
    }

    @Override
    public void updateUnitStack(TileModel tile) {
        unitsLayer.paintUnitStack(tile);
    }

    @Override
    public void centerViewOn(UnitModel selectedUnit, FormationModel selectedFormation) {
        JScrollBar verticalScrollBar = contentPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = contentPane.getHorizontalScrollBar();
        Point pos = GraphicsModel.INSTANCE.tileToPixel(selectedUnit.getLocation().getCoordinates());
        Dimension viewportSize = contentPane.getViewport().getSize();
        int boardWidth = terrainLayer.getGlobalImage().getWidth();
        int boardHeight = terrainLayer.getGlobalImage().getHeight();
        int x = pos.x - Math.min(viewportSize.width / 2, boardWidth - pos.x);
        int y = pos.y - Math.min(viewportSize.height / 2, boardHeight - pos.y);
        horizontalScrollBar.setValue(x);
        verticalScrollBar.setValue(y);
    }

    @Override
    public void setLayerVisible(int layer, boolean visible) {
        allLayers[layer].setVisible(visible);
    }

    @Override
    public boolean isLayerVisible(int layer) {
        return allLayers[layer].isVisible();
    }
}
