package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.ComparatorInteractor;
import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.ComparatorViewer;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.shared.boundaries.interactors.ScenarioInteractor;
import ares.application.shared.boundaries.viewers.MenuBarViewer;
import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.boundaries.viewers.ToolBarViewer;
import ares.application.shared.commands.*;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.controllers.ScenarioController;
import ares.application.shared.gui.profiles.GraphicsModel;
import ares.platform.model.UserRole;
import ares.platform.scenario.Scenario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderToolsController implements ActionController, ScenarioInteractor, ComparatorInteractor, PathfinderBenchmarkInteractor {

    private final PathfinderToolsViewer mainView;
    private final ComparatorViewer comparatorView;
    private final BenchmarkViewer benchmarkView;
    private final ComparatorController comparatorController;
    private final BenchmarkController benchmarkController;
    private final Action showComparator = new CommandAction(PathfinderToolsCommands.COMPARATOR_PERSPECTIVE, new ComparatorPerspectiveActionListener());
    private final Action showBenchmark = new CommandAction(PathfinderToolsCommands.BENCHMARK_PERSPECTIVE, new BenchmarkPerspectiveActionListener());
    private final ActionGroup actions;

    public PathfinderToolsController(PathfinderToolsViewer mainView) {
        this.mainView = mainView;
        MenuBarViewer menuView = mainView.getMenuView();
        PanelMenuViewer mainMenuView = mainView.getMainMenuView();
        ToolBarViewer toolBarView = mainView.getToolBarView();
        comparatorView = mainView.getComparatorView();
        benchmarkView = mainView.getBenchmarkView();

        Action[] viewActions = {showComparator, showBenchmark};
        CommandGroup group = AresCommandGroup.PATHFINDER_TOOLS;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);

        // instantiate controllers
        ScenarioController scenarioController = new ScenarioController(this, false);
        this.comparatorController = new ComparatorController(this);
        this.benchmarkController = new BenchmarkController(this, mainView);

        // populate menus and tool bars
        mainMenuView.addActionButtons(scenarioController.getActionGroup().createMainMenuButtons());
        toolBarView.addActionButtons(scenarioController.getActionGroup().createToolBarButtons());
        toolBarView.addActionButtons(comparatorController.getActionGroup().createToolBarButtons());
        JMenu[] menus = {
                scenarioController.getActionGroup().createMenu(),
                comparatorController.getActionGroup().createMenu(), //   analyserController.getActionGroup().createMenu()
                getActionGroup().createMenu()
        };
        menuView.addActionButtons(menus);

        //sets the initial perspective
        mainView.switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
    }

    @Override
    public void forgetScenario() {
        for (int side = 0; side < 2; side++) {
            comparatorView.getBoardView(side).flush();
        }
//        mainView.switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);
    }

    @Override
    public void newScenario(Scenario scenario, UserRole userRole) {
        // Initialize GraphicsModel
        GraphicsModel.INSTANCE.initialize(scenario.getBoard());
        // pass the scenario to the engine controller
        comparatorController.setScenario(scenario);
        // change the GUI to show the pathfinding comparison perspective
        mainView.switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
    }

    @Override
    public Container getGUIContainer() {
        return mainView.getContentPane();
    }

    @Override
    public ComparatorViewer getPathfinderComparatorView() {
        return comparatorView;
    }

    @Override
    public BenchmarkViewer getPathfinderBenchmarkView() {
        return benchmarkView;
    }

    @Override
    public ActionGroup getActionGroup() {
        return actions;
    }

    private class ComparatorPerspectiveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
        }
    }

    private class BenchmarkPerspectiveActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            mainView.switchPerspective(PathfinderToolsViewer.BENCHMARK_PERSPECTIVE);
        }
    }
}
