package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.engine.movement.MovementType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class BenchmarkView extends AbstractView<JPanel> implements BenchmarkViewer {
    private JPanel resultsPanel;
    private JSplitPane splitVert;
    private JList<MovementType> movementTypeList;
    private java.util.List<MovementType> movementTypes;
    private AlgorithmSelectionView algorithmSelectionView;
    private ProblemGeneratorView problemGeneratorView;
    
    @Override
    protected JPanel layout() {
        algorithmSelectionView = new AlgorithmSelectionView();
//        problemGeneratorView = new ProblemGeneratorView();

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        resultsPanel = new JPanel();
        panel.add(algorithmSelectionView.getContentPane(), BorderLayout.NORTH);
//        panel.add(problemGeneratorView.getContentPane(), BorderLayout.CENTER);
        panel.add(resultsPanel, BorderLayout.SOUTH);
        return panel;
    }

    @Override
    public void setMovementTypes(List<MovementType> movementTypes) {
        this.movementTypes = movementTypes;
    }

    @Override
    public AlgorithmSelectionViewer getAlgorithmSelectionView() {
        return algorithmSelectionView;
    }

    @Override
    public ProblemGeneratorViewer getProblemGeneratorView() {
        return problemGeneratorView;
    }
}
