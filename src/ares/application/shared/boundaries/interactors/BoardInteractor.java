package ares.application.shared.boundaries.interactors;

import ares.application.shared.boundaries.viewers.BoardViewer;
import javax.swing.JViewport;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface BoardInteractor extends Interactor {

    BoardViewer getBoardView();

    void changeBoardViewport(JViewport viewport);
}
