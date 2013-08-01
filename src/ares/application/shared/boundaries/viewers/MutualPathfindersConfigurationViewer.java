package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.platform.engine.movement.MovementType;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.JPanel;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public interface MutualPathfindersConfigurationViewer extends View<JPanel> {
    void setMovementTypeComboModel(ComboBoxModel<MovementType> comboModel, ActionListener listener);
}
