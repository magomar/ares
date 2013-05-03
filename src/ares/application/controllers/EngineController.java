package ares.application.controllers;

import ares.application.boundaries.view.ActionBarViewer;
import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.MessagesViewer;
import ares.application.boundaries.view.InfoViewer;
import ares.application.commands.EngineCommands;
import ares.application.commands.AresCommandGroup;
import ares.application.views.MessagesHandler;
import ares.engine.time.ClockEvent;
import ares.engine.time.ClockEventType;
import ares.engine.RealTimeEngine;
import ares.platform.controllers.AbstractSecondaryController;
import ares.engine.time.Clock;
import ares.platform.commands.CommandAction;
import ares.platform.commands.CommandGroup;
import ares.platform.view.ComponentFactory;
import ares.scenario.Scenario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public final class EngineController extends AbstractSecondaryController implements PropertyChangeListener {

    private static final Logger LOG = Logger.getLogger(EngineController.class.getName());
    private final ActionBarViewer<JMenu> menuView;
    private final ActionBarViewer<JButton> toolBarView;
    private final MessagesViewer messagesView;
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    private Action pause = new CommandAction(EngineCommands.PAUSE, new PauseActionListener(), false);
    private Action turn = new CommandAction(EngineCommands.TURN, new NextTurnActionListener());
    private Action step = new CommandAction(EngineCommands.STEP, new NextStepActionListener());

    public EngineController(WeGoPlayerController mainController) {
        super(mainController);
        this.menuView = mainController.getMenuView();
        this.toolBarView = mainController.getToolBarView();
        this.messagesView = mainController.getMessagesView();
        this.boardView = mainController.getBoardView();
        this.infoView = mainController.getInfoView();
        LOG.addHandler(messagesView.getHandler());

        //Add actions to the views

        Action[] actions = {pause, turn, step};
        CommandGroup group = AresCommandGroup.ENGINE;
        menuView.addActionButton(ComponentFactory.menu(group.getName(), group.getText(), group.getMnemonic(), actions));
        for (Action action : actions) {
            toolBarView.addActionButton(ComponentFactory.button(action));
        }

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
            infoView.updateScenarioInfo(scenInfo);
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                pause.setEnabled(false);
                turn.setEnabled(true);
                step.setEnabled(true);
            }
        }
    }

    private class PauseActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            engine.pause();
            pause.setEnabled(false);
            turn.setEnabled(true);
            step.setEnabled(true);
        }
    }

    private class NextTurnActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            messagesView.clear();
            pause.setEnabled(true);
            turn.setEnabled(false);
            step.setEnabled(false);
            boardView.updateCurrentOrders(null);
            engine.resume();
        }
    }

    private class NextStepActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LOG.log(MessagesHandler.MessageLevel.ENGINE, e.toString());
            boardView.updateCurrentOrders(null);
            engine.step();
        }
    }
}
