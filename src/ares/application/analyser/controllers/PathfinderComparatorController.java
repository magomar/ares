package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderComparatorInteractor;
import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.commands.ActionGroup;
import ares.application.shared.commands.CommandAction;
import ares.application.shared.commands.CommandGroup;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.PathfinderConfigurationViewer;
import ares.application.shared.boundaries.viewers.layerviewers.*;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.views.MessagesHandler;
import ares.application.shared.models.ScenarioModel;
import ares.platform.engine.algorithms.pathfinding.AStar;
import ares.platform.engine.algorithms.pathfinding.BeamSearch;
import ares.platform.engine.algorithms.pathfinding.ExtendedPath;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
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
public class PathfinderComparatorController implements ActionController {

    private static final Logger LOG = Logger.getLogger(PathfinderComparatorController.class.getName());
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private final PathfinderComparatorViewer comparatorView;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private final PathfinderConfiguration[] configuration;
    private Tile selectedTile;
    private Scenario scenario;
    private Unit testUnit;
    private final BoardViewer[] boardView;
    private InteractionMode interactionMode = InteractionMode.FREE;

    public PathfinderComparatorController(PathfinderComparatorInteractor interactor) {
        comparatorView = interactor.getPathfinderComparatorView();

        // create action groups
        Action[] viewActions = {viewGrid, viewUnits, zoomIn, zoomOut};
        CommandGroup group = AresCommandGroup.VIEW;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);


        // create combo box models, pass them to the view and add action listeners
        Pathfinder leftPathfinder = new AStar();
        Pathfinder rightPathfinder = new BeamSearch();
        Pathfinder[] pathfinders = {leftPathfinder, rightPathfinder};
        Heuristic[] heuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        CostFunction[] costFunctions = EstimatedCostFunctions.values();

        configuration = new PathfinderConfiguration[2];
        configuration[LEFT] = new PathfinderConfiguration(comparatorView.getLefConfigurationView(), pathfinders, heuristics, costFunctions).initialize(leftPathfinder);
        configuration[RIGHT] = new PathfinderConfiguration(comparatorView.getRightConfigurationView(), pathfinders, heuristics, costFunctions).initialize(rightPathfinder);

        boardView = new BoardViewer[2];
        boardView[LEFT] = comparatorView.getLeftBoardView();
        boardView[RIGHT] = comparatorView.getRightBoardView();

        // Adds various component listeners
        boardView[LEFT].addMouseListener(new BoardMouseListener(boardView[LEFT], configuration[LEFT]));
        boardView[RIGHT].addMouseListener(new BoardMouseListener(boardView[RIGHT], configuration[RIGHT]));
        boardView[LEFT].addMouseMotionListener(new BoardMouseMotionListener(boardView[LEFT], configuration[LEFT]));
        boardView[RIGHT].addMouseMotionListener(new BoardMouseMotionListener(boardView[RIGHT], configuration[RIGHT]));
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        testUnit = UnitFactory.createTestUnit(MovementType.MOTORIZED);
        ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
        initializeBoardView(boardView[LEFT], scenarioModel, GraphicsModel.INSTANCE.getActiveProfile());
        initializeBoardView(boardView[RIGHT], scenarioModel, GraphicsModel.INSTANCE.getActiveProfile());
    }

    private static void initializeBoardView(BoardViewer boardView, ScenarioModel scenarioModel, int profile) {
        boardView.setProfile(profile);
        ((TerrainLayerViewer) boardView.getLayerView(TerrainLayerViewer.NAME)).updateScenario(scenarioModel);
        ((GridLayerViewer) boardView.getLayerView(GridLayerViewer.NAME)).updateScenario(scenarioModel);
    }

    private void select(BoardViewer boardView, int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();
        if (GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            if (!tile.equals(selectedTile)) {
                selectedTile = tile;
                interactionMode = InteractionMode.UNIT_ORDERS;
                LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "New tile selected");
            }
            // TODO mark the selected tile in the board view, one option is to use SelectionLayerView
        }
    }

    private void deselect(BoardViewer boardView) {
        if (selectedTile != null) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Deselecting");
            selectedTile = null;
            interactionMode = InteractionMode.FREE;
            ((ArrowLayerViewer) boardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
            ((PathSearchLayerViewer) boardView.getLayerView(PathSearchLayerViewer.NAME)).updatePathSearch(null, null);
        }
    }

    private void command(BoardViewer boardView, Pathfinder pathfinder, int x, int y) {
        int profile = GraphicsModel.INSTANCE.getActiveProfile();
        if (interactionMode == InteractionMode.UNIT_ORDERS && GraphicsModel.INSTANCE.isWithinImageRange(x, y, profile)) {
            Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(x, y, profile);
            if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                return;
            }
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Destination selected, computing path...");
            Tile destination = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
            ExtendedPath path = pathfinder.getExtendedPath(selectedTile, destination, testUnit);
            if (path == null) {
                LOG.log(Level.WARNING, "No path found using {0}", pathfinder);
                return;
            }
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Path obtained {0}", path);
            ((PathSearchLayerViewer) boardView.getLayerView(PathSearchLayerViewer.NAME)).updatePathSearch(path.getOpenSetNodes(), path.getClosedSetNodes());
            ((ArrowLayerViewer) boardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(path);
        }
    }

    private class BoardMouseListener extends MouseAdapter {

        private final BoardViewer boardView;
        private final PathfinderConfiguration configuration;

        public BoardMouseListener(BoardViewer boardView, PathfinderConfiguration configuration) {
            this.boardView = boardView;
            this.configuration = configuration;
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, "Mouse clicked {0} from {1}", new Object[]{me.getButton(), me.getSource()});
            switch (me.getButton()) {
                case MouseEvent.BUTTON1:
                    select(boardView, me.getX(), me.getY());
                    break;
                case MouseEvent.BUTTON2:
                    deselect(boardView);
                    break;
                case MouseEvent.BUTTON3:
                    command(boardView, configuration.getPathfinder(), me.getX(), me.getY());
                    break;
            }
        }
    }

    private class BoardMouseMotionListener extends MouseMotionAdapter {

        private final BoardViewer boardView;
        private final PathfinderConfiguration configuration;

        public BoardMouseMotionListener(BoardViewer boardView, PathfinderConfiguration configuration) {
            this.boardView = boardView;
            this.configuration = configuration;
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            if (interactionMode == InteractionMode.UNIT_ORDERS) {
                Point pixel = new Point(me.getX(), me.getY());
                int profile = GraphicsModel.INSTANCE.getActiveProfile();
                if (GraphicsModel.INSTANCE.isWithinImageRange(pixel, profile)) {
                    Point tilePoint = GraphicsModel.INSTANCE.pixelToTileAccurate(pixel, profile);
                    if (!GraphicsModel.INSTANCE.validCoordinates(tilePoint.x, tilePoint.y)) {
                        return;
                    }
                    Tile tile = scenario.getBoard().getTile(tilePoint.x, tilePoint.y);
                    ExtendedPath path = configuration.getPathfinder().getExtendedPath(selectedTile, tile, testUnit);
                    if (path == null) {
                        return;
                    }
                    ((ArrowLayerViewer) boardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(path);
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
            initializeBoardView(boardView[LEFT], scenarioModel, nextProfile);
            initializeBoardView(boardView[RIGHT], scenarioModel, nextProfile);
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int previousProfile = GraphicsModel.INSTANCE.previousActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel(UserRole.GOD);
            initializeBoardView(boardView[LEFT], scenarioModel, previousProfile);
            initializeBoardView(boardView[RIGHT], scenarioModel, previousProfile);
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView[LEFT].switchLayerVisible(GridLayerViewer.NAME);
            boardView[RIGHT].switchLayerVisible(GridLayerViewer.NAME);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            boardView[LEFT].switchLayerVisible(UnitsLayerViewer.NAME);
            boardView[RIGHT].switchLayerVisible(UnitsLayerViewer.NAME);
        }
    }

    private class ChangePathfinderActionListener implements ActionListener {

        private final ComboBoxModel<Heuristic> heuristicComboModel;
        private final ComboBoxModel<CostFunction> costFunctionComboModel;

        public ChangePathfinderActionListener(ComboBoxModel<Heuristic> heuristicComboModel, ComboBoxModel<CostFunction> costFunctionComboModel) {
            this.heuristicComboModel = heuristicComboModel;
            this.costFunctionComboModel = costFunctionComboModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            JComboBox source = (JComboBox) e.getSource();
            Pathfinder pathfinder = (Pathfinder) source.getSelectedItem();
            pathfinder.setHeuristic((Heuristic) heuristicComboModel.getSelectedItem());
            pathfinder.setCostFunction((CostFunction) costFunctionComboModel.getSelectedItem());
        }
    }

    private class ChangeHeuristicActionListener implements ActionListener {

        private final ComboBoxModel<Pathfinder> pathfinderComboModel;

        public ChangeHeuristicActionListener(ComboBoxModel<Pathfinder> pathfinderComboModel) {
            this.pathfinderComboModel = pathfinderComboModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            JComboBox source = (JComboBox) e.getSource();
            Heuristic heuristic = (Heuristic) source.getSelectedItem();
            Pathfinder pathfinder = (Pathfinder) pathfinderComboModel.getSelectedItem();
            pathfinder.setHeuristic(heuristic);
        }
    }

    private class ChangeCostFunctionActionListener implements ActionListener {

        private final ComboBoxModel<Pathfinder> pathfinderComboModel;

        public ChangeCostFunctionActionListener(ComboBoxModel<Pathfinder> pathfinderComboModel) {
            this.pathfinderComboModel = pathfinderComboModel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.GAME_SYSTEM, e.toString());
            JComboBox source = (JComboBox) e.getSource();
            CostFunction costFunction = (CostFunction) source.getSelectedItem();
            Pathfinder pathfinder = (Pathfinder) pathfinderComboModel.getSelectedItem();
            pathfinder.setCostFunction(costFunction);
        }
    }

    private class PathfinderConfiguration {

        private final ComboBoxModel<Pathfinder> pathfinderComboModel;
        private final ComboBoxModel<Heuristic> heuristicComboModel;
        private final ComboBoxModel<CostFunction> costFunctionComboModel;
        private final PathfinderConfigurationViewer configurationView;

        PathfinderConfiguration(PathfinderConfigurationViewer configurationView, Pathfinder[] pathfinders, Heuristic[] heuristics, CostFunction[] costFunctions) {
            this.configurationView = configurationView;
            pathfinderComboModel = new DefaultComboBoxModel<>(pathfinders);
            heuristicComboModel = new DefaultComboBoxModel<>(heuristics);
            costFunctionComboModel = new DefaultComboBoxModel<>(costFunctions);
        }

        PathfinderConfiguration initialize(Pathfinder defaultPathfinder) {
            setPathfinder(defaultPathfinder);
            setHeuristic(defaultPathfinder.getHeuristic());
            setCostFunction(defaultPathfinder.getCostFunction());
            configurationView.setPathfinderComboModel(pathfinderComboModel,
                    new ChangePathfinderActionListener(heuristicComboModel, costFunctionComboModel));
            configurationView.setHeuristicComboModel(heuristicComboModel, new ChangeHeuristicActionListener(pathfinderComboModel));
            configurationView.setCostFunctionComboModel(costFunctionComboModel, new ChangeCostFunctionActionListener(pathfinderComboModel));
            return this;
        }

        void updateConfigurationView() {
        }

        void setPathfinder(Pathfinder pathfinder) {
            pathfinderComboModel.setSelectedItem(pathfinder);
        }

        void setHeuristic(Heuristic heuristic) {
            heuristicComboModel.setSelectedItem(heuristic);
        }

        void setCostFunction(CostFunction costFunction) {
            costFunctionComboModel.setSelectedItem(costFunction);
        }

        Pathfinder getPathfinder() {
            return (Pathfinder) pathfinderComboModel.getSelectedItem();
        }

        ComboBoxModel<Pathfinder> getPathfinderComboModel() {
            return pathfinderComboModel;
        }

        ComboBoxModel<Heuristic> getHeuristicComboModel() {
            return heuristicComboModel;
        }

        ComboBoxModel<CostFunction> getCostFunctionComboModel() {
            return costFunctionComboModel;
        }
    }

    private enum InteractionMode {

        FREE,
        UNIT_ORDERS,
        FORMATION_ORDERS
    }
}
