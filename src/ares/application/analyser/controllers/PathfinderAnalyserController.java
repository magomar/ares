package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderAnalyserInteractor;

import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderAnalyserController  {

    private final PathfinderAnalyserInteractor interactor;
    private static final Logger LOG = Logger.getLogger(PathfinderAnalyserController.class.getName());

    public PathfinderAnalyserController(PathfinderAnalyserInteractor interactor) {
        this.interactor = interactor;
    }

}
