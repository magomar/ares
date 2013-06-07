package ares.application.analyser.boundaries.viewers;

import ares.application.analyser.views.PathfinderComparatorView;
import ares.application.shared.gui.views.View;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfinderToolsViewer extends View {
    // Perspectives
    static final String MAIN_MENU_PERSPECTIVE = "Main";
    static final String COMPARATOR_PERSPECTIVE = "Comparator";
    static final String ANALYSER_PERSPECTIVE = "Analyser";


    void switchPerspective(String perspective);

    PathfinderComparatorView getComparatorView();
    
    ActionBarViewer<JButton> getToolBarView();

    ActionBarViewer<JMenu> getMenuView();

    ActionBarViewer<JButton> getMainMenuView();
}
