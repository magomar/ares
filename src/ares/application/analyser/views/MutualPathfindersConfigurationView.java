/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.MutualPathfindersConfigurationViewer;
import ares.application.analyser.boundaries.viewers.PathSearchLayerViewer;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.engine.movement.MovementType;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public class MutualPathfindersConfigurationView extends AbstractView<JPanel> implements MutualPathfindersConfigurationViewer {
    
    private JComboBox<MovementType> movementTypeComboBox;
    private JComboBox<PathSearchLayerViewer.ShowCostType> showCostTypeComboBox;

    @Override
    protected JPanel layout() {
        movementTypeComboBox = new JComboBox<>();
        showCostTypeComboBox = new JComboBox<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Select Unit Type:"), c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(movementTypeComboBox, c);
        c.gridx = 2;
        c.gridy = 0;
        panel.add(new JLabel("Select Cost Shown Type:"), c);
        c.gridx = 3;
        c.gridy = 0;
        panel.add(showCostTypeComboBox, c);
        return panel;
    }

    @Override
    public void setMovementTypeComboModel(ComboBoxModel<MovementType> comboModel, ActionListener listener) {
        movementTypeComboBox.setModel(comboModel);
        movementTypeComboBox.addActionListener(listener);
    }

    @Override
    public void setShowCostTypeComboModel(ComboBoxModel<PathSearchLayerViewer.ShowCostType> comboModel, ActionListener listener) {
        showCostTypeComboBox.setModel(comboModel);
        showCostTypeComboBox.addActionListener(listener);
    }
}
