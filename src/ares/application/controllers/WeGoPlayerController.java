package ares.application.controllers;

import ares.application.boundaries.view.*;
import ares.application.AresPlayerGUI;
import ares.engine.RealTimeEngine;
import ares.platform.application.*;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class WeGoPlayerController {

    // Views
    private final AbstractAresApplication mainView;
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    private final OOBViewer oobView;
    private final ActionBarViewer<JMenu> menuView;
    private final MessagesViewer messagesView;
    private final ActionBarViewer<JButton> welcomeScreenView;
    private final ActionBarViewer<JButton> toolBarView;
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

    public WeGoPlayerController(AbstractAresApplication mainView, BoardViewer boardView, InfoViewer unitView, OOBViewer oobView, ActionBarViewer<JMenu> menuView, MessagesViewer messagesView, ActionBarViewer<JButton> welcomeScreenV, ActionBarViewer<JButton> toolBarView) {
        //        executor = Executors.newCachedThreadPool();
        executor = Executors.newSingleThreadExecutor();
        this.engine = new RealTimeEngine();

        this.mainView = mainView;
        this.welcomeScreenView = welcomeScreenV;
        this.boardView = boardView;
        this.infoView = unitView;
        this.oobView = oobView;
        this.menuView = menuView;
        this.messagesView = messagesView;
        this.toolBarView = toolBarView;

        this.scenarioController = new ScenarioIOController(this);
        this.boardController = new BoardController(this);
        this.engineController = new EngineController(this);
        this.messagesController = new MessagesController(this);

        LOG.addHandler(messagesView.getHandler());

        //add buttons and menu elements fro here to have them in proper order

        //sets the main menu as the mainView
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

    public InfoViewer getInfoView() {
        return infoView;
    }

    public ActionBarViewer<JButton> getToolBarView() {
        return toolBarView;
    }

    public ActionBarViewer<JMenu> getMenuView() {
        return menuView;
    }

    public MessagesViewer getMessagesView() {
        return messagesView;
    }

    public OOBViewer getOobView() {
        return oobView;
    }

    public ActionBarViewer<JButton> getWelcomeScreenView() {
        return welcomeScreenView;
    }

    public RealTimeEngine getEngine() {
        return engine;
    }
}
