package ares.application.player.boundaries.interactors;

import ares.application.shared.boundaries.interactors.BoardInteractor;
import ares.application.shared.boundaries.viewers.BoardViewer;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface MiniMapInteractor extends BoardInteractor {

    BoardViewer getMiniMapView();

    void changeBoardViewport();
}
