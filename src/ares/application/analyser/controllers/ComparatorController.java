package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AlgorithmConfigurationInteractor;
import ares.application.analyser.boundaries.interactors.ComparatorInteractor;
import ares.application.analyser.boundaries.viewers.AlgorithmConfigurationViewer;
import ares.application.analyser.boundaries.viewers.ComparatorViewer;
import ares.application.analyser.boundaries.viewers.PathfindingLayerViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.layerviewers.ArrowLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.GridLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.TerrainLayerViewer;
import ares.application.shared.boundaries.viewers.layerviewers.UnitsLayerViewer;
import ares.application.shared.commands.*;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.platform.engine.algorithms.pathfinding.*;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.costfunctions.EstimatedCostFunctions;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.EnhancedMinimunDistance;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.engine.movement.MovementType;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;
import ares.platform.scenario.forces.UnitFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ComparatorController implements ActionController {

    private static final Logger LOG = Logger.getLogger(ComparatorController.class.getName());
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private final ComparatorViewer comparatorView;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final AlgorithmConfigurationController[] algorithmConfigurationControllers;
    private Tile selectedTile;
    private Unit testUnit;
    private Scenario scenario;
    private final BoardViewer[] boardViews;
    private final AlgorithmConfigurationViewer[] algorithmConfigurationViews;
    private InteractionMode interactionMode = InteractionMode.FREE;

    public ComparatorController(ComparatorInteractor interactor) {
        comparatorView = interactor.getPathfinderComparatorView();
        boardViews = new BoardViewer[2];
        boardViews[LEFT] = comparatorView.getLeftBoardView();
        boardViews[RIGHT] = comparatorView.getRightBoardView();
        algorithmConfigurationViews = new AlgorithmConfigurationViewer[2];
        algorithmConfigurationViews[LEFT] = comparatorView.getLefConfigurationView();
        algorithmConfigurationViews[RIGHT] = comparatorView.getRightConfigurationView();

        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);

        Pathfinder[] leftPathfinders = {new AStar(), new BeamSearch(), new BidirectionalSearch()};
        Pathfinder[] rightPathfinders = {new AStar(), new BeamSearch(), new BidirectionalSearch()};
        Heuristic[] allHeuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        CostFunction[] allCostFunctions = EstimatedCostFunctions.values();

        algorithmConfigurationControllers = new AlgorithmConfigurationController[2];
        algorithmConfigurationControllers[LEFT] = new AlgorithmConfigurationController(
                new AlgorithmConfigurationInteractor() {
                    @Override
                    public AlgorithmConfigurationViewer getAlgorithmConfigurationView() {
                        return comparatorView.getLefConfigurationView();
                    }
                }
                , leftPathfinders, allHeuristics, allCostFunctions, leftPathfinders[0]);
        algorithmConfigurationControllers[RIGHT] = new AlgorithmConfigurationController(
                new AlgorithmConfigurationInteractor() {
                    @Override
                    public AlgorithmConfigurationViewer getAlgorithmConfigurationView() {
                        return comparatorView.getRightConfigurationView();
                    }
                }
                , rightPathfinders, allHeuristics, allCostFunctions, rightPathfinders[1]);

        ComboBoxModel<MovementType> movementTypeComboModel = new DefaultComboBoxModel<>(MovementType.values());
        comparatorView.setMovementTypeComboModel(movementTypeComboModel);
        comparatorView.addMovementTypeActionListener(new ChangeMovementTypeActionListener());
        movementTypeComboModel.setSelectedItem(MovementType.MOTORIZED);

        ComboBoxModel<PathfindingLayerViewer.ShowCostType> showCostTypeComboBoxModel = new DefaultComboBoxModel<>(PathfindingLayerViewer.ShowCostType.values());
        comparatorView.setShowCostTypeComboModel(showCostTypeComboBoxModel);
        showCostTypeComboBoxModel.setSelectedItem(PathfindingLayerViewer.ShowCostType.SHOW_G_COST);

        JPanel statsPanel = comparatorView.getStatsPanel();
        ((JLabel) statsPanel.getComponent(LEFT)).setText("Nodes analysed: 0");
        ((JLabel) statsPanel.getComponent(RIGHT)).setText("Nodes analysed: 0");

        // Adds various component listeners
        boardViews[LEFT].addMouseListener(new BoardMouseListener(LEFT));
        boardViews[RIGHT].addMouseListener(new BoardMouseListener(RIGHT));
        boardViews[LEFT].addMouseMotionListener(new BoardMouseMotionListener(LEFT));
        boardViews[RIGHT].addMouseMotionListener(new BoardMouseMotionListener(RIGHT));
    }


    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
        initializeBoardView(boardViews[LEFT], scenarioModel, GraphicsModel.INSTANCE.getActiveProfile());
        initializeBoardView(boardViews[RIGHT], scenarioModel, GraphicsModel.INSTANCE.getActiveProfile());
    }

    private static void initializeBoardView(BoardViewer boardView, ScenarioModel scenarioModel, int profile) {
        boardView.setProfile(profile);
        ((TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME)).updateScenario(scenarioModel);
        ((GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME)).updateScenario(scenarioModel);
    }

    private void select(int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();
        if (GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTile(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            if (!tile.equals(selectedTile)) {
                selectedTile = tile;
                interactionMode = InteractionMode.UNIT_ORDERS;
                BoardViewer leftBoardView = this.boardViews[LEFT];
                BoardViewer rightBoardView = this.boardViews[RIGHT];
                ((PathfindingLayerViewer) leftBoardView.getLayerView(PathfindingLayerViewer.NAME)).updatePathSearch(null, null, 0);
                ((PathfindingLayerViewer) rightBoardView.getLayerView(PathfindingLayerViewer.NAME)).updatePathSearch(null, null, 0);
                ((ArrowLayerViewer) leftBoardView.getLayerView(ArrowLayerViewer.NAME)).updateLastOrders(null);
                ((ArrowLayerViewer) rightBoardView.getLayerView(ArrowLayerViewer.NAME)).updateLastOrders(null);
                ((ArrowLayerViewer) leftBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
                ((ArrowLayerViewer) rightBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
                ((JLabel) comparatorView.getStatsPanel().getComponent(LEFT)).setText("Nodes analysed: 0");
                ((JLabel) comparatorView.getStatsPanel().getComponent(RIGHT)).setText("Nodes analysed: 0");
                LOG.log(Level.INFO, "New tile selected: " + selectedTile.toString());
            }
        }
    }

    private void deselect() {
        if (selectedTile != null) {
            selectedTile = null;
            interactionMode = InteractionMode.FREE;
            ((ArrowLayerViewer) boardViews[LEFT].getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
            ((ArrowLayerViewer) boardViews[RIGHT].getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
        }
    }

    private void command(int side, int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();

        if (interactionMode == InteractionMode.UNIT_ORDERS && GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point coordinates = GraphicsModel.INSTANCE.pixelToTile(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(coordinates.x, coordinates.y)) {
                return;
            }
            Tile destination = scenario.getBoard().getTile(coordinates.x, coordinates.y);
            PathfindingLayerViewer.ShowCostType showCostType = (PathfindingLayerViewer.ShowCostType) comparatorView.getCostTypeComboModel().getSelectedItem();
            Pathfinder thisPathfinder = (Pathfinder) algorithmConfigurationViews[side].getPathfinderComboModel().getSelectedItem();
            ExtendedPath thisPath = thisPathfinder.getExtendedPath(selectedTile, destination, testUnit);
            if (thisPath == null) {
                LOG.log(Level.WARNING, "No path found using {0}", thisPathfinder);
                return;
            }
            LOG.log(Level.INFO, "Path obtained {0}", thisPath);
            BoardViewer thisBoardView = boardViews[side];
            ((ArrowLayerViewer) thisBoardView.getLayerView(ArrowLayerViewer.NAME)).updateLastOrders(thisPath);
            ((PathfindingLayerViewer) thisBoardView.getLayerView(PathfindingLayerViewer.NAME)).updatePathSearch(thisPath.getOpenSetNodes(), thisPath.getClosedSetNodes(), showCostType.getValue());
            ((ArrowLayerViewer) thisBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
            ((JLabel) comparatorView.getStatsPanel().getComponent(LEFT)).setText("Nodes visited: " + thisPath.getNumNodesVisited() +
                    " Path nodes: " + thisPath.size() + " Path cost: " + (int) thisPath.getLast().getG());

            int theOtherSide = 1 - side;
            Pathfinder theOtherPathfinder = (Pathfinder) algorithmConfigurationViews[theOtherSide].getPathfinderComboModel().getSelectedItem();
            ExtendedPath theOtherPath = theOtherPathfinder.getExtendedPath(selectedTile, destination, testUnit);
            if (theOtherPath == null) {
                LOG.log(Level.WARNING, "No path found using {0}", theOtherPathfinder);
                return;
            }
            LOG.log(Level.INFO, "Path obtained {0}", thisPath);
            BoardViewer theOtherBoardView = boardViews[theOtherSide];
            ((ArrowLayerViewer) theOtherBoardView.getLayerView(ArrowLayerViewer.NAME)).updateLastOrders(theOtherPath);
            ((PathfindingLayerViewer) theOtherBoardView.getLayerView(PathfindingLayerViewer.NAME)).updatePathSearch(theOtherPath.getOpenSetNodes(), theOtherPath.getClosedSetNodes(), showCostType.getValue());
            ((ArrowLayerViewer) theOtherBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
            ((JLabel) comparatorView.getStatsPanel().getComponent(RIGHT)).setText("Nodes visited: " + theOtherPath.getNumNodesVisited() +
                    " Path nodes: " + theOtherPath.size() + " Path cost: " + (int) theOtherPath.getLast().getG());
        }
    }


    private class BoardMouseListener extends MouseAdapter {

        private final int side;

        public BoardMouseListener(int side) {
            this.side = side;
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    select(me.getX(), me.getY());
                    break;
                case MouseEvent.BUTTON2:
                    deselect();
                    break;
                case MouseEvent.BUTTON3:
                    command(side, me.getX(), me.getY());
                    break;
            }
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        private final int side;

        public BoardMouseMotionListener(int side) {
            this.side = side;
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            if (interactionMode == InteractionMode.UNIT_ORDERS) {
                Point pixel = new Point(me.getX(), me.getY());
                int profile = GraphicsModel.INSTANCE.getActiveProfile();
                if (GraphicsModel.INSTANCE.isWithinImageRange(pixel, profile)) {
                    Point tilePoint = GraphicsModel.INSTANCE.pixelToTile(pixel, profile);
                    if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile destination = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    Pathfinder thisPathfinder = (Pathfinder) algorithmConfigurationViews[side].getPathfinderComboModel().getSelectedItem();
                    ExtendedPath thisPath = thisPathfinder.getExtendedPath(selectedTile, destination, testUnit);
                    if (thisPath == null) {
                        return;
                    }
                    BoardViewer thisBoardView = boardViews[side];
                    ((ArrowLayerViewer) thisBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(thisPath);

                    int theOtherSide = 1 - side;
                    Pathfinder theOtherPathfinder = (Pathfinder) algorithmConfigurationViews[theOtherSide].getPathfinderComboModel().getSelectedItem();
                    ExtendedPath theOtherPath = theOtherPathfinder.getExtendedPath(selectedTile, destination, testUnit);
                    if (theOtherPath == null) {
                        return;
                    }
                    BoardViewer theOtherBoardView = boardViews[theOtherSide];
                    ((ArrowLayerViewer) theOtherBoardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(theOtherPath);

                }
            }
        }
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int nextProfile = GraphicsModel.INSTANCE.nextActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
            initializeBoardView(boardViews[LEFT], scenarioModel, nextProfile);
            initializeBoardView(boardViews[RIGHT], scenarioModel, nextProfile);
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int previousProfile = GraphicsModel.INSTANCE.previousActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
            initializeBoardView(boardViews[LEFT], scenarioModel, previousProfile);
            initializeBoardView(boardViews[RIGHT], scenarioModel, previousProfile);
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardViews[LEFT].switchLayerVisible(GridLayerViewer.NAME);
            boardViews[RIGHT].switchLayerVisible(GridLayerViewer.NAME);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardViews[LEFT].switchLayerVisible(UnitsLayerViewer.NAME);
            boardViews[RIGHT].switchLayerVisible(UnitsLayerViewer.NAME);
        }
    }

    private class ChangeMovementTypeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            testUnit = UnitFactory.createTestUnit((MovementType) source.getSelectedItem());
        }
    }

    public enum InteractionMode {

        FREE,
        UNIT_ORDERS;
    }
}
