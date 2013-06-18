package ares.application.shared.boundaries.interactors;

import ares.application.shared.boundaries.viewers.BoardViewer;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardInteractor extends Interactor {

    BoardViewer getBoardView();

}
