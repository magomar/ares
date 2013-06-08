package ares.application.shared.gui.views;

import ares.application.shared.models.board.TileModel;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.application.shared.gui.views.layerviews.ArrowLayerView;
import ares.application.shared.gui.views.layerviews.GridLayerView;
import ares.application.shared.gui.views.layerviews.SelectionLayerView;
import ares.application.shared.gui.views.layerviews.TerrainLayerView;
import ares.application.shared.gui.views.layerviews.PathSearchLayer;
import ares.application.shared.gui.views.layerviews.UnitsLayerView;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.models.forces.ForceModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.algorithms.pathfinding.Node;
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

    private int profile;
    private JLayeredPane layeredPane;
    private TerrainLayerView terrainLayer;
    private UnitsLayerView unitsLayer;
    private GridLayerView gridLayer;
    private SelectionLayerView selectionLayer;
    private ArrowLayerView arrowLayer;
    private PathSearchLayer pathSearchLayer;
    private final ImageLayerViewer[] allLayers = {terrainLayer, gridLayer, selectionLayer, arrowLayer, pathSearchLayer, unitsLayer};

    @Override
    protected JScrollPane layout() {
        JScrollPane scrollPane = new JScrollPane();
        //Create layered pane to hold all the layers
        layeredPane = new JLayeredPane();
        layeredPane.setOpaque(true);
        layeredPane.setBackground(Color.BLACK);
        // Create layers
        JViewport viewport = scrollPane.getViewport();
        terrainLayer = new TerrainLayerView(viewport);
        unitsLayer = new UnitsLayerView(viewport);
        gridLayer = new GridLayerView(viewport);
        selectionLayer = new SelectionLayerView(viewport);
        //Shares image with selection layer
        arrowLayer = new ArrowLayerView(viewport, selectionLayer);
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
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayerViewer imageLayer = allLayers[i];
            imageLayer.setProfile(profile);
            imageLayer.initialize();
        }

        // Render board: paint terrain and units
        terrainLayer.paintTerrain(scenario);
        unitsLayer.paintAllUnits(scenario);
        gridLayer.update(scenario);
    }

        @Override
    public void forgetScenario() {
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayerViewer imageLayer = allLayers[i];
            imageLayer.flush();
        }
    }
    
    @Override
    public void updateScenario(ScenarioModel scenario) {
        unitsLayer.paintAllUnits(scenario);
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
    public void updateLastPathSearch(Collection<Node> openSet, Collection<Node> closedSet) {
        pathSearchLayer.updatePathSearch(openSet, closedSet);
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
        Point pos = GraphicsModel.INSTANCE.tileToPixel(selectedUnit.getLocation().getCoordinates(), profile);
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

    public ImageLayerViewer[] getAllLayers() {
        return allLayers;
    }

    @Override
    public void setProfile(int profile) {
        this.profile = profile;
        Dimension imageSize = new Dimension(GraphicsModel.INSTANCE.getBoardWidth(profile), GraphicsModel.INSTANCE.getBoardHeight(profile));
        layeredPane.setPreferredSize(imageSize);
        layeredPane.setSize(imageSize);
        for (int i = 0; i < allLayers.length; i++) {
            ImageLayerViewer imageLayer = allLayers[i];
            imageLayer.setProfile(profile);
            imageLayer.initialize();
        }
    }

    @Override
    public void switchLayerVisible(int layer) {
        ImageLayerViewer imageLayer = allLayers[layer];
        imageLayer.setVisible(!imageLayer.isVisible());
    }
}
