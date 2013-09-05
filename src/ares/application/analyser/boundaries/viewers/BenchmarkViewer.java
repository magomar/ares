package ares.application.analyser.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.platform.engine.movement.MovementType;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public interface BenchmarkViewer extends View<JPanel> {

    AlgorithmSelectionViewer getAlgorithmSelectionView();

    ProblemGeneratorViewer getProblemGeneratorView();

    void setMovementTypeComboBoxModel(ComboBoxModel<MovementType> movementTypeComboBoxModel);

    void addMovementTypeActionListener(ActionListener actionListener);

    void addExecuteBenchmarkActionListener(ActionListener actionListener);

    void log(String text);

    MovementType getMovementType();
}
