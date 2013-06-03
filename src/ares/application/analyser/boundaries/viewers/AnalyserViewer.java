package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.View;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface AnalyserViewer extends View {
    // Perspectives

    static final String MAIN_MENU_PERSPECTIVE = "Main";
    static final String COMPARATOR_PERSPECTIVE = "Comparator";
    static final String ANALYSER_PERSPECTIVE = "Analyser";

    /**
     * Switches to one among several available perspectives Each perspective is a different combination of visible and
     * invisible views
     */
    void switchPerspective(String perspective);

    BoardViewer getLeftBoardView();
    
    BoardViewer getRightBoardView();
    
    ActionBarViewer<JButton> getToolBarView();

    ActionBarViewer<JMenu> getMenuView();

    MessagesViewer getMessagesView();

    ActionBarViewer<JButton> getMainMenuView();
}
