package ares.application.boundaries.interactor;

import ares.application.boundaries.view.MessagesViewer;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface MessagesInteractor extends Interactor {
    MessagesViewer getMessagesView();
}
