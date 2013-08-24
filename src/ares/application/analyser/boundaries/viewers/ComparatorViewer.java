package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.gui.views.View;
import ares.platform.engine.movement.MovementType;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ComparatorViewer extends View<JPanel> {


    BoardViewer getLeftBoardView();

    BoardViewer getRightBoardView();

    AlgorithmConfigurationViewer getLefConfigurationView();

    AlgorithmConfigurationViewer getRightConfigurationView();

    JPanel getStatsPanel();

    void setMovementTypeComboModel(ComboBoxModel<MovementType> comboModel, ActionListener listener);

    void setShowCostTypeComboModel(ComboBoxModel<PathfindingLayerViewer.ShowCostType> comboModel, ActionListener listener);
}
