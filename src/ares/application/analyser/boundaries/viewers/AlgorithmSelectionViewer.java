package ares.application.analyser.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public interface AlgorithmSelectionViewer extends View<JPanel> {
    AlgorithmConfigurationViewer getAlgorithmConfigurationView();

    ListModel<Pathfinder> getSelectedAlgorithmsListModel();

    ListSelectionModel getSelectedAlgorithmsListSelectionModel();

    void setSelectedAlgorithmsListModel(ListModel<Pathfinder> listModel);

    void setAlgorithmListSelectionMode(ListSelectionModel listSelectionModel);

    void addAddAlgorithmActionListener(ActionListener actionListener);

    void addRemoveAlgorithmActionListener(ActionListener actionListener);
}
