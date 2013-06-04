package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.PathfinderConfigurationViewer;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderComparatorViewer {
    

    BoardViewer getLeftBoardView();

    BoardViewer getRightBoardView();
    
    PathfinderConfigurationViewer getLefConfigurationView();
    
    PathfinderConfigurationViewer getRightConfigurationView();

    void flush();

}
