package ares.application.analyser.boundaries.viewers;

import ares.application.shared.gui.views.View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ProblemGeneratorViewer extends View<JPanel> {
    ListModel<String> getScenarioListModel();

    ListModel<String> getSelectedScenarioListModel();

    ListSelectionModel getScenarioSelectionModel();

    ListSelectionModel getSelectedScenarioSelectionModel();

    int getNumProblems();

    void updateNumProblems(int numProblems);

    void updateScenarioPath(String path);

    void setScenarioListModel(ListModel<String> listModel);

    void setSelectedScenarioListModel(ListModel<String> listModel);

    void setScenarioSelectionModel(ListSelectionModel selectionModel);

    void setSelectedScenarioSelectionModel(ListSelectionModel selectionModel);

    void addAddScenarioActionListener(ActionListener actionListener);

    void addRemoveScenarioActionListener(ActionListener actionListener);

    void addGenerateProblemsActionListener(ActionListener actionListener);

    void addChangeScenarioPathActionListener(ActionListener actionListener);

    String getScenarioPath();
}
