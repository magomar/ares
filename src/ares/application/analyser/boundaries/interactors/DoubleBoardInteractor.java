package ares.application.analyser.boundaries.interactors;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface DoubleBoardInteractor extends BoardInteractor {
        BoardViewer getSecondBoardView();
}

