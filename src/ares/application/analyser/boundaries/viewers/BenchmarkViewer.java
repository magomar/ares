package ares.application.analyser.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.platform.engine.movement.MovementType;

import javax.swing.JPanel;
import java.util.List;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public interface BenchmarkViewer extends View<JPanel> {
    void setMovementTypes(List<MovementType> movementTypes);
}
