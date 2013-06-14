package ares.application.player;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.player.boundaries.viewers.PlayerViewer;
import ares.application.player.controllers.WeGoPlayerController;
import ares.application.player.views.PlayerBoardView;
import ares.application.shared.gui.views.MessagesView;
import ares.application.shared.gui.views.OOBView;
import ares.application.shared.gui.views.MainMenuView;
import ares.application.shared.gui.views.ToolBarView;
import ares.application.shared.gui.views.InfoView;
import ares.application.shared.gui.views.MenuBarView;
import ares.application.shared.gui.views.MiniMapView;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.WindowUtil;
import ares.application.shared.gui.views.AbstractView;
import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.boundaries.viewers.MenuBarViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import ares.application.shared.boundaries.viewers.OOBViewer;
import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.boundaries.viewers.ToolBarViewer;
import ares.application.shared.gui.views.TerrainInfoView;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public final class AresPlayerGUI extends AbstractView<JFrame> implements PlayerViewer {

    private static final int INFO_VIEW_WIDTH = 250;
    private static final int OOB_VIEW_WIDTH = 200;
    private static final int MINIMAP_VIEW_HEIGHT = 150;
    private static final int MESSAGES_WIEW_HEIGHT = 150;
    private JSplitPane splitHoriz;
    private JSplitPane splitVert;
    private JSplitPane splitHoriz2;
    private JSplitPane splitVert2;
    private JPanel cards;
    private MainMenuView mainMenuView;
    private MenuBarView menuView;
    private InfoView infoView;
    private OOBView oobView;
    private PlayerBoardView boardView;
    private MessagesView messagesView;
    private ToolBarView toolBarView;
    private MiniMapView miniMapView;

    @Override
    protected JFrame layout() {
        mainMenuView = new MainMenuView();
        menuView = new MenuBarView();
        infoView = new InfoView();
        oobView = new OOBView();
        boardView = new PlayerBoardView();
        messagesView = new MessagesView();
        toolBarView = new ToolBarView();
        miniMapView = new MiniMapView();
        JFrame mainFrame = ComponentFactory.frame("Ares Player", menuView.getContentPane(), toolBarView.getContentPane());
        // These dimensions are necessary when the frame is not fullscreen
        Dimension maxSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        Dimension defaultSize = new Dimension(1440, 900);
        Dimension minSize = new Dimension(800, 600);
        Dimension minimunSize = new Dimension(
                (maxSize.width < minSize.width ? maxSize.width : minSize.width),
                (maxSize.height < minSize.height ? maxSize.height : minSize.height));
        Dimension preferredSize = new Dimension(
                (maxSize.width < defaultSize.width ? maxSize.width : defaultSize.width),
                (maxSize.height < defaultSize.height ? maxSize.height : defaultSize.height));
        mainFrame.setMinimumSize(minimunSize);
        mainFrame.setPreferredSize(preferredSize);
        mainFrame.setMaximumSize(maxSize);
        mainFrame.setSize(preferredSize);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(true);

        boardView.setPreferredSize(getBoardPaneDimension(preferredSize));
        infoView.setPreferredSize(getInfoPaneDimension(preferredSize));
        oobView.setPreferredSize(getOOBPaneDimension(preferredSize));
        miniMapView.setPreferredSize(getMiniMapPaneDimension());
        messagesView.setPreferredSize(getMessagesPaneDimension(preferredSize));

        splitVert = ComponentFactory.verticalSplitPane(true, boardView.getContentPane(), messagesView.getContentPane(), 1);
        splitVert2 = ComponentFactory.verticalSplitPane(true, miniMapView.getContentPane(), oobView.getContentPane(), 0);
        splitHoriz = ComponentFactory.horizontalSplitPane(true, infoView.getContentPane(), splitVert, 0);
        splitHoriz2 = ComponentFactory.horizontalSplitPane(true, splitHoriz, splitVert2, 1);


        cards = new JPanel(new CardLayout());
        cards.add(mainMenuView.getContentPane(), PlayerViewer.MAIN_MENU_PERSPECTIVE);
        cards.add(splitHoriz2, PlayerViewer.PLAYER_PERSPECTIVE);
        mainFrame.add(cards);
        switchPerspective(PlayerViewer.MAIN_MENU_PERSPECTIVE);
        return mainFrame;
    }

    public void show() {
        WindowUtil.centerAndShow(contentPane);
    }

    @Override
    public void switchPerspective(String perspective) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, perspective);
        switch (perspective) {
            case PlayerViewer.MAIN_MENU_PERSPECTIVE:
                menuView.setVisible(false);
                toolBarView.setVisible(false);
                break;
            case PlayerViewer.PLAYER_PERSPECTIVE:
                menuView.setVisible(true);
                toolBarView.setVisible(true);
                break;
        }
    }

    private Dimension getMiniMapPaneDimension() {
        return new Dimension(OOB_VIEW_WIDTH, MINIMAP_VIEW_HEIGHT);
    }

    private Dimension getOOBPaneDimension(Dimension containerSize) {
        return new Dimension(OOB_VIEW_WIDTH, containerSize.height - MINIMAP_VIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getInfoPaneDimension(Dimension containerSize) {
        return new Dimension(INFO_VIEW_WIDTH, containerSize.height);
    }

    private Dimension getBoardPaneDimension(Dimension containerSize) {
        return new Dimension(containerSize.width - INFO_VIEW_WIDTH - OOB_VIEW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE,
                containerSize.height - MESSAGES_WIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getMessagesPaneDimension(Dimension containerSize) {
        return new Dimension(containerSize.width - INFO_VIEW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE, MESSAGES_WIEW_HEIGHT);
    }

    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AresPlayerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AresPlayerGUI mainView = new AresPlayerGUI();
                WeGoPlayerController mainController = new WeGoPlayerController(mainView);
                mainView.show();
            }
        });
    }

    @Override
    public BoardViewer getBoardView() {
        return boardView;
    }

    @Override
    public InfoViewer getInfoView() {
        return infoView;
    }

    @Override
    public ToolBarViewer getToolBarView() {
        return toolBarView;
    }

    @Override
    public MenuBarViewer getMenuView() {
        return menuView;
    }

    @Override
    public MessagesViewer getMessagesView() {
        return messagesView;
    }

    @Override
    public OOBViewer getOobView() {
        return oobView;
    }

    @Override
    public BoardViewer getMiniMapView() {
        return miniMapView;
    }

    @Override
    public PanelMenuViewer getMainMenuView() {
        return mainMenuView;
    }
}
