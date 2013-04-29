package ares.application.controllers;

import ares.application.boundaries.view.*;
import ares.application.gui.main.AresPlayerGUI;
import ares.engine.RealTimeEngine;
import ares.platform.application.*;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class WeGoPlayerController {

    // Views
    private final AbstractAresApplication mainView;
    private final BoardViewer boardView;
    private final UnitInfoViewer infoView;
    private final OOBViewer oobView;
    private final CommandBarViewer menuView;
    private final MessagesViewer messagesView;
    private final CommandBarViewer welcomeScreenView;
    //Secondary controllers
    private final BoardController boardController;
    private final EngineController engineController;
    private final MessagesController messagesController;
    private final ScenarioIOController scenarioController;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private final RealTimeEngine engine;
    private UserRole userRole;
    private Scenario scenario;
    // Other fields
    private final ExecutorService executor;
    private static final Logger LOG = Logger.getLogger(WeGoPlayerController.class.getName());

    public WeGoPlayerController(AbstractAresApplication mainView, BoardViewer boardView, UnitInfoViewer unitView, OOBViewer oobView, CommandBarViewer menuView, MessagesViewer messagesView, CommandBarViewer welcomeScreenV) {
        //        executor = Executors.newCachedThreadPool();
        executor = Executors.newSingleThreadExecutor();
        this.engine = new RealTimeEngine();

        this.mainView = mainView;

        this.boardView = boardView;
        this.infoView = unitView;
        this.oobView = oobView;
        this.menuView = menuView;
        this.messagesView = messagesView;
        this.welcomeScreenView = welcomeScreenV;

        this.boardController = new BoardController(this);
        this.engineController = new EngineController(this);
        this.messagesController = new MessagesController(this);
        this.scenarioController = new ScenarioIOController(this);

        LOG.addHandler(messagesView.getHandler());

        //Initialize views
        mainView.switchCard(AresPlayerGUI.MAIN_MENU_CARD);

    }

    public ExecutorService getExecutor() {
        return executor;
    }

    void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    UserRole getUserRole() {
        return userRole;
    }

    Scenario getScenario() {
        return scenario;
    }

    void setScenario(Scenario scenario) {
        this.scenario = scenario;
        engine.setScenario(scenario);
    }

    public AbstractAresApplication getMainView() {
        return mainView;
    }

    public BoardViewer getBoardView() {
        return boardView;
    }

    public UnitInfoViewer getInfoView() {
        return infoView;
    }

    public CommandBarViewer getMenuView() {
        return menuView;
    }

    public MessagesViewer getMessagesView() {
        return messagesView;
    }

    public OOBViewer getOobView() {
        return oobView;
    }

    public CommandBarViewer getWelcomeScreenView() {
        return welcomeScreenView;
    }

    public RealTimeEngine getEngine() {
        return engine;
    }
}
