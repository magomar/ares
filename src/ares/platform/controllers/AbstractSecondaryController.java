package ares.platform.controllers;

import ares.application.controllers.WeGoPlayerController;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractSecondaryController  {

    protected final WeGoPlayerController mainController;

    public AbstractSecondaryController(WeGoPlayerController mainController) {
        this.mainController = mainController;
    }
}
