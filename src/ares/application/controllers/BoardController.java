package ares.application.controllers;

import ares.application.boundaries.view.ActionBarViewer;
import ares.engine.algorithms.pathfinding.PathFinder;
import ares.engine.algorithms.pathfinding.Path;
import ares.engine.algorithms.pathfinding.AStar;
import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.OOBViewer;
import ares.application.boundaries.view.InfoViewer;
import ares.application.commands.AresCommandGroup;
import ares.application.commands.ViewCommands;
import ares.application.gui.GraphicsModel;
import ares.application.interaction.InteractionMode;
import ares.application.models.board.TileModel;
import ares.application.models.forces.ForceModel;
import ares.application.models.forces.FormationModel;
import ares.application.models.forces.UnitModel;
import ares.application.views.MessagesHandler;
import ares.engine.RealTimeEngine;
import ares.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.platform.commands.CommandAction;
import ares.platform.commands.CommandGroup;
import ares.platform.controllers.AbstractSecondaryController;
import ares.platform.model.UserRole;
import ares.platform.view.ComponentFactory;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.awt.Point;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class BoardController extends AbstractSecondaryController implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(BoardController.class.getName());
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    private final OOBViewer oobView;
    private final ActionBarViewer<JMenu> menuView;
    private final ActionBarViewer<JButton> toolBarView;
    private final PathFinder pathFinder;
    private Tile selectedTile;
    private Unit selectedUnit;
    private InteractionMode interactionMode = InteractionMode.FREE;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());

    public BoardController(WeGoPlayerController mainController) {
        super(mainController);
        LOG.addHandler(mainController.getMessagesView().getHandler());
        this.boardView = mainController.getBoardView();
        this.infoView = mainController.getInfoView();
        this.oobView = mainController.getOobView();
        this.menuView = mainController.getMenuView();
        this.toolBarView = mainController.getToolBarView();

        pathFinder = new AStar(new MinimunDistance(DistanceCalculator.DELTA));
        //Add actions to the views
        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        menuView.addActionButton(ComponentFactory.menu(group.getName(), group.getText(), group.getMnemonic(), viewActions));
        for (Action action : viewActions) {
            toolBarView.addActionButton(ComponentFactory.button(action));
        }

        // Adds various component listeners
        boardView.addMouseListener(new BoardMouseListener());
        boardView.addMouseMotionListener(new BoardMouseMotionListener());
        oobView.addTreeSelectionListener(new OOBTreeSelectionListener());

        //Add change listeners to entities
        mainController.getEngine().addPropertyChangeListener(this);
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GraphicsModel.INSTANCE.nextActiveProfile();
            boardView.loadScenario(mainController.getScenario().getModel(mainController.getUserRole()));
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            GraphicsModel.INSTANCE.previousActiveProfile();
            boardView.loadScenario(mainController.getScenario().getModel(mainController.getUserRole()));
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.setLayerVisible(BoardViewer.GRID, !boardView.isLayerVisible(BoardViewer.GRID));
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView.setLayerVisible(BoardViewer.UNITS, !boardView.isLayerVisible(BoardViewer.UNITS));
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
                UserRole role = mainController.getUserRole();
                TileModel tileModel = selectedTile.getModel(role);
                infoView.updateTileInfo(tileModel);
                boardView.updateUnitStack(tileModel);
//                if (selectedUnit != null) {
                if (interactionMode == InteractionMode.UNIT_ORDERS) {
                    UnitModel unitModel = selectedUnit.getModel(role);
                    FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                    ForceModel forceModel = selectedUnit.getForce().getModel(role);
                    boardView.updateSelectedUnit(unitModel, formationModel, forceModel);
                    boardView.centerViewOn(unitModel, formationModel);
                    boardView.updateLastOrders(null);
                    boardView.updateCurrentOrders(null);
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
        UserRole role = mainController.getUserRole();
        TileModel tileModel = selectedTile.getModel(role);
        infoView.updateTileInfo(tileModel);
    }

    private void changeSelectedUnit(Unit unit) {
        selectedUnit = unit;
        UserRole role = mainController.getUserRole();
        TileModel tileModel = selectedTile.getModel(role);
        boardView.updateUnitStack(tileModel);
        if (selectedUnit != null) {
            UnitModel unitModel = selectedUnit.getModel(role);
            FormationModel formationModel = selectedUnit.getFormation().getModel(role);
            ForceModel forceModel = selectedUnit.getForce().getModel(role);
            boardView.updateCurrentOrders(null);
            boardView.updateSelectedUnit(unitModel, formationModel, forceModel);
            oobView.select(selectedUnit);
            infoView.updateUnitInfo(unitModel);
        }
    }

    private void select(int x, int y) {
        if (GraphicsModel.INSTANCE.isWithinImageRange(x, y)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = mainController.getScenario().getBoard().getTile(tilePoint.x, tilePoint.y);
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
            boardView.updateCurrentOrders(null);
            // the order matters here, updateSelectedUnit must be done after updateCurrentOrders, or updateCurrentOrders will have no effect
            boardView.updateSelectedUnit(null, null, null);
        }
    }

    private void command(int x, int y) {
        if (interactionMode == InteractionMode.UNIT_ORDERS && GraphicsModel.INSTANCE.isWithinImageRange(x, y)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Scenario scenario = mainController.getScenario();
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            TacticalMission mission = TacticalMissionType.OCCUPY.getNewTacticalMission(selectedUnit, tile, pathFinder);
            selectedUnit.setMission(mission);
            selectedUnit.schedule();
            boardView.updateLastOrders(mission.getPath());
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent me) {
            if (interactionMode == InteractionMode.UNIT_ORDERS && !mainController.getEngine().isRunning()) {
                Scenario scenario = mainController.getScenario();
                Point pixel = new Point(me.getX(), me.getY());
                if (GraphicsModel.INSTANCE.isWithinImageRange(pixel)) {
                    Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(pixel);
                    if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    Path path = pathFinder.getPath(selectedUnit.getLocation(), tile, selectedUnit);
                    if (path == null) {
                        return;
                    }
                    boardView.updateCurrentOrders(path);
                }
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            if (selectedUnit != null) {
                UserRole role = mainController.getUserRole();
                UnitModel unitModel = selectedUnit.getModel(role);
                FormationModel formationModel = selectedUnit.getFormation().getModel(role);
                ForceModel forceModel = selectedUnit.getForce().getModel(role);
//                boardView.updateCurrentOrders(null);
                boardView.updateSelectedUnit(unitModel, formationModel, forceModel);
                infoView.updateTileInfo(unitModel.getLocation());
                infoView.updateUnitInfo(unitModel);
            }
        }
    }
}
