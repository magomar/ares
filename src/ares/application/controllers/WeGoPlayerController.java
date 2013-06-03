package ares.application.controllers;

import ares.application.gui.ComponentFactory;
import ares.application.boundaries.view.ActionBarViewer;
import ares.application.boundaries.view.InfoViewer;
import ares.application.boundaries.view.OOBViewer;
import ares.application.boundaries.view.MiniMapViewer;
import ares.application.boundaries.view.BoardViewer;
import ares.application.boundaries.view.MessagesViewer;
import ares.application.gui.profiles.GraphicsModel;
import ares.platform.scenario.forces.UnitsColor;
import ares.application.gui.providers.AresMiscTerrainGraphics;
import ares.application.models.ScenarioModel;
import ares.application.boundaries.interactor.EngineInteractor;
import ares.application.boundaries.interactor.MessagesInteractor;
import ares.application.boundaries.interactor.PlayerBoardInteractor;
import ares.application.boundaries.interactor.ScenarioInteractor;
import ares.application.boundaries.view.PlayerViewer;
import ares.platform.engine.time.Clock;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Terrain;
import java.awt.Container;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.*;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class WeGoPlayerController implements EngineInteractor, ScenarioInteractor, MessagesInteractor, PlayerBoardInteractor {

    // Views
    private final PlayerViewer playerView;
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    private final OOBViewer oobView;
    private final ActionBarViewer<JMenu> menuView;
    private final MessagesViewer messagesView;
    private final ActionBarViewer<JButton> mainMenuView;
    private final ActionBarViewer<JButton> toolBarView;
    private final MiniMapViewer miniMapView;
    //Secondary controllers
    private final BoardController boardController;
    private final RealTimeEngineController engineController;
    private final MessagesController messagesController;
    private final ScenarioController scenarioController;
    // Entities (bussines logic), they interact with the model providers and provide models to the views
    private Scenario scenario;
    // Other fields
    private final ExecutorService executor;

    public WeGoPlayerController(PlayerViewer playerView) {
        executor = Executors.newCachedThreadPool();
//        executor = Executors.newSingleThreadExecutor();
        this.playerView = playerView;
        boardView = playerView.getBoardView();
        infoView = playerView.getInfoView();
        oobView = playerView.getOobView();
        menuView = playerView.getMenuView();
        messagesView = playerView.getMessagesView();
        mainMenuView = playerView.getMainMenuView();
        toolBarView = playerView.getToolBarView();
        miniMapView = playerView.getMiniMapView();

        this.scenarioController = new ScenarioController(this);
        this.engineController = new RealTimeEngineController(this);
        this.messagesController = new MessagesController(this);
        this.boardController = new BoardController(this, engineController.getEngine());

        //sets the main menu as the mainView
        playerView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);

    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void setRunning(boolean running) {
        if (running) {
            messagesView.clear();
            boardView.updateCurrentOrders(null);
        } else {
        }
    }

    @Override
    public void newScenario(Scenario scenario) {
        this.scenario = scenario;
        // Initialize GraphicsModel
        GraphicsModel.INSTANCE.initialize(scenario.getBoard());
        GraphicsModel.INSTANCE.addAllGraphics(Terrain.values());
        GraphicsModel.INSTANCE.addAllGraphics(AresMiscTerrainGraphics.values());
        GraphicsModel.INSTANCE.addAllGraphics(UnitsColor.values());
        // pass the scenario to the engine controller
        engineController.setScenario(scenario);
        boardController.setScenario(scenario);
       // change the GUI to show the scenario
        playerView.switchPerspective(PlayerViewer.PLAYER_PERSPECTIVE);
 
        System.gc();
    }

    @Override
    public void forgetScenario() {
        boardView.flush();
        miniMapView.flush();
        oobView.flush();
        playerView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);
        System.gc();
    }

    @Override
    public void registerLogger(Logger logger) {
        logger.addHandler(messagesView.getHandler());
    }

    @Override
    public void addMenu(String name, String text, Integer mnemonic, Action[] actions) {
        menuView.addActionButton(ComponentFactory.menu(name, text, mnemonic, actions));
    }

    @Override
    public void addMainActions(Action[] actions) {
        for (Action action : actions) {
            mainMenuView.addActionButton(ComponentFactory.translucidButton(action));
        }
    }

    @Override
    public void addActions(Action[] actions) {
        for (Action action : actions) {
            toolBarView.addActionButton(ComponentFactory.button(action));
        }
    }

    @Override
    public Container getGUIContainer() {
        if (scenario == null) {
            return mainMenuView.getContentPane();
        } else {
            return menuView.getContentPane();
        }
    }

    @Override
    public BoardViewer getBoardView() {
        return boardView;
    }

    @Override
    public MessagesViewer getMessagesView() {
        return messagesView;
    }

    @Override
    public InfoViewer getInfoView() {
        return infoView;
    }

    @Override
    public OOBViewer getOOBView() {
        return oobView;
    }

    @Override
    public MiniMapViewer getMiniMapView() {
        return miniMapView;
    }
}
