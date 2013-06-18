package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderConfigurationViewer extends View<JPanel> {

    void setPathfinderComboModel(ComboBoxModel<Pathfinder> comboModel, ActionListener listener);

    void setHeuristicComboModel(ComboBoxModel<Heuristic> comboModel, ActionListener listener);

    void setCostFunctionComboModel(ComboBoxModel<CostFunction> comboModel, ActionListener listener);
}
