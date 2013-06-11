package ares.application.player.controllers;

import ares.application.player.boundaries.interactors.MiniMapInteractor;
import ares.application.player.boundaries.interactors.PlayerBoardInteractor;
import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.platform.scenario.board.UnitsStack;
import ares.platform.scenario.board.Tile;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.AStar;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.board.TileModel;
import ares.application.shared.models.forces.ForceModel;
import ares.application.shared.models.forces.FormationModel;
import ares.application.shared.models.forces.UnitModel;
import ares.application.shared.gui.views.MessagesHandler;
import ares.platform.engine.RealTimeEngine;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.model.UserRole;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.boundaries.viewers.OOBViewer;
import ares.application.shared.boundaries.viewers.layerviewers.ArrowLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.SelectionLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.controllers.BoardController;
import ares.application.shared.controllers.MiniMapController;
import ares.application.shared.models.ScenarioModel;
import ares.platform.action.ActionGroup;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunctions;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.forces.Formation;
import ares.platform.scenario.forces.Unit;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class PlayerBoardController implements BoardInteractor, MiniMapInteractor, ActionController, PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(PlayerBoardController.class.getName());
    private final Pathfinder pathFinder;
    private final BoardViewer boardView;
    private final OOBViewer oobView;
    private final BoardViewer miniMapView;
    private final InfoViewer infoView;
//    private final TerrainLayerViewer terrainLayerView;
    private final UnitsLayerViewer unitsLayerView;
//    private final GridLayerViewer gridLayerView;
    private final ArrowLayerViewer arrowLayerView;
    private final SelectionLayerViewer selectionLayerView;
    private Tile selectedTile;
    private Unit selectedUnit;
    private Scenario scenario;
    private InteractionMode interactionMode = InteractionMode.FREE;
    private final BoardController boardController;
    private final MiniMapController miniMapController;
    private final PlayerBoardInteractor interactor;

    public PlayerBoardController(final PlayerBoardInteractor interactor, RealTimeEngine engine) {
        this.interactor = interactor;
        pathFinder = new AStar(MinimunDistance.create(DistanceCalculator.DELTA), CostFunctions.FASTEST);
        boardView = interactor.getBoardView();
        oobView = interactor.getOOBView();
        miniMapView = interactor.getMiniMapView();
        infoView = interactor.getInfoView();
//        terrainLayerView = (TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME);
        unitsLayerView = (UnitsLayerViewer) boardView.getLayerView(UnitsLayerViewer.NAME);
//        gridLayerView = (GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME);
        arrowLayerView = (ArrowLayerViewer) boardView.getLayerView(ArrowLayerViewer.NAME);
        selectionLayerView = (SelectionLayerViewer) boardView.getLayerView(SelectionLayerViewer.NAME);
        // create action groups

        boardController = new BoardController(this);
        miniMapController = new MiniMapController(this, this);

        // Adds various component listeners
        interactor.getBoardView().addMouseListener(new BoardMouseListener());
        interactor.getBoardView().addMouseMotionListener(new BoardMouseMotionListener());
        interactor.getOOBView().addTreeSelectionListener(new OOBTreeSelectionListener());

        //Add change listeners to entities
        engine.addPropertyChangeListener(this);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        ScenarioModel scenarioModel = scenario.getModel();
        oobView.loadScenario(scenarioModel);
        infoView.updateScenarioInfo(Clock.INSTANCE.getNow());
        boardController.setScenario(scenario, GraphicsModel.INSTANCE.getActiveProfile());
        miniMapController.setScenario(scenario, 0);
    }

    @Override
    public ActionGroup getActionGroup() {
        return boardController.getActionGroup();
    }

    @Override
    public void changeBoardViewport(JViewport viewport) {
        miniMapController.changeBoardViewport(viewport);
    }

    private class OOBTreeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent se) {
            JTree tree = (JTree) se.getSource();
            //Returns the last path element of the selection.
            //This method is useful only when the selection model allows a single selection.
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null) {
                return;
            }
            Object object = node.getUserObject();
            if (object instanceof Unit) {
                selectedUnit = (Unit) object;
            } else {
                Formation selectedFormation = (Formation) object;
                List<Unit> availableUnits = selectedFormation.getAvailableUnits();
                if (!availableUnits.isEmpty()) {
                    selectedUnit = selectedFormation.getAvailableUnits().get(0);
                }
            }
            if (selectedUnit != null) {
                selectedTile = selectedUnit.getLocation();
                interactionMode = InteractionMode.UNIT_ORDERS;
                LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New unit selected");
                UserRole role = scenario.getUserRole();
                TileModel tileModel = selectedTile.getModel(role);
                infoView.updateTileInfo(tileModel);
                unitsLayerView.updateUnitStack(tileModel);
//                if (selectedUnit != null) {
                if (interactionMode == InteractionMode.UNIT_ORDERS) {
                    UnitModel unitModel = selectedUnit.getModel(role);
                    FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                    ForceModel forceModel = selectedUnit.getForce().getModel(role);
                    updateSelectedUnit(unitModel, formationModel, forceModel);
                    boardView.centerViewOn(unitModel);
                    arrowLayerView.updateLastOrders(null);
                    arrowLayerView.updateCurrentOrders(null);
                }
            }
        }
    }

    private class BoardMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent me) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Mouse clicked {0}", me.getButton());
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    select(me.getX(), me.getY());
                    break;
                case MouseEvent.BUTTON2:
                    deselect();
                    break;
                case MouseEvent.BUTTON3:
                    command(me.getX(), me.getY());
                    break;
            }

        }
    }

    private void changeSelectedTile(Tile tile) {
        selectedTile = tile;
        UserRole role = scenario.getUserRole();
        TileModel tileModel = selectedTile.getModel(role);
        infoView.updateTileInfo(tileModel);
    }

    private void changeSelectedUnit(Unit unit) {
        selectedUnit = unit;
        UserRole role = scenario.getUserRole();
        TileModel tileModel = selectedTile.getModel(role);
        unitsLayerView.updateUnitStack(tileModel);
        if (selectedUnit != null) {
            UnitModel unitModel = selectedUnit.getModel(role);
            FormationModel formationModel = selectedUnit.getFormation().getModel(role);
            ForceModel forceModel = selectedUnit.getForce().getModel(role);
            arrowLayerView.updateCurrentOrders(null);
            updateSelectedUnit(unitModel, formationModel, forceModel);
            oobView.select(selectedUnit);
            infoView.updateUnitInfo(unitModel);
        }
    }

    private void select(int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();
        if (GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            UnitsStack stack = tile.getUnitsStack();

            if (!tile.equals(selectedTile)) {
                changeSelectedTile(tile);
                if (stack.isEmpty()) {
                    changeSelectedUnit(null);
                    interactionMode = InteractionMode.FREE;
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New tile selected");
                } else {
                    changeSelectedUnit(tile.getTopUnit());
                    interactionMode = InteractionMode.UNIT_ORDERS;
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New unit selected");
                }

            } else {
                if (stack.size() > 1) {
                    stack.next();
                    changeSelectedUnit(tile.getTopUnit());
                    LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Next unit in stack selected");
                }
            }
        }
    }

    private void deselect() {
        if (selectedTile != null) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Deselecting");
            selectedTile = null;
            selectedUnit = null;
            interactionMode = InteractionMode.FREE;
            infoView.clear();
            arrowLayerView.updateCurrentOrders(null);
            // the order matters here, updateSelectedUnit must be done after updateCurrentOrders, or updateCurrentOrders will have no effect
            updateSelectedUnit(null, null, null);
        }
    }

    private void command(int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();
        if (interactionMode == InteractionMode.UNIT_ORDERS && GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(selectedUnit, tile, pathFinder);
            selectedUnit.setMission(mission);
            selectedUnit.schedule();
            arrowLayerView.updateLastOrders(mission.getPath());
//            boardView.updateLastPathSearch(null, null);
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
//            if (interactionMode == InteractionMode.UNIT_ORDERS && !wegoController.getEngine().isRunning()) {
            if (interactionMode == InteractionMode.UNIT_ORDERS) {
                int profile = GraphicsModel.INSTANCE.getActiveProfile();
                Point pixel = new Point(me.getX(), me.getY());
                if (GraphicsModel.INSTANCE.isWithinImageRange(pixel, profile)) {
                    Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(pixel, profile);
                    if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    Path path = pathFinder.getPath(selectedUnit.getLocation(), tile, selectedUnit);
                    if (path == null) {
                        return;
                    }
                    arrowLayerView.updateCurrentOrders(path);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            unitsLayerView.updateScenario(scenario.getModel());
            infoView.updateScenarioInfo(Clock.INSTANCE.getNow());
            if (selectedUnit != null) {
                UserRole role = scenario.getUserRole();
                UnitModel unitModel = selectedUnit.getModel(role);
                FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                ForceModel forceModel = selectedUnit.getForce().getModel(role);
//                boardView.updateCurrentOrders(null);
                updateSelectedUnit(unitModel, formationModel, forceModel);
                infoView.updateTileInfo(unitModel.getLocation());
                infoView.updateUnitInfo(unitModel);
            }
        }
    }

    private void updateSelectedUnit(UnitModel selectedUnit, FormationModel selectedFormation, ForceModel selectedForce) {
        selectionLayerView.updateSelectedUnit(selectedUnit, selectedFormation);
        if (selectedUnit == null) {
            arrowLayerView.updatePlannedOrders(null, null, null);
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
        arrowLayerView.updatePlannedOrders(selectedUnitPath, formationPaths, forcePaths);
    }

    @Override
    public BoardViewer getBoardView() {
        return boardView;
    }

    @Override
    public Container getGUIContainer() {
        return interactor.getGUIContainer();
    }

    @Override
    public BoardViewer getMiniMapView() {
        return miniMapView;
    }

    private enum InteractionMode {

        FREE,
        UNIT_ORDERS,
        FORMATION_ORDERS
    }
}
