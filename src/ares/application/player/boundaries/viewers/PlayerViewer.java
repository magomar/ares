package ares.application.player.boundaries.viewers;

import ares.application.shared.views.View;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import ares.application.shared.boundaries.viewers.MiniMapViewer;
import ares.application.shared.boundaries.viewers.OOBViewer;
import javax.swing.JButton;
import javax.swing.JMenu;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface PlayerViewer extends View {

    // Perspectives
    static final String MAIN_MENU_PERSPECTIVE = "Main";
    static final String PLAYER_PERSPECTIVE = "Player";

    /**
     * Switches to one among several available perspectives Each perspective is a different combination of visible and
     * invisible views
     */
    void switchPerspective(String perspective);

    BoardViewer getBoardView();

    InfoViewer getInfoView();

    ActionBarViewer<JButton> getToolBarView();

    ActionBarViewer<JMenu> getMenuView();

    MessagesViewer getMessagesView();

    OOBViewer getOobView();

    MiniMapViewer getMiniMapView();

    ActionBarViewer<JButton> getMainMenuView();
}
