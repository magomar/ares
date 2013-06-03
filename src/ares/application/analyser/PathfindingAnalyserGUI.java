package ares.application.analyser;

import ares.application.analyser.controllers.PathfindingAnalyserController;
import ares.application.analyser.boundaries.viewers.AnalyserViewer;
import ares.application.player.AresPlayerGUI;
import ares.application.shared.boundaries.viewers.BoardViewer;
import ares.application.player.boundaries.viewers.PlayerViewer;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.WindowUtil;
import ares.application.shared.views.BoardView;
import ares.application.shared.views.MainMenuView;
import ares.application.shared.views.MenuBarView;
import ares.application.shared.views.MessagesView;
import ares.application.shared.views.ToolBarView;
import ares.application.shared.views.AbstractView;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.boundaries.viewers.MessagesViewer;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfindingAnalyserGUI extends AbstractView<JFrame> implements AnalyserViewer {

    private static final int MESSAGES_WIEW_HEIGHT = 150;
    private JSplitPane splitHoriz;
    private JSplitPane splitVert;
    private JPanel cards;
    private MainMenuView mainMenuV;
    private MenuBarView menuV;
    private BoardView leftBoardV;
    private BoardView rightBoardV;
    private MessagesView messagesV;
    private ToolBarView toolBarV;

    @Override
    protected JFrame layout() {
        mainMenuV = new MainMenuView();
        menuV = new MenuBarView();
        leftBoardV = new BoardView();
        rightBoardV = new BoardView();
        messagesV = new MessagesView();
        toolBarV = new ToolBarView();
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
        leftBoardV.getContentPane().setPreferredSize(getBoardPaneDimension(container));
        rightBoardV.getContentPane().setPreferredSize(getBoardPaneDimension(container));
        messagesV.getContentPane().setPreferredSize(getMessagesPaneDimension(container));

        splitHoriz = ComponentFactory.horizontalSplitPane(true, leftBoardV.getContentPane(), rightBoardV.getContentPane(), 0.5);
        splitVert = ComponentFactory.verticalSplitPane(true, splitHoriz, messagesV.getContentPane(), 1);

        cards = new JPanel(new CardLayout());
        cards.add(mainMenuV.getContentPane(), AnalyserViewer.MAIN_MENU_PERSPECTIVE);
        cards.add(splitVert, AnalyserViewer.COMPARATOR_PERSPECTIVE);
        mainFrame.add(cards);
//        switchPerspective(AnalyserViewer.MAIN_MENU_PERSPECTIVE);
        switchPerspective(AnalyserViewer.COMPARATOR_PERSPECTIVE);
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
            case AnalyserViewer.MAIN_MENU_PERSPECTIVE:
                menuV.setVisible(false);
                toolBarV.setVisible(false);
                break;
            case AnalyserViewer.COMPARATOR_PERSPECTIVE:
                menuV.setVisible(true);
                toolBarV.setVisible(true);
                break;
            case AnalyserViewer.ANALYSER_PERSPECTIVE:
                menuV.setVisible(true);
                toolBarV.setVisible(true);
                break;
        }
    }

    private Dimension getBoardPaneDimension(Container container) {
        return new Dimension(container.getWidth() / 2 - ComponentFactory.SPLIT_DIVIDER_SIZE,
                container.getHeight() - MESSAGES_WIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getMessagesPaneDimension(Container container) {
        return new Dimension(container.getWidth(), MESSAGES_WIEW_HEIGHT);
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
                PathfindingAnalyserGUI mainView = new PathfindingAnalyserGUI();
                PathfindingAnalyserController mainController = new PathfindingAnalyserController(mainView);
                mainView.show();
            }
        });
    }

    @Override
    public BoardViewer getLeftBoardView() {
        return leftBoardV;
    }

    @Override
    public BoardViewer getRightBoardView() {
        return rightBoardV;
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
    public ActionBarViewer<JButton> getMainMenuView() {
        return mainMenuV;
    }
}
