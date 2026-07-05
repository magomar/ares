package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AlgorithmConfigurationInteractor;
import ares.application.analyser.boundaries.interactors.AlgorithmSelectionInteractor;
import ares.application.analyser.boundaries.viewers.AlgorithmConfigurationViewer;
import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmSelectionController implements AlgorithmConfigurationInteractor {
    private AlgorithmSelectionViewer algorithmSelectionView;
    private AlgorithmConfigurationController algorithmConfigurationController;

    public AlgorithmSelectionController(AlgorithmSelectionInteractor algorithmSelectionInteractor, Pathfinder[] pathfinders, Heuristic[] heuristics, CostFunction[] costFunctions, Pathfinder defaultPathfinder) {
        this.algorithmSelectionView = algorithmSelectionInteractor.getAlgorithmSelectionView();
        algorithmConfigurationController = new AlgorithmConfigurationController(this, pathfinders, heuristics, costFunctions, pathfinders[0]);
        algorithmSelectionView.setSelectedAlgorithmsListModel(new DefaultListModel<Pathfinder>());
        algorithmSelectionView.addAddAlgorithmActionListener(new AddAlgorithmActionListener());
        algorithmSelectionView.addRemoveAlgorithmActionListener(new RemoveAlgorithmActionListener());
        algorithmSelectionView.setAlgorithmListSelectionMode(new DefaultListSelectionModel());
    }

    @Override
    public AlgorithmConfigurationViewer getAlgorithmConfigurationView() {
        return algorithmSelectionView.getAlgorithmConfigurationView();
    }

    private class AddAlgorithmActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ListModel<Pathfinder> selectedAlgorithmsListModel = algorithmSelectionView.getSelectedAlgorithmsListModel();
            Pathfinder pathfinder = (Pathfinder) getAlgorithmConfigurationView().getPathfinderComboModel().getSelectedItem();
            ((DefaultListModel) selectedAlgorithmsListModel).addElement(pathfinder.copy());
        }
    }

    private class RemoveAlgorithmActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<Pathfinder> selectedAlgorithmsListModel = (DefaultListModel<Pathfinder>) algorithmSelectionView.getSelectedAlgorithmsListModel();
            DefaultListSelectionModel selectedAlgorithmsListSelectionModel = (DefaultListSelectionModel) algorithmSelectionView.getSelectedAlgorithmsListSelectionModel();
            for (int i = selectedAlgorithmsListSelectionModel.getMinSelectionIndex(); i < selectedAlgorithmsListSelectionModel.getMaxSelectionIndex() + 1; i++) {
                if (selectedAlgorithmsListSelectionModel.isSelectedIndex(i)) selectedAlgorithmsListModel.remove(i);
            }
        }
    }


}
