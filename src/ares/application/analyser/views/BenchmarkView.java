package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.engine.movement.MovementType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class BenchmarkView extends AbstractView<JPanel> implements BenchmarkViewer {
    private JTextArea results;
    private JSplitPane splitVert;
    private JComboBox<MovementType> movementType;
    private JButton executeBenchmarkButton;
    private AlgorithmSelectionView algorithmSelectionView;
    private ProblemGeneratorView problemGeneratorView;

    @Override
    protected JPanel layout() {
        algorithmSelectionView = new AlgorithmSelectionView();
        problemGeneratorView = new ProblemGeneratorView();
        JPanel benchmarkUI = new JPanel();
        movementType = new JComboBox<>();
        executeBenchmarkButton = ComponentFactory.button("Execute");
        benchmarkUI.add(movementType);
        benchmarkUI.add(executeBenchmarkButton);
        results = new JTextArea();
        JScrollPane resultsPanel = new JScrollPane(results);
        resultsPanel.add(results);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(algorithmSelectionView.getContentPane(), BorderLayout.CENTER);
        panel.add(problemGeneratorView.getContentPane(), BorderLayout.WEST);
        panel.add(benchmarkUI, BorderLayout.EAST);
        panel.add(resultsPanel, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public AlgorithmSelectionViewer getAlgorithmSelectionView() {
        return algorithmSelectionView;
    }

    @Override
    public ProblemGeneratorViewer getProblemGeneratorView() {
        return problemGeneratorView;
    }

    @Override
    public MovementType getMovementType() {
        return (MovementType) movementType.getSelectedItem();
    }

    @Override
    public void setMovementTypeComboBoxModel(ComboBoxModel<MovementType> comboBoxModel) {
        movementType.setModel(comboBoxModel);
    }

    @Override
    public void addMovementTypeActionListener(ActionListener actionListener) {
        movementType.addActionListener(actionListener);
    }

    @Override
    public void addExecuteBenchmarkActionListener(ActionListener actionListener) {
        executeBenchmarkButton.addActionListener(actionListener);
    }

    @Override
    public void log(String text) {
        this.results.append(text);
    }
}
