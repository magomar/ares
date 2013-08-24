package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;

import java.util.logging.Logger;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderBenchmarkController {

    private final PathfinderBenchmarkInteractor interactor;
    private static final Logger LOG = Logger.getLogger(PathfinderBenchmarkController.class.getName());

    public PathfinderBenchmarkController(PathfinderBenchmarkInteractor interactor) {
        this.interactor = interactor;
    }

}
