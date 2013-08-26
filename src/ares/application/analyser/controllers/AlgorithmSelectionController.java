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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmSelectionController implements AlgorithmConfigurationInteractor {
    private static final Logger LOG = Logger.getLogger(AlgorithmSelectionController.class.getName());
    private AlgorithmSelectionViewer algorithmSelectionView;
    private AlgorithmConfigurationController algorithmConfigurationController;

    public AlgorithmSelectionController(AlgorithmSelectionInteractor algorithmSelectionInteractor, Pathfinder[] pathfinders, Heuristic[] heuristics, CostFunction[] costFunctions, Pathfinder defaultPathfinder) {
        this.algorithmSelectionView = algorithmSelectionInteractor.getAlgorithmSelectionView();
        algorithmConfigurationController = new AlgorithmConfigurationController(this, pathfinders, heuristics, costFunctions, pathfinders[0]);
        ListModel<Pathfinder> selectedAlgorithmsListModel = new DefaultListModel<>();
        algorithmSelectionView.setSelectedAlgorithmsListModel(selectedAlgorithmsListModel);
        algorithmSelectionView.addAddAlgorithmActionListener(new AddAlgorithmActionListener());
        algorithmSelectionView.addRemoveAlgorithmActionListener(new RemoveAlgorithmActionListener());
        ListSelectionModel listSelectionModel = new DefaultListSelectionModel();
        listSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        algorithmSelectionView.setAlgorithmListSelectionMode(listSelectionModel);
    }

    @Override
    public AlgorithmConfigurationViewer getAlgorithmConfigurationView() {
        return algorithmSelectionView.getAlgorithmConfigurationView();
    }

    private class AddAlgorithmActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            ListModel<Pathfinder> selectedAlgorithmsListModel = algorithmSelectionView.getSelectedAlgorithmsListModel();
            Pathfinder pathfinder = (Pathfinder) getAlgorithmConfigurationView().getPathfinderComboModel().getSelectedItem();
            ((DefaultListModel) selectedAlgorithmsListModel).addElement(pathfinder);
        }
    }

    private class RemoveAlgorithmActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(Level.INFO, e.toString());
            DefaultListModel<Pathfinder> selectedAlgorithmsListModel = (DefaultListModel<Pathfinder>) algorithmSelectionView.getSelectedAlgorithmsListModel();
            DefaultListSelectionModel listSelectionModel = (DefaultListSelectionModel) algorithmSelectionView.getSelectedAlgorithmsListSelectionModel();
            for(int i = listSelectionModel.getMinSelectionIndex(); i< listSelectionModel.getMaxSelectionIndex()+1;i++) {
                   if (listSelectionModel.isSelectedIndex(i)) selectedAlgorithmsListModel.remove(i);
            }
        }
    }
}
