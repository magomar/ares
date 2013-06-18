package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.PathfinderConfigurationViewer;
import ares.application.shared.gui.views.View;

import javax.swing.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderComparatorViewer extends View<JPanel> {


    BoardViewer getLeftBoardView();

    BoardViewer getRightBoardView();

    PathfinderConfigurationViewer getLefConfigurationView();

    PathfinderConfigurationViewer getRightConfigurationView();


}
