package ares.application.player;

import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.player.boundaries.viewers.PlayerViewer;
import ares.application.player.controllers.WeGoPlayerController;
import ares.application.shared.views.BoardView;
import ares.application.shared.views.MessagesView;
import ares.application.shared.views.OOBView;
import ares.application.shared.views.MainMenuView;
import ares.application.shared.views.ToolBarView;
import ares.application.shared.views.InfoView;
import ares.application.shared.views.MenuBarView;
import ares.application.shared.views.MiniMapView;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.WindowUtil;
import ares.application.shared.views.AbstractView;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.InfoViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import ares.application.shared.boundaries.viewers.MiniMapViewer;
import ares.application.shared.boundaries.viewers.OOBViewer;
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
    private MainMenuView mainMenuV;
    private MenuBarView menuV;
    private InfoView infoV;
    private OOBView oobV;
    private BoardView boardV;
    private MessagesView messagesV;
    private ToolBarView toolBarV;
    private MiniMapView miniMapV;

    @Override
    protected JFrame layout() {
        mainMenuV = new MainMenuView();
        menuV = new MenuBarView();
        infoV = new InfoView();
        oobV = new OOBView();
        boardV = new BoardView();
        messagesV = new MessagesView();
        toolBarV = new ToolBarView();
        miniMapV = new MiniMapView();
        JFrame mainFrame = ComponentFactory.frame("Ares Player", menuV.getContentPane(), toolBarV.getContentPane());
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
//        menuV.getContentPane().setVisible(false);
//        toolBarV.getContentPane().setVisible(false);
        Container container = mainFrame.getContentPane();
        boardV.getContentPane().setPreferredSize(getBoardPaneDimension(container));
        infoV.getContentPane().setPreferredSize(getInfoPaneDimension(container));
        oobV.getContentPane().setPreferredSize(getOOBPaneDimension(container));
        miniMapV.getContentPane().setPreferredSize(getMiniMapPaneDimension(container));
        messagesV.getContentPane().setPreferredSize(getMessagesPaneDimension(container));

        splitVert = ComponentFactory.verticalSplitPane(true, boardV.getContentPane(), messagesV.getContentPane(), 1);
        splitVert2 = ComponentFactory.verticalSplitPane(true, miniMapV.getContentPane(), oobV.getContentPane(), 0);
        splitHoriz = ComponentFactory.horizontalSplitPane(true, infoV.getContentPane(), splitVert, 0);
        splitHoriz2 = ComponentFactory.horizontalSplitPane(true, splitHoriz, splitVert2, 1);


        cards = new JPanel(new CardLayout());
        cards.add(mainMenuV.getContentPane(), PlayerViewer.MAIN_MENU_PERSPECTIVE);
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
                menuV.setVisible(false);
                toolBarV.setVisible(false);
                break;
            case PlayerViewer.PLAYER_PERSPECTIVE:
                menuV.setVisible(true);
                toolBarV.setVisible(true);
                break;
        }
    }

    private Dimension getMiniMapPaneDimension(Container container) {
        return new Dimension(OOB_VIEW_WIDTH, MINIMAP_VIEW_HEIGHT);
    }

    private Dimension getOOBPaneDimension(Container container) {
        return new Dimension(OOB_VIEW_WIDTH, container.getHeight() - MINIMAP_VIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getInfoPaneDimension(Container container) {
        return new Dimension(INFO_VIEW_WIDTH, container.getHeight());
    }

    private Dimension getBoardPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_VIEW_WIDTH - OOB_VIEW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE,
                container.getHeight() - MESSAGES_WIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getMessagesPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_VIEW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE, MESSAGES_WIEW_HEIGHT);
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
        return boardV;
    }

    @Override
    public InfoViewer getInfoView() {
        return infoV;
    }

    @Override
    public ActionBarViewer<JButton> getToolBarView() {
        return toolBarV;
    }

    @Override
    public ActionBarViewer<JMenu> getMenuView() {
        return menuV;
    }

    @Override
    public MessagesViewer getMessagesView() {
        return messagesV;
    }

    @Override
    public OOBViewer getOobView() {
        return oobV;
    }

    @Override
    public MiniMapViewer getMiniMapView() {
        return miniMapV;
    }

    @Override
    public ActionBarViewer<JButton> getMainMenuView() {
        return mainMenuV;
    }
}
