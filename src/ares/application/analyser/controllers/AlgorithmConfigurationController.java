package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AlgorithmConfigurationInteractor;
import ares.application.analyser.boundaries.viewers.AlgorithmConfigurationViewer;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmConfigurationController {
    private final AlgorithmConfigurationInteractor interactor;


    public AlgorithmConfigurationController(AlgorithmConfigurationInteractor interactor, Pathfinder[] pathfinders, Heuristic[] heuristics, CostFunction[] costFunctions, Pathfinder defaultPathfinder) {
        this.interactor = interactor;
        AlgorithmConfigurationViewer configurationView = interactor.getAlgorithmConfigurationView();
        ComboBoxModel<Pathfinder> pathfinderComboModel = new DefaultComboBoxModel<>(pathfinders);
        ComboBoxModel<Heuristic> heuristicComboModel = new DefaultComboBoxModel<>(heuristics);
        ComboBoxModel<CostFunction> costFunctionComboModel = new DefaultComboBoxModel<>(costFunctions);
        pathfinderComboModel.setSelectedItem(defaultPathfinder);
        heuristicComboModel.setSelectedItem(defaultPathfinder.getHeuristic());
        costFunctionComboModel.setSelectedItem(defaultPathfinder.getCostFunction());
        configurationView.setPathfinderComboModel(pathfinderComboModel, new ChangePathfinderActionListener());
        configurationView.setHeuristicComboModel(heuristicComboModel, new ChangeHeuristicActionListener());
        configurationView.setCostFunctionComboModel(costFunctionComboModel, new ChangeCostFunctionActionListener());
    }

    private class ChangePathfinderActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            Pathfinder pathfinder = (Pathfinder) source.getSelectedItem();
            pathfinder.setHeuristic((Heuristic) interactor.getAlgorithmConfigurationView().getHeuristicComboModel().getSelectedItem());
            pathfinder.setCostFunction((CostFunction) interactor.getAlgorithmConfigurationView().getCostFunctionComboModel().getSelectedItem());
        }
    }

    private class ChangeHeuristicActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            Heuristic heuristic = (Heuristic) source.getSelectedItem();
            Pathfinder pathfinder = (Pathfinder) interactor.getAlgorithmConfigurationView().getPathfinderComboModel().getSelectedItem();
            pathfinder.setHeuristic(heuristic);
        }
    }

    private class ChangeCostFunctionActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            CostFunction costFunction = (CostFunction) source.getSelectedItem();
            Pathfinder pathfinder = (Pathfinder) interactor.getAlgorithmConfigurationView().getPathfinderComboModel().getSelectedItem();
            pathfinder.setCostFunction(costFunction);
        }
    }
}
