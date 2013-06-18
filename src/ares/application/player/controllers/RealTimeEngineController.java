package ares.application.player.controllers;

import ares.application.shared.action.ActionGroup;
import ares.application.shared.action.CommandAction;
import ares.application.shared.action.CommandGroup;
import ares.application.shared.boundaries.interactors.EngineInteractor;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.EngineCommands;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.gui.views.MessagesHandler;
import ares.platform.engine.RealTimeEngine;
import ares.platform.engine.time.ClockEvent;
import ares.platform.engine.time.ClockEventType;
import ares.platform.scenario.Scenario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class RealTimeEngineController implements ActionController, PropertyChangeListener {

    public static final String RUNNING = "Scenario";
    private static final Logger LOG = Logger.getLogger(RealTimeEngineController.class.getName());
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    private final EngineInteractor interactor;
    private Action pause = new CommandAction(EngineCommands.ENGINE_PAUSE, new PauseActionListener(), false);
    private Action turn = new CommandAction(EngineCommands.ENGINE_NEXT_TURN, new NextTurnActionListener());
    private Action step = new CommandAction(EngineCommands.ENGINE_NEXT_STEP, new NextStepActionListener());
    private final ActionGroup actions;

    public RealTimeEngineController(EngineInteractor interactor) {
        this.interactor = interactor;
        // create action groups
        Action[] engineActions = {pause, turn, step};
        CommandGroup group = AresCommandGroup.ENGINE;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), engineActions);
        //Add change listeners to entities
        engine = new RealTimeEngine();
        engine.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                pause.setEnabled(false);
                turn.setEnabled(true);
                step.setEnabled(true);
            }
        }
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            pause.setEnabled(false);
            turn.setEnabled(true);
            step.setEnabled(true);
            interactor.setRunning(false);
            engine.pause();
        }
    }

    private class NextTurnActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            pause.setEnabled(true);
            turn.setEnabled(false);
            step.setEnabled(false);
            interactor.setRunning(true);
            engine.resume();
        }
    }

    private class NextStepActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            interactor.setRunning(true);
            engine.step();
            interactor.setRunning(false);
        }
    }

    public RealTimeEngine getEngine() {
        return engine;
    }

    public void setScenario(Scenario scenario) {
        engine.setScenario(scenario);
        engine.activate();
    }
}
