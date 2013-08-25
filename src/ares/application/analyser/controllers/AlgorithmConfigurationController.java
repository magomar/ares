package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.viewers.AlgorithmConfigurationViewer;
import ares.application.shared.gui.views.MessagesHandler;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmConfigurationController {
    private static final Logger LOG = Logger.getLogger(AlgorithmConfigurationController.class.getName());
    private final ComboBoxModel<Pathfinder> pathfinderComboModel;
    private final ComboBoxModel<Heuristic> heuristicComboModel;
    private final ComboBoxModel<CostFunction> costFunctionComboModel;
    private final AlgorithmConfigurationViewer configurationView;


    public AlgorithmConfigurationController(AlgorithmConfigurationViewer configurationView,Pathfinder[] pathfinders, Heuristic[] heuristics, CostFunction[] costFunctions, Pathfinder defaultPathfinder) {
        this.configurationView = configurationView;
        pathfinderComboModel = new DefaultComboBoxModel<>(pathfinders);
        heuristicComboModel = new DefaultComboBoxModel<>(heuristics);
        costFunctionComboModel = new DefaultComboBoxModel<>(costFunctions);
        pathfinderComboModel.setSelectedItem(defaultPathfinder);
        heuristicComboModel.setSelectedItem(defaultPathfinder.getHeuristic());
        costFunctionComboModel.setSelectedItem(defaultPathfinder.getCostFunction());
        this.configurationView.setPathfinderComboModel(pathfinderComboModel,
                new ChangePathfinderActionListener(heuristicComboModel, costFunctionComboModel));
        this.configurationView.setHeuristicComboModel(heuristicComboModel, new ChangeHeuristicActionListener(pathfinderComboModel));
        this.configurationView.setCostFunctionComboModel(costFunctionComboModel, new ChangeCostFunctionActionListener(pathfinderComboModel));
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
}
