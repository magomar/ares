package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;

import java.util.logging.Logger;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class BenchmarkController {

    private static final Logger LOG = Logger.getLogger(BenchmarkController.class.getName());
    private final BenchmarkViewer benchmarkView;

    
    public BenchmarkController(PathfinderBenchmarkInteractor interactor, PathfinderToolsViewer mainView) {
        benchmarkView = interactor.getPathfinderBenchmarkView();
    }





}
