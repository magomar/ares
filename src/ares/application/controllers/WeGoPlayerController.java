package ares.application.controllers;

import ares.application.boundaries.view.*;
import ares.application.commands.*;
import ares.application.player.AresPlayerGUI;
import ares.application.views.*;
import ares.engine.realtime.*;
import ares.platform.application.*;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.board.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class WeGoPlayerController implements PropertyChangeListener {

    // Views
    private final AbstractAresApplication mainView;
    private final BoardViewer boardView;
    private final UnitInfoViewer unitView;
    private final CommandBarViewer menuView;
    private final MessagesViewer messagesView;
    private final WelcomeScreenView welcomeScreenV;
    //Secondary controllers
    private final BoardController boardController;
    private final EngineController engineController;
    private final MessagesController messagesController;
    private final ScenarioIOController scenarioController;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    // Other fields
    private final ExecutorService executor;
    private UserRole userRole;
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());

    public WeGoPlayerController(AbstractAresApplication mainView, BoardViewer boardView, UnitInfoViewer unitView, CommandBarViewer menuView, MessagesViewer messagesView, WelcomeScreenView welcomeScreenV) {
        executor = Executors.newCachedThreadPool();
        this.mainView = mainView;
        this.engine = new RealTimeEngine();
        this.boardView = boardView;
        this.unitView = unitView;
        this.menuView = menuView;
        this.messagesView = messagesView;
        this.welcomeScreenV = welcomeScreenV;

        this.boardController = new BoardController(this);
        this.engineController = new EngineController(this);
        this.messagesController = new MessagesController(this);
        this.scenarioController = new ScenarioIOController(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (RealTimeEngine.CLOCK_EVENT_PROPERTY.equals(evt.getPropertyName())) {
            ClockEvent clockEvent = (ClockEvent) evt.getNewValue();
            Scenario scenario = engine.getScenario();
            mainView.setTitle("ARES   " + scenario.getName() + "   " + scenario.getCalendar().toString()
                    + "   Role: " + userRole + "           Time: " + clockEvent.getClock().toString());

            boardView.updateScenario(engine.getScenarioModel(userRole));

            if (boardController.getSelectedTile() != null) {
                unitView.updateInfo(boardController.getSelectedTile().getModel(userRole));
            }
            if (clockEvent.getEventTypes().contains(ClockEventType.TURN)) {
                menuView.setCommandEnabled(EngineCommands.PAUSE.getName(), false);
                menuView.setCommandEnabled(EngineCommands.NEXT.getName(), true);
            }
        }
    }

    public void initialize() {

        //Add listeners to the views
        menuView.addActionListener(FileCommands.NEW_SCENARIO.getName(), scenarioController.OpenScenarioActionListener());
        menuView.addActionListener(FileCommands.CLOSE_SCENARIO.getName(), scenarioController.CloseScenarioActionListener());
        menuView.addActionListener(FileCommands.EXIT.getName(), scenarioController.ExitActionListener());
        menuView.addActionListener(EngineCommands.START.name(), engineController.StartActionListener());
        menuView.addActionListener(EngineCommands.PAUSE.name(), engineController.PauseActionListener());
        menuView.addActionListener(EngineCommands.NEXT.name(), engineController.NextActionListener());
        boardView.addMouseListener(boardController.BoardMouseListener());

        // Add listeners to WelcomeScreen buttons
        welcomeScreenV.addActionListener(FileCommands.NEW_SCENARIO.getName(), scenarioController.OpenScenarioActionListener());
        welcomeScreenV.addActionListener(FileCommands.LOAD_SCENARIO.getName(), scenarioController.LoadScenarioActionListener());
        welcomeScreenV.addActionListener(FileCommands.SETTINGS.getName(), scenarioController.SettingsActionListener());
        welcomeScreenV.addActionListener(FileCommands.EXIT.getName(), scenarioController.ExitActionListener());

        // Add listeners to MessagesView check boxes
        messagesView.setLogCheckBoxes(messagesController.LogCheckBoxListener());

        //Add change listeners to entities
        engine.addPropertyChangeListener(this);
        LOG.addHandler(messagesView.getHandler());

        //Initialize views
        mainView.switchCard(AresPlayerGUI.MAIN_MENU_CARD);
    }

    Logger getLog() {
        return LOG;
    }

    RealTimeEngine getEngine() {
        return engine;
    }

    AbstractAresApplication getMainView() {
        return mainView;
    }

    BoardViewer getBoardView() {
        return boardView;
    }

    UnitInfoViewer getUnitView() {
        return unitView;
    }

    CommandBarViewer getMenuView() {
        return menuView;
    }

    MessagesViewer getMessagesView() {
        return messagesView;
    }

    WelcomeScreenView getWelcomeScreenV() {
        return welcomeScreenV;
    }

    ExecutorService getExecutor() {
        return executor;
    }

    void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    UserRole getUserRole() {
        return userRole;
    }
}
