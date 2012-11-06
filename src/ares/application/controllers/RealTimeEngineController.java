package ares.application.controllers;

import ares.engine.realtime.RealTimeEngine;
import ares.platform.controller.AbstractController;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngineController extends AbstractController {

    private static final Logger LOG = Logger.getLogger(RealTimeEngineController.class.getName());

    @Override
    protected void registerAllActionListeners() {
    }

    @Override
    protected void registerAllModels() {
        getModel(RealTimeEngine.class).addPropertyChangeListener(this);
    }
}
