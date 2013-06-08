package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderAnalyserInteractor;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.analyser.boundaries.interactors.PathfinderComparatorInteractor;
import ares.application.analyser.boundaries.viewers.PathfinderComparatorViewer;
import ares.application.shared.boundaries.interactors.ScenarioInteractor;
import ares.application.shared.boundaries.viewers.MenuBarViewer;
import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.boundaries.viewers.ToolBarViewer;
import ares.application.shared.controllers.ScenarioController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.application.shared.gui.providers.AresMiscTerrainGraphics;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Terrain;
import ares.platform.scenario.forces.UnitsColor;
import java.awt.Container;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderToolsController implements ScenarioInteractor, PathfinderComparatorInteractor, PathfinderAnalyserInteractor {

    private final PathfinderToolsViewer mainView;
    private final MenuBarViewer menuView;
    private final PanelMenuViewer mainMenuView;
    private final ToolBarViewer toolBarView;
    private final PathfinderComparatorViewer comparatorView;
    private final ScenarioController scenarioController;
    private final PathfinderComparatorController comparatorController;
    private final PathfinderAnalyserController analyserController;

    public PathfinderToolsController(PathfinderToolsViewer mainView) {
        this.mainView = mainView;
        menuView = mainView.getMenuView();
        mainMenuView = mainView.getMainMenuView();
        toolBarView = mainView.getToolBarView();
        comparatorView = mainView.getComparatorView();

        // instantiate controllers
        this.scenarioController = new ScenarioController(this);
        this.comparatorController = new PathfinderComparatorController(this);
        this.analyserController = new PathfinderAnalyserController(this);

        // populate menus and tool bars
        mainMenuView.addActionButtons(scenarioController.getActionGroup().createMainMenuButtons());
        toolBarView.addActionButtons(scenarioController.getActionGroup().createToolBarButtons());
        toolBarView.addActionButtons(comparatorController.getActionGroup().createToolBarButtons());
//        toolBarView.addActionButtons(analyserController.getActionGroup().createToolBarButtons());
        JMenu[] menus = {
            scenarioController.getActionGroup().createMenu(),
            comparatorController.getActionGroup().createMenu(), //            analyserController.getActionGroup().createMenu()
        };
        menuView.addActionButtons(menus);

        //sets the initial perspective
        mainView.switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
    }

    @Override
    public void forgetScenario() {
        comparatorView.flush();
//        mainView.switchPerspective(PathfinderToolsViewer.MAIN_MENU_PERSPECTIVE);
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
        mainView.switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
        System.gc();
    }

    @Override
    public Container getGUIContainer() {
        return mainView.getContentPane();
    }

    @Override
    public PathfinderComparatorViewer getPathfinderComparatorView() {
        return comparatorView;
    }
}
