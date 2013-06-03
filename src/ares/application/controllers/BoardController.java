package ares.application.controllers;

import ares.platform.scenario.board.UnitsStack;
import ares.platform.scenario.board.Tile;
import ares.platform.engine.algorithms.pathfinding.PathFinder;
import ares.platform.engine.algorithms.pathfinding.Path;
import ares.platform.engine.algorithms.pathfinding.AStar;
import ares.application.boundaries.view.BoardViewer;
import ares.application.commands.AresCommandGroup;
import ares.application.commands.ViewCommands;
import ares.application.gui.profiles.GraphicsModel;
import ares.application.models.board.TileModel;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.application.views.MessagesHandler;
import ares.platform.engine.RealTimeEngine;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.commands.CommandAction;
import ares.platform.commands.CommandGroup;
import ares.platform.model.UserRole;
import ares.application.boundaries.interactor.PlayerBoardInteractor;
import ares.application.models.ScenarioModel;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.forces.Formation;
import ares.platform.scenario.forces.Unit;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class BoardController implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(BoardController.class.getName());
    private final PlayerBoardInteractor coordinator;
    private final PathFinder pathFinder;
    private Tile selectedTile;
    private Unit selectedUnit;
    private Scenario scenario;
    private InteractionMode interactionMode = InteractionMode.FREE;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());

    public BoardController(PlayerBoardInteractor coordinator, RealTimeEngine engine) {
        this.coordinator = coordinator;
        pathFinder = new AStar(new MinimunDistance(DistanceCalculator.DELTA));
        //Add actions to the views
        Action[] actions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        coordinator.addActions(actions);
        coordinator.addMenu(group.getName(), group.getText(), group.getMnemonic(), actions);

        // Adds various component listeners
        coordinator.getBoardView().addMouseListener(new BoardMouseListener());
        coordinator.getBoardView().addMouseMotionListener(new BoardMouseMotionListener());
        coordinator.getOOBView().addTreeSelectionListener(new OOBTreeSelectionListener());

        //Add change listeners to entities
        engine.addPropertyChangeListener(this);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
                // obtain the scenario model with the active userRole
        ScenarioModel scenarioModel = scenario.getModel();
        coordinator.getMiniMapView().setProfile(0);
        coordinator.getMiniMapView().loadScenario(scenarioModel);
        coordinator.getBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        coordinator.getBoardView().loadScenario(scenarioModel);
        coordinator.getOOBView().loadScenario(scenarioModel);
        coordinator.getInfoView().updateScenarioInfo(Clock.INSTANCE.getNow());
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            coordinator.getBoardView().setProfile(GraphicsModel.INSTANCE.nextActiveProfile());
            coordinator.getBoardView().loadScenario(scenario.getModel());
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            coordinator.getBoardView().setProfile(GraphicsModel.INSTANCE.previousActiveProfile());
            coordinator.getBoardView().loadScenario(scenario.getModel());
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            coordinator.getBoardView().switchLayerVisible(BoardViewer.GRID);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            coordinator.getBoardView().switchLayerVisible(BoardViewer.UNITS);
        }
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
                coordinator.getInfoView().updateTileInfo(tileModel);
                coordinator.getBoardView().updateUnitStack(tileModel);
//                if (selectedUnit != null) {
                if (interactionMode == InteractionMode.UNIT_ORDERS) {
                    UnitModel unitModel = selectedUnit.getModel(role);
                    FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                    ForceModel forceModel = selectedUnit.getForce().getModel(role);
                    coordinator.getBoardView().updateSelectedUnit(unitModel, formationModel, forceModel);
                    coordinator.getBoardView().centerViewOn(unitModel, formationModel);
                    coordinator.getBoardView().updateLastOrders(null);
                    coordinator.getBoardView().updateCurrentOrders(null);
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
        coordinator.getInfoView().updateTileInfo(tileModel);
    }

    private void changeSelectedUnit(Unit unit) {
        selectedUnit = unit;
        UserRole role = scenario.getUserRole();
        TileModel tileModel = selectedTile.getModel(role);
        coordinator.getBoardView().updateUnitStack(tileModel);
        if (selectedUnit != null) {
            UnitModel unitModel = selectedUnit.getModel(role);
            FormationModel formationModel = selectedUnit.getFormation().getModel(role);
            ForceModel forceModel = selectedUnit.getForce().getModel(role);
            coordinator.getBoardView().updateCurrentOrders(null);
            coordinator.getBoardView().updateSelectedUnit(unitModel, formationModel, forceModel);
            coordinator.getOOBView().select(selectedUnit);
            coordinator.getInfoView().updateUnitInfo(unitModel);
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
            coordinator.getInfoView().clear();
            coordinator.getBoardView().updateCurrentOrders(null);
            // the order matters here, updateSelectedUnit must be done after updateCurrentOrders, or updateCurrentOrders will have no effect
            coordinator.getBoardView().updateSelectedUnit(null, null, null);
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
            coordinator.getBoardView().updateLastOrders(mission.getPath());
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
                    coordinator.getBoardView().updateCurrentOrders(path);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            coordinator.getBoardView().updateScenario(scenario.getModel());
            coordinator.getInfoView().updateScenarioInfo(Clock.INSTANCE.getNow());
            if (selectedUnit != null) {
                UserRole role = scenario.getUserRole();
                UnitModel unitModel = selectedUnit.getModel(role);
                FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                ForceModel forceModel = selectedUnit.getForce().getModel(role);
//                boardView.updateCurrentOrders(null);
                coordinator.getBoardView().updateSelectedUnit(unitModel, formationModel, forceModel);
                coordinator.getInfoView().updateTileInfo(unitModel.getLocation());
                coordinator.getInfoView().updateUnitInfo(unitModel);
            }
        }
    }

    private enum InteractionMode {

        FREE,
        UNIT_ORDERS,
        FORMATION_ORDERS
    }
}
