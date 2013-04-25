package ares.application.views;

import ares.application.gui.AresGraphicsModel;
import ares.application.boundaries.view.BoardViewer;
import ares.application.gui.ImageLayer;
import ares.application.gui.command.ArrowLayer;
import ares.application.gui.board.GridLayer;
import ares.application.gui.command.SelectionLayer;
import ares.application.gui.board.TerrainLayer;
import ares.application.gui.forces.UnitsLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.engine.action.Action;
import ares.engine.action.actions.MoveAction;
import ares.engine.algorithms.pathfinding.Path;
import ares.engine.command.tactical.TacticalMission;
import ares.platform.view.AbstractView;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class BoardView extends AbstractView<JScrollPane> implements BoardViewer {

    private JLayeredPane layeredPane;
    private TerrainLayer terrainLayer;
    private UnitsLayer unitsLayer;
    private GridLayer gridLayer;
    private ArrowLayer arrowLayer;
    private SelectionLayer selectionLayer;
//    private final static int ALL = 0;
    private final static int LOW = 1;
    private final static int MID = 2;
    private final static int HIGH = 3;
    /**
     * This matrix has all the layers sorted by depth and priority.
     *
     * First level (index 0) is the array with all the existing layers Next
     * indexes are set in such way that the bigger it is the "closer" is to the
     * user. Layers are processed in index order (from left to right)
     */
    private final ImageLayer[][] imageLayers = {
        // Low level
        {terrainLayer},
        // Mid level
        {gridLayer, selectionLayer, arrowLayer},
        // High level
        {unitsLayer}};
    private final Thread[] layerThreads;

    public BoardView() {
        layerThreads = new Thread[imageLayers.length];
    }

    @Override
    protected JScrollPane layout() {
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        terrainLayer = new TerrainLayer();
        unitsLayer = new UnitsLayer();
        gridLayer = new GridLayer();
        selectionLayer = new SelectionLayer();
        //Shares image with selection layer
        arrowLayer = new ArrowLayer(selectionLayer);

        // Add the last layer from each level to the layered pane
        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gridLayer, JLayeredPane.DEFAULT_LAYER + 10);
        layeredPane.add(arrowLayer, JLayeredPane.DEFAULT_LAYER + 20);
        layeredPane.add(unitsLayer, JLayeredPane.DRAG_LAYER);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.add(layeredPane);
        scrollPane.setViewportView(layeredPane);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setVisible(true);
        scrollPane.setOpaque(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        return scrollPane;
    }

    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }

    @Override
    public void loadScenario(final ScenarioModel scenario) {
        Dimension imageSize = new Dimension(AresGraphicsModel.getImageWidth(), AresGraphicsModel.getImageHeight());
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);
        // Prepare each layer's thread
        for (int depth = 0; depth < imageLayers.length; depth++) {
            final int depthLevel = depth;
            layerThreads[depth] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int priority = 0; priority < imageLayers[depthLevel].length; priority++) {
                        imageLayers[depthLevel][priority].initialize();
                    }
                }
            });
        }
        // Setup laye sizes and the start threads
        for (int depthLevel = 0; depthLevel < imageLayers.length; depthLevel++) {
            for (int priority = 0; priority < imageLayers[depthLevel].length; priority++) {
                imageLayers[depthLevel][priority].setPreferredSize(imageSize);
                imageLayers[depthLevel][priority].setSize(imageSize);
            }
            layerThreads[depthLevel].start();
        }
        // Render board: paint terrain and units
        terrainLayer.paintTerrain(scenario);
        unitsLayer.paintAllUnits(scenario);
    }

    @Override
    public void updateScenario(ScenarioModel scenario) {
        //Update all layers from the HIGH level
//        for (int index = HIGH; index < imageLayers.length; index++) {
//            for (AbstractImageLayer layer : imageLayers[index]) {
//                layer.update(scenario);
//            }
//        }
        unitsLayer.paintAllUnits(scenario);
    }

    @Override
    public void closeScenario() {
        for (int layer = 0; layer < imageLayers.length; layer++) {
            for (int subLayer = 0; subLayer < imageLayers[layer].length; subLayer++) {
                imageLayers[layer][subLayer].flush();
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
    public void updateArrowPath(ScenarioModel s, Path path) {
        arrowLayer.paintArrow(path);
    }

    @Override
    public void updateSelectedUnit(UnitModel selectedUnit, FormationModel formation, ScenarioModel scenario) {
        selectionLayer.paintSelectedUnit(selectedUnit, formation);
        if (selectedUnit == null) {
            arrowLayer.paintArrows(null);
            return;
        }
        Collection<Path> paths = new ArrayList<>();
        for (UnitModel unit : formation.getUnitModels()) {
            TacticalMission mission = unit.getTacticalMission();
            Action action = mission.getCurrentAction();
            if (action instanceof MoveAction) {
                MoveAction move = (MoveAction) action;
                paths.add(move.getPath());
            }
        }
        arrowLayer.paintArrows(paths);
    }

    @Override
    public void updateUnitStack(TileModel tile) {
        unitsLayer.paintUnitStack(tile);
    }
}
