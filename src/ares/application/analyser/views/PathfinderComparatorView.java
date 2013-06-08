package ares.application.analyser.views;

import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.PathfinderConfigurationViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.views.AbstractView;
import ares.application.shared.gui.views.BoardView;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderComparatorView extends AbstractView<JPanel> implements PathfinderComparatorViewer {

    private static final int PATHFINDER_CONFIGURATION_HEIGHT = 150;
    private static final int PATHFINDER_STATS_HEIGHT = 150;
    private JSplitPane splitHoriz;
    private JPanel configurationPanel;
    private JPanel statsPanel;
    private BoardView leftBoardV;
    private BoardView rightBoardV;
    private PathfinderConfigurationView leftConfigurationV;
    private PathfinderConfigurationView rightConfigurationV;

    @Override
    protected JPanel layout() {
        leftBoardV = new BoardView();
        rightBoardV = new BoardView();
        leftConfigurationV = new PathfinderConfigurationView();
        rightConfigurationV = new PathfinderConfigurationView();
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        configurationPanel = new JPanel();
        configurationPanel.add(leftConfigurationV.getContentPane());
        configurationPanel.add(rightConfigurationV.getContentPane());
        statsPanel = new JPanel();
        statsPanel.add(new JLabel("Left stats"));
        statsPanel.add(new JLabel("Right stats"));
        splitHoriz = ComponentFactory.horizontalSplitPane(true, leftBoardV.getContentPane(), rightBoardV.getContentPane(), 0.5);
        container.add(configurationPanel, BorderLayout.NORTH);
        container.add(splitHoriz, BorderLayout.CENTER);
        container.add(statsPanel, BorderLayout.SOUTH);
        return container;
    }

//    @Override
//    public void setPreferredSize(Dimension size) {
////        contentPane.setPreferredSize(size);
//        super.setPreferredSize(size);
//        Dimension configurationPaneDimension = getConfigurationPaneDimension(size);
//        Dimension boardPaneDimension = getBoardPaneDimension(size);
////        leftConfigurationV.setPreferredSize(configurationPaneDimension);
////        rightConfigurationV.setPreferredSize(configurationPaneDimension);
////        leftBoardV.setPreferredSize(boardPaneDimension);
////        rightBoardV.setPreferredSize(boardPaneDimension);
//        statsPanel.setPreferredSize(getStatsPaneDimension(size));
//    }

//    private Dimension getConfigurationPaneDimension(Dimension containerSize) {
//        return new Dimension(containerSize.width / 2, PATHFINDER_CONFIGURATION_HEIGHT);
//    }
//
//    private Dimension getBoardPaneDimension(Dimension containerSize) {
//        return new Dimension(containerSize.width / 2 - ComponentFactory.SPLIT_DIVIDER_SIZE,
//                containerSize.height - PATHFINDER_STATS_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE - PATHFINDER_CONFIGURATION_HEIGHT);
//    }
//
//    private Dimension getStatsPaneDimension(Dimension containerSize) {
//        return new Dimension(containerSize.width/2, PATHFINDER_STATS_HEIGHT);
//    }

    @Override
    public BoardViewer getLeftBoardView() {
        return leftBoardV;
    }

    @Override
    public BoardViewer getRightBoardView() {
        return rightBoardV;
    }

    @Override
    public PathfinderConfigurationViewer getLefConfigurationView() {
        return leftConfigurationV;
    }

    @Override
    public PathfinderConfigurationViewer getRightConfigurationView() {
        return rightConfigurationV;
    }

    @Override
    public void flush() {
        leftBoardV.forgetScenario();
        rightBoardV.forgetScenario();
    }
}
