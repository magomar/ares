package ares.application.player.boundaries.viewers;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.shared.gui.views.View;
import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.boundaries.viewers.MenuBarViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import ares.application.shared.boundaries.viewers.OOBViewer;
import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.boundaries.viewers.ToolBarViewer;
import javax.swing.JFrame;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface PlayerViewer extends View<JFrame> {

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

    ToolBarViewer getToolBarView();

    MenuBarViewer getMenuView();

    MessagesViewer getMessagesView();

    OOBViewer getOobView();

    BoardViewer getMiniMapView();

    PanelMenuViewer getMainMenuView();
}
