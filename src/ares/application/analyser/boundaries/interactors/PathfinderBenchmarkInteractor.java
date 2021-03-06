package ares.application.analyser.boundaries.interactors;

import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.shared.boundaries.interactors.Interactor;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderBenchmarkInteractor extends Interactor {
    
    BenchmarkViewer getPathfinderBenchmarkView();
}
