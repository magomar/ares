package ares.application.analyser.boundaries.interactors;

import ares.application.analyser.boundaries.viewers.ComparatorViewer;
import ares.application.shared.boundaries.interactors.Interactor;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ComparatorInteractor extends Interactor {

    ComparatorViewer getPathfinderComparatorView();
}
