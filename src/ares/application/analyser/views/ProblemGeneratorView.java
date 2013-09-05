package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.util.MathUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class ProblemGeneratorView extends AbstractView<JPanel> implements ProblemGeneratorViewer {
    private JTextField scenarioPath;
    private JTextField numProblems;
    private JButton changeScenarioPathButton;
    private JButton generateProblemsButton;
    private JList<String> scenarioList;
    private JList<String> selectedScenarioList;
    private JButton addScenarioButton;
    private JButton removeScenarioButton;

    @Override
    protected JPanel layout() {
        JLabel folderLabel = new JLabel("Scenario folder: ");
        JLabel numProblemsLabel = new JLabel("Num. problems: ");
        scenarioPath = new JTextField();
        numProblems = new JTextField();
        changeScenarioPathButton = ComponentFactory.button("...");
        generateProblemsButton =ComponentFactory.button("Generate");
        scenarioList = new JList<>();
        selectedScenarioList = new JList<>();
        addScenarioButton = ComponentFactory.button("Add");
        removeScenarioButton = ComponentFactory.button("Remove");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(folderLabel);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(scenarioPath, c);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(changeScenarioPathButton, c);
        c.gridx = 0;
        c.gridy = 1;
        panel.add(scenarioList, c);
        c.gridx = 1;
        c.gridy = 1;
        panel.add(selectedScenarioList, c);
        c.gridx = 0;
        c.gridy = 2;
        panel.add(addScenarioButton, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(removeScenarioButton, c);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(numProblemsLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(numProblems, c);
        c.gridx = 2;
        c.gridy = 3;
        panel.add(generateProblemsButton, c);
        return panel;
    }

    @Override
    public ListModel<String> getScenarioListModel() {
        return scenarioList.getModel();
    }

    @Override
    public ListModel<String> getSelectedScenarioListModel() {
        return selectedScenarioList.getModel();
    }

    @Override
    public ListSelectionModel getScenarioSelectionModel() {
        return scenarioList.getSelectionModel();
    }

    @Override
    public ListSelectionModel getSelectedScenarioSelectionModel() {
        return selectedScenarioList.getSelectionModel();
    }

    @Override
    public int getNumProblems() {
        String s = numProblems.getText();
        if (MathUtils.isInteger(s)) return Integer.parseInt(s);
        return 0;
    }

    @Override
    public void updateNumProblems(int numProblems) {
        this.numProblems.setText(Integer.toString(numProblems));
    }

    @Override
    public void updateScenarioPath(String path) {
        scenarioPath.setText(path);
    }

    @Override
    public void setScenarioListModel(ListModel<String> listModel) {
        scenarioList.setModel(listModel);
    }

    @Override
    public void setSelectedScenarioListModel(ListModel<String> listModel) {
        selectedScenarioList.setModel(listModel);
    }

    @Override
    public void setScenarioSelectionModel(ListSelectionModel selectionModel) {
        scenarioList.setSelectionModel(selectionModel);
    }

    @Override
    public void setSelectedScenarioSelectionModel(ListSelectionModel selectionModel) {
        selectedScenarioList.setSelectionModel(selectionModel);
    }

    @Override
    public void addAddScenarioActionListener(ActionListener actionListener) {
        addScenarioButton.addActionListener(actionListener);
    }

    @Override
    public void addRemoveScenarioActionListener(ActionListener actionListener) {
        removeScenarioButton.addActionListener(actionListener);
    }

    @Override
    public void addGenerateProblemsActionListener(ActionListener actionListener) {
        generateProblemsButton.addActionListener(actionListener);
    }

    @Override
    public void addChangeScenarioPathActionListener(ActionListener actionListener) {
        changeScenarioPathButton.addActionListener(actionListener);
    }

    @Override
    public String getScenarioPath() {
        return scenarioPath.getText();
    }

}
