package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderComparatorInteractor;
import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.ViewCommands;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.models.ScenarioModel;
import ares.application.shared.gui.views.MessagesHandler;
import ares.platform.action.ActionGroup;
import ares.platform.action.CommandAction;
import ares.platform.action.CommandGroup;
import ares.platform.engine.algorithms.pathfinding.AStar;
import ares.platform.engine.algorithms.pathfinding.BeamSearch;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunctions;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.EnhancedMinimunDistance;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderComparatorController implements ActionController {

    private static final Logger LOG = Logger.getLogger(ChangePathfinderActionListener.class.getName());
    private Scenario scenario;
    private final PathfinderComparatorViewer comparatorView;
    private final Action viewGrid = new CommandAction(ViewCommands.VIEW_GRID, new ViewGridActionListener());
    private final Action viewUnits = new CommandAction(ViewCommands.VIEW_UNITS, new ViewUnitsActionListener());
    private final Action zoomIn = new CommandAction(ViewCommands.VIEW_ZOOM_IN, new ZoomInActionListener());
    private final Action zoomOut = new CommandAction(ViewCommands.VIEW_ZOOM_OUT, new ZoomOutActionListener());
    private final ActionGroup actions;
    private ComboBoxModel<Pathfinder> leftPathfinderComboModel;
    private ComboBoxModel<Pathfinder> rightPathfinderComboModel;
    private ComboBoxModel<Heuristic> leftHeuristicComboModel;
    private ComboBoxModel<Heuristic> rightHeuristicComboModel;
    private ComboBoxModel<CostFunction> leftCostFunctionComboModel;
    private ComboBoxModel<CostFunction> rightCostFunctionComboModel;

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
        leftPathfinderComboModel = new DefaultComboBoxModel<>(pathfinders);
        rightPathfinderComboModel = new DefaultComboBoxModel<>(new Pathfinder[]{new AStar(), rightPathfinder});
        
        Heuristic[] heuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        leftHeuristicComboModel = new DefaultComboBoxModel<>(heuristics);
        rightHeuristicComboModel = new DefaultComboBoxModel<>(heuristics);
        
        CostFunction[] costFunctions = CostFunctions.values();
        leftCostFunctionComboModel = new DefaultComboBoxModel<>(costFunctions);
        rightCostFunctionComboModel = new DefaultComboBoxModel<>(costFunctions);

        leftPathfinderComboModel.setSelectedItem(leftPathfinder);
        rightPathfinderComboModel.setSelectedItem(rightPathfinder);
        leftHeuristicComboModel.setSelectedItem(leftPathfinder.getHeuristic());
        rightHeuristicComboModel.setSelectedItem(rightPathfinder.getHeuristic());
        leftCostFunctionComboModel.setSelectedItem(leftPathfinder.getCostFunction());
        rightCostFunctionComboModel.setSelectedItem(rightPathfinder.getCostFunction());
        
                comparatorView.getLefConfigurationView().setPathfinderComboModel(leftPathfinderComboModel,
                new ChangePathfinderActionListener(leftHeuristicComboModel, leftCostFunctionComboModel));
        comparatorView.getRightConfigurationView().setPathfinderComboModel(rightPathfinderComboModel,
                new ChangePathfinderActionListener(rightHeuristicComboModel, rightCostFunctionComboModel));

        comparatorView.getLefConfigurationView().setHeuristicComboModel(leftHeuristicComboModel, new ChangeHeuristicActionListener(leftPathfinderComboModel));
        comparatorView.getRightConfigurationView().setHeuristicComboModel(rightHeuristicComboModel, new ChangeHeuristicActionListener(rightPathfinderComboModel));


        comparatorView.getLefConfigurationView().setCostFunctionComboModel(leftCostFunctionComboModel, new ChangeCostFunctionActionListener(leftPathfinderComboModel));
        comparatorView.getRightConfigurationView().setCostFunctionComboModel(rightCostFunctionComboModel, new ChangeCostFunctionActionListener(rightPathfinderComboModel));
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
        ScenarioModel scenarioModel = scenario.getModel();
        comparatorView.getLeftBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        comparatorView.getLeftBoardView().loadScenario(scenarioModel);
        comparatorView.getRightBoardView().setProfile(GraphicsModel.INSTANCE.getActiveProfile());
        comparatorView.getRightBoardView().loadScenario(scenarioModel);
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    public Pathfinder getLeftPathfinder() {
        return (Pathfinder) leftPathfinderComboModel.getSelectedItem();
    }

    public Pathfinder getRightPathfinder() {
        return (Pathfinder) rightPathfinderComboModel.getSelectedItem();
    }

    private class ZoomInActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int nextProfile = GraphicsModel.INSTANCE.nextActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel();
            comparatorView.getLeftBoardView().setProfile(nextProfile);
            comparatorView.getLeftBoardView().loadScenario(scenarioModel);
            comparatorView.getRightBoardView().setProfile(nextProfile);
            comparatorView.getRightBoardView().loadScenario(scenarioModel);
        }
    }

    private class ZoomOutActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int previousProfile = GraphicsModel.INSTANCE.previousActiveProfile();
            ScenarioModel scenarioModel = scenario.getModel();
            comparatorView.getLeftBoardView().setProfile(previousProfile);
            comparatorView.getLeftBoardView().loadScenario(scenarioModel);
            comparatorView.getRightBoardView().setProfile(previousProfile);
            comparatorView.getRightBoardView().loadScenario(scenarioModel);
        }
    }

    private class ViewGridActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            comparatorView.getLeftBoardView().switchLayerVisible(BoardViewer.GRID);
            comparatorView.getRightBoardView().switchLayerVisible(BoardViewer.GRID);
        }
    }

    private class ViewUnitsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            comparatorView.getLeftBoardView().switchLayerVisible(BoardViewer.UNITS);
            comparatorView.getRightBoardView().switchLayerVisible(BoardViewer.UNITS);
        }
    }

    private class ChangePathfinderActionListener implements ActionListener {

        private ComboBoxModel<Heuristic> heuristicComboModel;
        private ComboBoxModel<CostFunction> costFunctionComboModel;

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
}
