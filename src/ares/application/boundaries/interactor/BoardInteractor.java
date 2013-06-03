package ares.application.boundaries.interactor;

import ares.application.boundaries.view.BoardViewer;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardInteractor extends Interactor {

    BoardViewer getBoardView();
}
