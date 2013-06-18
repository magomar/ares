package ares.application.player.controllers;

import ares.application.player.boundaries.interactors.PlayerBoardInteractor;
import ares.application.player.boundaries.viewers.PlayerViewer;
import ares.application.shared.boundaries.interactors.EngineInteractor;
import ares.application.shared.boundaries.interactors.MessagesInteractor;
import ares.application.shared.boundaries.interactors.ScenarioInteractor;
import ares.application.shared.boundaries.viewers.*;
import ares.application.shared.boundaries.viewers.layerviewers.ArrowLayerViewer;
import ares.application.shared.controllers.MessagesController;
import ares.application.shared.controllers.ScenarioController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 * @author Heine <heisncfr@inf.upv.es>
 */
public class WeGoPlayerController implements EngineInteractor, ScenarioInteractor, PlayerBoardInteractor, MessagesInteractor {

    // Views
    private final PlayerViewer mainView;
    private final BoardViewer boardView;
    private final InfoViewer infoView;
    private final OOBViewer oobView;
    private final ActionBarViewer<JMenuBar, JMenu> menuView;
    private final MessagesViewer messagesView;
    private final ActionBarViewer<JPanel, JButton> mainMenuView;
    private final ActionBarViewer<JToolBar, JButton> toolBarView;
    private final BoardViewer miniMapView;
    //Secondary controllers
    private final PlayerBoardController boardController;
    private final RealTimeEngineController engineController;
    private final MessagesController messagesController;
    private final ScenarioController scenarioController;
    // Other fields
    private final ExecutorService executor;

    public WeGoPlayerController(PlayerViewer mainView) {
        executor = Executors.newCachedThreadPool();
//        executor = Executors.newSingleThreadExecutor();
        // instantiate views
        this.mainView = mainView;
        boardView = mainView.getBoardView();
        infoView = mainView.getInfoView();
        oobView = mainView.getOobView();
        menuView = mainView.getMenuView();
        messagesView = mainView.getMessagesView();
        mainMenuView = mainView.getMainMenuView();
        toolBarView = mainView.getToolBarView();
        miniMapView = mainView.getMiniMapView();

        // instantiate controllers
        scenarioController = new ScenarioController(this, true);
        engineController = new RealTimeEngineController(this);
        messagesController = new MessagesController(this);
        boardController = new PlayerBoardController(this, engineController.getEngine());

        //register logger handlers
        registerLogger(ScenarioController.class);
        registerLogger(RealTimeEngineController.class);
        registerLogger(PlayerBoardController.class);

        // populate menus and tool bars
        mainMenuView.addActionButtons(scenarioController.getActionGroup().createMainMenuButtons());
        toolBarView.addActionButtons(scenarioController.getActionGroup().createToolBarButtons());
        toolBarView.addActionButtons(boardController.getActionGroup().createToolBarButtons());
        toolBarView.addActionButtons(engineController.getActionGroup().createToolBarButtons());
        JMenu[] menus = {
                scenarioController.getActionGroup().createMenu(),
                boardController.getActionGroup().createMenu(),
                engineController.getActionGroup().createMenu()
        };
        menuView.addActionButtons(menus);

        //sets the initial perspective
        mainView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);

    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public void setRunning(boolean running) {
        if (running) {
            messagesView.clear();
            ((ArrowLayerViewer) boardView.getLayerView(ArrowLayerViewer.NAME)).updateCurrentOrders(null);
        } else {
        }
    }

    @Override
    public void newScenario(Scenario scenario, UserRole userRole) {
        // Initialize GraphicsModel
        GraphicsModel.INSTANCE.initialize(scenario.getBoard());
        // pass the scenario to the engine controller
        engineController.setScenario(scenario);
        boardController.setScenario(scenario, userRole);
        // change the GUI to show the playing perspective
        mainView.switchPerspective(PlayerViewer.PLAYER_PERSPECTIVE);
        System.gc();
    }

    @Override
    public void forgetScenario() {
        boardView.flush();
        miniMapView.flush();
        oobView.flush();
        mainView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);
        System.gc();
    }

    @Override
    public Container getGUIContainer() {
        return mainView.getContentPane();
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
    public BoardViewer getMiniMapView() {
        return miniMapView;
    }

    private void registerLogger(Class<?> aClass) {
        Logger.getLogger(aClass.getName()).addHandler(messagesView.getHandler());
    }

    @Override
    public PlayerViewer getPlayerView() {
        return mainView;
    }

}
