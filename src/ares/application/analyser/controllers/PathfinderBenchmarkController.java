package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;
import ares.application.analyser.boundaries.viewers.PathfinderBenchmarkViewer;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.shared.commands.ActionGroup;
import ares.application.shared.commands.AresCommandGroup;
import ares.application.shared.commands.CommandAction;
import ares.application.shared.commands.CommandGroup;
import ares.application.shared.commands.PathfinderToolsCommands;
import ares.application.shared.controllers.ActionController;
import ares.application.shared.controllers.ScenarioController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;
import javax.swing.Action;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderBenchmarkController implements ActionController {

    private static final Logger LOG = Logger.getLogger(PathfinderBenchmarkController.class.getName());
    private final PathfinderToolsViewer mainView;
    private final PathfinderBenchmarkViewer benchmarkView;
    private final Action comparatorPerspective = new CommandAction(PathfinderToolsCommands.COMPARATOR_PERSPECTIVE, new ComparatorPerspectiveActionListener());
    private final Action benchMarkPerspective = new CommandAction(PathfinderToolsCommands.BENCHMARK_PERSPECTIVE, new BenchmarkPerspectiveActionListener());
    private final ActionGroup actions;
    
    public PathfinderBenchmarkController(PathfinderBenchmarkInteractor interactor, PathfinderToolsViewer mainView) {
        this.mainView = mainView;
        benchmarkView = interactor.getPathfinderBenchmarkView();
        
        Action[] viewActions = {comparatorPerspective, benchMarkPerspective};
        CommandGroup group = AresCommandGroup.PATHFINDER_TOOLS;
        actions = new ActionGroup(group.getName(), group.getText(), group.getMnemonic(), viewActions);
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
