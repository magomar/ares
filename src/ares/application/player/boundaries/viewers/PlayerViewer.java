package ares.application.player.boundaries.viewers;

import ares.application.shared.boundaries.viewers.*;
import ares.application.shared.gui.views.View;

import javax.swing.*;

/**
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
