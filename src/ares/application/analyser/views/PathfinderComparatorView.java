package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.MutualPathfindersConfigurationViewer;
import ares.application.shared.boundaries.viewers.PathfinderConfigurationViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderComparatorView extends AbstractView<JPanel> implements PathfinderComparatorViewer {

    //    private static final int PATHFINDER_CONFIGURATION_HEIGHT = 150;
//    private static final int PATHFINDER_STATS_HEIGHT = 150;
    private JSplitPane splitHoriz;
    private JPanel configurationPanel;
    private JPanel statsPanel;
    private PathSearchBoardView leftBoardView;
    private PathSearchBoardView rightBoardView;
    private PathfinderConfigurationView leftConfigurationView;
    private PathfinderConfigurationView rightConfigurationView;
    private MutualPathfindersConfigurationView mutualConfigurationView;

    @Override
    protected JPanel layout() {
        leftBoardView = new PathSearchBoardView();
        rightBoardView = new PathSearchBoardView();
        leftConfigurationView = new PathfinderConfigurationView();
        rightConfigurationView = new PathfinderConfigurationView();
        mutualConfigurationView = new MutualPathfindersConfigurationView();
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        configurationPanel.add(leftConfigurationView.getContentPane(), c);
        c.gridx = 1;
        c.gridy = 0;
        configurationPanel.add(rightConfigurationView.getContentPane(), c);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        configurationPanel.add(mutualConfigurationView.getContentPane(), c);
        statsPanel = new JPanel();
        statsPanel.add(new JLabel("Left stats"));
        statsPanel.add(new JLabel("Right stats"));
        splitHoriz = ComponentFactory.horizontalSplitPane(true, leftBoardView.getContentPane(), rightBoardView.getContentPane(), 0.5);
        container.add(configurationPanel, BorderLayout.NORTH);
        container.add(splitHoriz, BorderLayout.CENTER);
        container.add(statsPanel, BorderLayout.SOUTH);
        return container;
    }


    @Override
    public BoardViewer getLeftBoardView() {
        return leftBoardView;
    }

    @Override
    public BoardViewer getRightBoardView() {
        return rightBoardView;
    }

    @Override
    public PathfinderConfigurationViewer getLefConfigurationView() {
        return leftConfigurationView;
    }

    @Override
    public PathfinderConfigurationViewer getRightConfigurationView() {
        return rightConfigurationView;
    }

    @Override
    public MutualPathfindersConfigurationViewer getMutualConfigurationView() {
        return mutualConfigurationView;
    }
    
    @Override
    public JPanel getStatsPanel() {
        return statsPanel;
    }

}
