package ares.application.controllers;

import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.CommandBarViewer;
import ares.application.boundaries.view.MessagesViewer;
import ares.application.boundaries.view.UnitInfoViewer;
import ares.application.commands.EngineCommands;
import ares.application.views.MessagesHandler;
import ares.engine.time.ClockEvent;
import ares.engine.time.ClockEventType;
import ares.engine.RealTimeEngine;
import ares.platform.controllers.AbstractSecondaryController;
import ares.scenario.Clock;
import ares.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class EngineController extends AbstractSecondaryController implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(EngineController.class.getName());
    private final CommandBarViewer menuView;
    private final MessagesViewer messagesView;
    private final BoardViewer boardView;
    private final UnitInfoViewer infoView;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;

    public EngineController(WeGoPlayerController mainController) {
        super(mainController);

        this.menuView = mainController.getMenuView();
        this.messagesView = mainController.getMessagesView();
        this.boardView = mainController.getBoardView();
        this.infoView = mainController.getInfoView();
        LOG.addHandler(messagesView.getHandler());

        menuView.addActionListener(EngineCommands.START.name(), new StartActionListener());
        menuView.addActionListener(EngineCommands.PAUSE.name(), new PauseActionListener());
        menuView.addActionListener(EngineCommands.NEXT.name(), new NextActionListener());

        //Add change listeners to entities
        engine = mainController.getEngine();
        engine.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            Scenario scenario = engine.getScenario();
            boardView.updateScenario(scenario.getModel(mainController.getUserRole()));
            String scenInfo = scenario.getName() + "\n" + Clock.INSTANCE.toStringVerbose() + "\nRole: " + mainController.getUserRole();
            infoView.updateScenInfo(scenInfo);
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setCommandEnabled(EngineCommands.NEXT.getName(), true);
            }
        }
    }

    RealTimeEngine getEngine() {
        return engine;
    }

    class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            engine.start();
            menuView.setCommandEnabled(EngineCommands.START.getName(), false);
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), true);
        }
    }

    class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            engine.stop();
            menuView.setCommandEnabled(EngineCommands.START.getName(), true);
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
        }
    }

    class NextActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            messagesView.clear();
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), true);
            menuView.setCommandEnabled(EngineCommands.NEXT.getName(), false);
            engine.start();
        }
    }
}
