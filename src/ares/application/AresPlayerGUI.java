package ares.application;

import ares.application.controllers.WeGoPlayerController;
import ares.application.views.*;
import ares.platform.application.AbstractAresApplication;
import ares.platform.view.ComponentFactory;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class AresPlayerGUI extends AbstractAresApplication {

    private static final int INFO_VIEW_WIDTH = 250;
    private static final int OOB_VIEW_WIDTH = 200;
    private static final int MINIMAP_VIEW_HEIGHT = 150;
    private static final int MESSAGES_WIEW_HEIGHT = 150;
    public static final String MAIN_MENU_CARD = "Main";
    public static final String PLAY_CARD = "Play";
    private BoardView boardV;
    private InfoView infoV;
    private MenuBarView menuV;
    private MessagesView messagesV;
    private WelcomeScreenView welcomeScreenV;
    private ToolBarView toolBarV;
    private MiniMapView miniMapV;
    private JSplitPane splitHoriz;
    private JSplitPane splitVert;
    private JSplitPane splitHoriz2;
    private JSplitPane splitVert2;
    private JPanel cards;
    private WeGoPlayerController mainController;
    private OOBView oobV;

    public AresPlayerGUI() {
        super(); // creates layout
        // Create controllers and passes the views
        mainController = new WeGoPlayerController(this, boardV, infoV, oobV, menuV, messagesV, welcomeScreenV, toolBarV, miniMapV);
    }

    @Override
    protected JFrame layout() {
        menuV = new MenuBarView();
        infoV = new InfoView();
        oobV = new OOBView();
        boardV = new BoardView();
        messagesV = new MessagesView();
        welcomeScreenV = new WelcomeScreenView();
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
        menuV.getContentPane().setVisible(false);
//        menuV.getContentPane().setPreferredSize(new Dimension(preferredSize.width, 30));
//        menuV.getContentPane().setSize(new Dimension(preferredSize.width, 30));
        toolBarV.getContentPane().setVisible(false);
//        toolBarV.getContentPane().setPreferredSize(new Dimension(preferredSize.width, 30));
//        toolBarV.getContentPane().setSize(new Dimension(preferredSize.width, 30));
        Container container = mainFrame.getContentPane();
        boardV.getContentPane().setPreferredSize(getBoardPaneDimension(container));
        infoV.getContentPane().setPreferredSize(getInfoPaneDimension(container));
//        infoV.getContentPane().setMaximumSize(infoV.getContentPane().getPreferredSize());
        oobV.getContentPane().setPreferredSize(getOOBPaneDimension(container));
//        oobV.getContentPane().setMaximumSize(oobV.getContentPane().getPreferredSize());
        miniMapV.getContentPane().setPreferredSize(getMiniMapPaneDimension(container));
//        miniMapV.getContentPane().setMaximumSize(miniMapV.getContentPane().getPreferredSize());
        messagesV.getContentPane().setPreferredSize(getMessagesPaneDimension(container));

        splitVert = ComponentFactory.verticalSplitPane(true, boardV.getContentPane(), messagesV.getContentPane(), 1);
        splitVert2 = ComponentFactory.verticalSplitPane(true, miniMapV.getContentPane(), oobV.getContentPane(), 0);
        splitHoriz = ComponentFactory.horizontalSplitPane(true, infoV.getContentPane(), splitVert, 0);
        splitHoriz2 = ComponentFactory.horizontalSplitPane(true, splitHoriz, splitVert2, 1);


        cards = new JPanel(new CardLayout());
        cards.add(welcomeScreenV.getContentPane(), MAIN_MENU_CARD);
        cards.add(splitHoriz2, PLAY_CARD);
        mainFrame.add(cards);
        return mainFrame;
    }

    @Override
    public void switchCard(String cardName) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, cardName);
    }

    private Dimension getMiniMapPaneDimension(Container container) {
        return new Dimension(OOB_VIEW_WIDTH, MINIMAP_VIEW_HEIGHT);
    }

    private Dimension getOOBPaneDimension(Container container) {
        return new Dimension(OOB_VIEW_WIDTH, container.getHeight() - MINIMAP_VIEW_HEIGHT);
    }

    private Dimension getInfoPaneDimension(Container container) {
        return new Dimension(INFO_VIEW_WIDTH, container.getHeight());
    }

    private Dimension getBoardPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_VIEW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE, container.getHeight()
                - MESSAGES_WIEW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
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
                new AresPlayerGUI().show();
            }
        });
    }
}
