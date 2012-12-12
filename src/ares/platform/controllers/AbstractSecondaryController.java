package ares.platform.controllers;

import ares.application.controllers.WeGoPlayerController;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public class AbstractSecondaryController {
    protected final WeGoPlayerController mainController;
    
    public AbstractSecondaryController(WeGoPlayerController mainController) {
        this.mainController = mainController;
    }
}
