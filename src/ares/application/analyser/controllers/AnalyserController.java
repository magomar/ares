package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AnalyserInteractor;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class AnalyserController  {

    private final AnalyserInteractor interactor;
    private static final Logger LOG = Logger.getLogger(AnalyserController.class.getName());

    public AnalyserController(AnalyserInteractor interactor) {
        this.interactor = interactor;
        interactor.registerLogger(LOG);
    }

}
