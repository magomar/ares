package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AnalyserInteractor;
import ares.application.analyser.boundaries.viewers.AnalyserViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.player.boundaries.viewers.PlayerViewer;
import ares.application.analyser.boundaries.interactors.DoubleBoardInteractor;
import ares.application.shared.boundaries.interactors.MessagesInteractor;
import ares.application.shared.boundaries.interactors.ScenarioInteractor;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import ares.application.shared.controllers.MessagesController;
import ares.application.shared.controllers.ScenarioController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Terrain;
import ares.platform.scenario.forces.UnitsColor;
import java.awt.Container;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfindingAnalyserController implements ScenarioInteractor, MessagesInteractor, DoubleBoardInteractor, AnalyserInteractor {

    private final AnalyserViewer mainView;
    private final ActionBarViewer<JMenu> menuView;
    private final MessagesViewer messagesView;
    private final ActionBarViewer<JButton> mainMenuView;
    private final ActionBarViewer<JButton> toolBarView;
    private final BoardViewer leftBoardView;
    private final BoardViewer rightBoardView;
    private final ScenarioController scenarioController;
    private final ComparatorController comparatorController;
    private final AnalyserController analyserController;
    private final MessagesController messagesController;

    public PathfindingAnalyserController(AnalyserViewer mainView) {
        this.mainView = mainView;
        menuView = mainView.getMenuView();
        messagesView = mainView.getMessagesView();
        mainMenuView = mainView.getMainMenuView();
        toolBarView = mainView.getToolBarView();
        leftBoardView = mainView.getLeftBoardView();
        rightBoardView = mainView.getRightBoardView();

        // instantiate controllers
        this.scenarioController = new ScenarioController(this);
        this.comparatorController = new ComparatorController(this);
        this.analyserController = new AnalyserController(this);
        this.messagesController = new MessagesController(this);

        // populate menus and tool bars
        mainMenuView.addActionButtons(scenarioController.getActionGroup().createMainMenuButtons());
        toolBarView.addActionButtons(scenarioController.getActionGroup().createToolBarButtons());
        toolBarView.addActionButtons(comparatorController.getActionGroup().createToolBarButtons());
//        toolBarView.addActionButtons(analyserController.getActionGroup().createToolBarButtons());
        JMenu[] menus = {
            scenarioController.getActionGroup().createMenu(),
            comparatorController.getActionGroup().createMenu(),
//            analyserController.getActionGroup().createMenu()
        };
        menuView.addActionButtons(menus);

        //sets the initial perspective
        mainView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);
    }

    @Override
    public void forgetScenario() {
        leftBoardView.flush();
        rightBoardView.flush();
        mainView.switchPerspective(AnalyserViewer.MAIN_MENU_PERSPECTIVE);
        System.gc();
    }

    @Override
    public void newScenario(Scenario scenario) {
        // Initialize GraphicsModel
        GraphicsModel.INSTANCE.initialize(scenario.getBoard());
        GraphicsModel.INSTANCE.addAllGraphics(Terrain.values());
        GraphicsModel.INSTANCE.addAllGraphics(AresMiscTerrainGraphics.values());
        GraphicsModel.INSTANCE.addAllGraphics(UnitsColor.values());
        // pass the scenario to the engine controller
        comparatorController.setScenario(scenario);
        // change the GUI to show the pathfinding comparison perspective
        mainView.switchPerspective(AnalyserViewer.COMPARATOR_PERSPECTIVE);
        System.gc();
    }

    @Override
    public void registerLogger(Logger logger) {
        logger.addHandler(messagesView.getHandler());
    }

    @Override
    public Container getGUIContainer() {
        return mainView.getContentPane();
    }

    @Override
    public MessagesViewer getMessagesView() {
        return messagesView;
    }

    @Override
    public BoardViewer getSecondBoardView() {
        return rightBoardView;
    }

    @Override
    public BoardViewer getBoardView() {
        return leftBoardView;
    }
}
