package ares.application.analyser.boundaries.interactors;

import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.boundaries.interactors.Interactor;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderComparatorInteractor extends Interactor {

    PathfinderComparatorViewer getPathfinderComparatorView();
}
