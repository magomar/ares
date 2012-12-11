package ares.application.controllers;

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
