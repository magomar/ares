package ares.application.analyser.boundaries.viewers;

import ares.application.analyser.views.PathfinderComparatorView;
import ares.application.analyser.views.PathfinderBenchmarkView;
import ares.application.shared.boundaries.viewers.MenuBarViewer;
import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.boundaries.viewers.ToolBarViewer;
import ares.application.shared.gui.views.View;

import javax.swing.*;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderToolsViewer extends View<JFrame> {
    // Perspectives
    static final String MAIN_MENU_PERSPECTIVE = "Main";
    static final String COMPARATOR_PERSPECTIVE = "Comparator";
    static final String BENCHMARK_PERSPECTIVE = "Benchmark";


    void switchPerspective(String perspective);

    PathfinderComparatorView getComparatorView();
    
    PathfinderBenchmarkView getBenchmarkView();

    ToolBarViewer getToolBarView();

    MenuBarViewer getMenuView();

    PanelMenuViewer getMainMenuView();
}
