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
import ares.platform.engine.algorithms.pathfinding.costfunctions.TerrainCostFunctions;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ComparatorController implements ActionController {

    private static final Logger LOG = Logger.getLogger(ComparatorController.class.getName());
    private final ComparatorViewer comparatorView;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final AlgorithmConfigurationController[] algorithmConfigurationControllers;
    private final BoardViewer[] boardViews;
    private final AlgorithmConfigurationViewer[] algorithmConfigurationViews;
    private Tile selectedTile;
    private Unit testUnit;
    private Scenario scenario;
    private InteractionMode interactionMode = InteractionMode.FREE;

    private enum InteractionMode {
        FREE,
        UNIT_ORDERS;
    }

    public ComparatorController(ComparatorInteractor interactor) {
        comparatorView = interactor.getPathfinderComparatorView();

        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);

        ComboBoxModel<MovementType> movementTypeComboModel = new DefaultComboBoxModel<>(MovementType.values());
        comparatorView.setMovementTypeComboModel(movementTypeComboModel);
        comparatorView.addMovementTypeActionListener(new ChangeMovementTypeActionListener());
        movementTypeComboModel.setSelectedItem(MovementType.MOTORIZED);

        ComboBoxModel<PathfindingLayerViewer.ShowCostType> showCostTypeComboBoxModel = new DefaultComboBoxModel<>(PathfindingLayerViewer.ShowCostType.values());
        comparatorView.setShowCostTypeComboModel(showCostTypeComboBoxModel);
        showCostTypeComboBoxModel.setSelectedItem(PathfindingLayerViewer.ShowCostType.SHOW_G_COST);

        Heuristic[] allHeuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        CostFunction[] allCostFunctions = TerrainCostFunctions.values();
        Heuristic defaultHeuristic = allHeuristics[0];
        CostFunction defaultCostFunction = allCostFunctions[0];
        Pathfinder[][] pathfinders = new Pathfinder[2][];

        boardViews = new BoardViewer[2];
        algorithmConfigurationViews = new AlgorithmConfigurationViewer[2];
        algorithmConfigurationControllers = new AlgorithmConfigurationController[2];
        for (int side = 0; side < 2; side++) {
            boardViews[side] = comparatorView.getBoardView(side);
            algorithmConfigurationViews[side] = comparatorView.getConfigurationView(side);
            pathfinders[side] = new Pathfinder[]{new AStar(defaultHeuristic, defaultCostFunction), new BeamSearch(defaultHeuristic, defaultCostFunction), new BidirectionalSearch(defaultHeuristic, defaultCostFunction)};
            algorithmConfigurationControllers[side] = new AlgorithmConfigurationController(
                    new ComparatorAlgorithmConfigurationInteractor(side), pathfinders[side], allHeuristics, allCostFunctions, pathfinders[side][side]);
            ((JLabel) comparatorView.getStatsPanel().getComponent(side)).setText("Nodes analysed: 0");
            // Adds various component listeners
            boardViews[side].addMouseListener(new BoardMouseListener(side));
            boardViews[side].addMouseMotionListener(new BoardMouseMotionListener(side));
            boardViews[side].getContentPane().getViewport().addChangeListener(new ChangeViewportListener(side));
        }
    }

    private static void initializeBoardView(BoardViewer boardView, ScenarioModel scenarioModel, int profile) {
        boardView.setProfile(profile);
        ((TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME)).updateScenario(scenarioModel);
        ((GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME)).updateScenario(scenarioModel);
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
        for (int side = 0; side < 2; side++) {
            initializeBoardView(boardViews[side], scenarioModel, GraphicsModel.INSTANCE.getActiveProfile());
        }
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
                for (int side = 0; side < 2; side++) {
                    ((PathfindingLayerViewer) boardViews[side].getLayerView(PathfindingLayerViewer.NAME)).updatePathSearch(null, null, 0);
                    ((ArrowLayerViewer) boardViews[side].getLayerView(ArrowLayerViewer.NAME)).updateLastOrders(null);
                    ((ArrowLayerViewer) boardViews[side].getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
                    ((JLabel) comparatorView.getStatsPanel().getComponent(side)).setText("Nodes analysed: 0");
                }
                LOG.log(Level.INFO, "New tile selected: " + selectedTile.toString());
            }
        }
    }

    private void deselect() {
        if (selectedTile != null) {
            selectedTile = null;
            interactionMode = InteractionMode.FREE;
            for (int side = 0; side < 2; side++) {
                ((ArrowLayerViewer) boardViews[side].getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
            }
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
            ((JLabel) comparatorView.getStatsPanel().getComponent(side)).setText("Nodes visited: " + thisPath.getNumNodesVisited() +
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
            ((JLabel) comparatorView.getStatsPanel().getComponent(theOtherSide)).setText("Nodes visited: " + theOtherPath.getNumNodesVisited() +
                    " Path nodes: " + theOtherPath.size() + " Path cost: " + (int) theOtherPath.getLast().getG());
        }
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
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

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int nextProfile = GraphicsModel.INSTANCE.nextActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
            for (int side = 0; side < 2; side++) {
                initializeBoardView(boardViews[side], scenarioModel, nextProfile);
            }
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int previousProfile = GraphicsModel.INSTANCE.previousActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
            for (int side = 0; side < 2; side++) {
                initializeBoardView(boardViews[side], scenarioModel, previousProfile);
            }
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int side = 0; side < 2; side++) {
                boardViews[side].switchLayerVisible(GridLayerViewer.NAME);
            }
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int side = 0; side < 2; side++) {
                boardViews[side].switchLayerVisible(UnitsLayerViewer.NAME);
            }
        }
    }

    private class ChangeMovementTypeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            testUnit = UnitFactory.createTestUnit((MovementType) source.getSelectedItem());
        }
    }

    private class ChangeViewportListener implements ChangeListener {
        private int side;

        private ChangeViewportListener(int side) {
            this.side = side;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JViewport viewport = (JViewport) e.getSource();
            boardViews[1 - side].setViewPosition(viewport.getViewPosition());
        }
    }

    private class ComparatorAlgorithmConfigurationInteractor implements AlgorithmConfigurationInteractor {
        private int side;

        private ComparatorAlgorithmConfigurationInteractor(int side) {
            this.side = side;
        }

        @Override
        public AlgorithmConfigurationViewer getAlgorithmConfigurationView() {
            return algorithmConfigurationViews[side];
        }
    }

}
