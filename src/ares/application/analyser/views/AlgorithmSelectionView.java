package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmSelectionView extends AbstractView<JPanel> implements AlgorithmSelectionViewer {
    private AlgorithmConfigurationView algorithmConfigurationView;
    private JList<Pathfinder> selectedAlgorithms;
    private JButton addButton;
    private JButton removeButton;

    @Override
    protected JPanel layout() {
        algorithmConfigurationView = new AlgorithmConfigurationView();
        selectedAlgorithms = new JList<>();
        addButton = ComponentFactory.button("Add");
        removeButton = ComponentFactory.button("Remove");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(algorithmConfigurationView.getContentPane(), c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(selectedAlgorithms, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(addButton, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(removeButton, c);
        return panel;
    }

    @Override
    public AlgorithmConfigurationView getAlgorithmConfigurationView() {
        return algorithmConfigurationView;
    }

    @Override
    public ListModel<Pathfinder> getSelectedAlgorithmsListModel() {
        return selectedAlgorithms.getModel();
    }

    @Override
    public ListSelectionModel getSelectedAlgorithmsListSelectionModel() {
        return selectedAlgorithms.getSelectionModel();
    }

    @Override
    public void setSelectedAlgorithmsListModel(ListModel<Pathfinder> listModel) {
        selectedAlgorithms.setModel(listModel);
    }

    @Override
    public void addAddAlgorithmActionListener(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    @Override
    public void addRemoveAlgorithmActionListener(ActionListener actionListener) {
        removeButton.addActionListener(actionListener);
    }

    @Override
    public void setAlgorithmListSelectionMode(ListSelectionModel listSelectionModel) {
        selectedAlgorithms.setSelectionModel(listSelectionModel);
    }

}
