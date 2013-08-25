package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
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

    private JPanel configurationPanel;
    private JPanel problemGeneratorPanel;
    private JPanel resultsPanel;
    private ProblemGeneratorView benchmarkConfigurationView;
    private JSplitPane splitVert;
    private JList<MovementType> movementTypeList;
    private java.util.List<MovementType> movementTypes;
    
    @Override
    protected JPanel layout() {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        configurationPanel = new JPanel();
        problemGeneratorPanel = new JPanel();
        resultsPanel = new JPanel();
        container.add(configurationPanel, BorderLayout.NORTH);
        container.add(problemGeneratorPanel, BorderLayout.CENTER);
        container.add(resultsPanel, BorderLayout.SOUTH);
        return container;
    }

    @Override
    public void setMovementTypes(List<MovementType> movementTypes) {
        this.movementTypes = movementTypes;
    }
}
