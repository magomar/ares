package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.AlgorithmConfigurationViewer;
import ares.application.analyser.boundaries.viewers.ComparatorViewer;
import ares.application.analyser.boundaries.viewers.PathfindingLayerViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;
import ares.platform.engine.movement.MovementType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class ComparatorView extends AbstractView<JPanel> implements ComparatorViewer {

    //    private static final int PATHFINDER_CONFIGURATION_HEIGHT = 150;
//    private static final int PATHFINDER_STATS_HEIGHT = 150;
    private JSplitPane splitHoriz;
    private JPanel configurationPanel;
    private JPanel statsPanel;
    private PathfindingBoardView[] pathfindingBoardView;
    private AlgorithmConfigurationView[] algorithmConfigurationView;
    private JComboBox<MovementType> movementTypeComboBox;
    private JComboBox<PathfindingLayerViewer.ShowCostType> showCostTypeComboBox;

    @Override
    protected JPanel layout() {
        pathfindingBoardView = new PathfindingBoardView[2];
        algorithmConfigurationView = new AlgorithmConfigurationView[2];
        for (int side = 0; side < 2; side++) {
            pathfindingBoardView[side] = new PathfindingBoardView();
            algorithmConfigurationView[side] = new AlgorithmConfigurationView();
        }

        movementTypeComboBox = new JComboBox<>();
        showCostTypeComboBox = new JComboBox<>();
        JPanel commonConfigurationPanel = new JPanel();
        commonConfigurationPanel.setLayout(new GridBagLayout());
        GridBagConstraints commonConfigurationPanelConstraints = new GridBagConstraints();
        commonConfigurationPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        commonConfigurationPanelConstraints.gridx = 0;
        commonConfigurationPanelConstraints.gridy = 0;
        commonConfigurationPanel.add(new JLabel("Select Unit Type:"), commonConfigurationPanelConstraints);
        commonConfigurationPanelConstraints.gridx = 1;
        commonConfigurationPanelConstraints.gridy = 0;
        commonConfigurationPanel.add(movementTypeComboBox, commonConfigurationPanelConstraints);
        commonConfigurationPanelConstraints.gridx = 2;
        commonConfigurationPanelConstraints.gridy = 0;
        commonConfigurationPanel.add(new JLabel("Select Cost Shown Type:"), commonConfigurationPanelConstraints);
        commonConfigurationPanelConstraints.gridx = 3;
        commonConfigurationPanelConstraints.gridy = 0;
        commonConfigurationPanel.add(showCostTypeComboBox, commonConfigurationPanelConstraints);

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());

        configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridBagLayout());
        GridBagConstraints configurationPanelConstraints = new GridBagConstraints();
        configurationPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
        configurationPanelConstraints.gridx = 0;
        configurationPanelConstraints.gridy = 0;
        configurationPanel.add(algorithmConfigurationView[LEFT].getContentPane(), configurationPanelConstraints);
        configurationPanelConstraints.gridx = 1;
        configurationPanelConstraints.gridy = 0;
        configurationPanel.add(algorithmConfigurationView[RIGHT].getContentPane(), configurationPanelConstraints);
        configurationPanelConstraints.gridwidth = 2;
        configurationPanelConstraints.gridx = 0;
        configurationPanelConstraints.gridy = 1;
        configurationPanel.add(commonConfigurationPanel, configurationPanelConstraints);
        statsPanel = new JPanel();
        statsPanel.add(new JLabel("Left stats"));
        statsPanel.add(new JLabel("Right stats"));
        splitHoriz = ComponentFactory.horizontalSplitPane(true, pathfindingBoardView[LEFT].getContentPane(), pathfindingBoardView[RIGHT].getContentPane(), 0.5);
        container.add(configurationPanel, BorderLayout.NORTH);
        container.add(splitHoriz, BorderLayout.CENTER);
        container.add(statsPanel, BorderLayout.SOUTH);
        return container;
    }

    @Override
    public BoardViewer getBoardView(int side) {
        return pathfindingBoardView[side];
    }

    @Override
    public AlgorithmConfigurationViewer getConfigurationView(int side) {
        return algorithmConfigurationView[side];
    }

    @Override
    public ComboBoxModel<PathfindingLayerViewer.ShowCostType> getCostTypeComboModel() {
        return showCostTypeComboBox.getModel();
    }

    @Override
    public ComboBoxModel<MovementType> getMovementTypeComboModel() {
        return movementTypeComboBox.getModel();
    }

    @Override
    public void setMovementTypeComboModel(ComboBoxModel<MovementType> comboModel) {
        movementTypeComboBox.setModel(comboModel);
    }

    @Override
    public JPanel getStatsPanel() {
        return statsPanel;
    }

    @Override
    public void setShowCostTypeComboModel(ComboBoxModel<PathfindingLayerViewer.ShowCostType> comboModel) {
        showCostTypeComboBox.setModel(comboModel);
    }

    @Override
    public void addMovementTypeActionListener(ActionListener actionListener) {
        movementTypeComboBox.addActionListener(actionListener);
    }
}
