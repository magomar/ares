package ares.application.views;

import ares.application.graphics.AresGraphicsModel;
import ares.application.boundaries.view.BoardViewer;
import ares.application.graphics.ImageLayer;
import ares.application.graphics.command.ArrowLayer;
import ares.application.graphics.board.GridLayer;
import ares.application.graphics.command.SelectionLayer;
import ares.application.graphics.board.TerrainLayer;
import ares.application.graphics.forces.UnitsLayer;
import ares.application.models.ScenarioModel;
import ares.application.models.board.*;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.engine.action.Action;
import ares.engine.action.actions.MoveAction;
import ares.engine.algorithms.routing.Path;
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
    private final static int ALL = 0;
    private final static int LOW = 1;
    private final static int MID = 2;
    private final static int HIGH = 3;
    /**
     * This matrix has all the layers sorted by depth and priority.
     *
     * First level (index 0) is the array with all the existing layers Next indexes are set in such way that the bigger
     * it is the "closer" is to the user. Layers are processed in index order (from left to right)
     */
    private final ImageLayer[][] imageLayers = {
        // All layers
        {terrainLayer, gridLayer, selectionLayer, arrowLayer, unitsLayer},
        // Low level
        {terrainLayer},
        // Mid level
        {gridLayer, selectionLayer, arrowLayer},
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
        gridLayer = new GridLayer();
        selectionLayer = new SelectionLayer();
        //Shares image with selection layer
        arrowLayer = new ArrowLayer(selectionLayer);

        // Add the last layer from each level to the layered pane
        layeredPane.add(terrainLayer, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(gridLayer, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(arrowLayer, JLayeredPane.PALETTE_LAYER);
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
        for (int index = 0; index < imageLayers[ALL].length; index++) {
            final int finalIndex = index;
            layerThreads[finalIndex] = new Thread(new Runnable() {
                @Override
                public void run() {
                    imageLayers[0][finalIndex].initialize();
                }
            });
        }
        // Initialize
        for (int index = 0; index < imageLayers[ALL].length; index++) {
            imageLayers[ALL][index].setPreferredSize(imageSize);
            imageLayers[ALL][index].setSize(imageSize);
            layerThreads[index].start();
        }
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
        for (ImageLayer ail : imageLayers[ALL]) {
            ail.flush();
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
