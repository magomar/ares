package ares.application.gui.main;

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

    private static final int INFO_WINDOW_WIDTH = 250;
    private static final int OOB_WINDOW_WIDTH = 200;
    private static final int MESSAGES_WINDOW_HEIGHT = 150;
    public static final String MAIN_MENU_CARD = "Main";
    public static final String PLAY_CARD = "Play";
    private BoardView boardV;
    private UnitInfoView unitV;
    private MenuBarView menuV;
    private MessagesView messagesV;
    private WelcomeScreenView welcomeScreenV;
    private ToolBarView toolBarV;
    private JSplitPane splitHoriz;
    private JSplitPane splitVert;
    private JSplitPane splitHoriz2;
    private JPanel cards;
    private WeGoPlayerController mainController;
    private OOBView oobV;

    public AresPlayerGUI() {
        super(); // creates layout
        // Create controllers and passes the views
        mainController = new WeGoPlayerController(this, boardV, unitV, oobV, menuV, messagesV, welcomeScreenV, toolBarV);
    }

    @Override
    protected JFrame layout() {
        menuV = new MenuBarView();
        unitV = new UnitInfoView();
        oobV = new OOBView();
        boardV = new BoardView();
        messagesV = new MessagesView();
        welcomeScreenV = new WelcomeScreenView();
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
        menuV.getContentPane().setVisible(false);
//        menuV.getContentPane().setPreferredSize(new Dimension(preferredSize.width, 30));
//        menuV.getContentPane().setSize(new Dimension(preferredSize.width, 30));
        toolBarV.getContentPane().setVisible(false);
//        toolBarV.getContentPane().setPreferredSize(new Dimension(preferredSize.width, 30));
//        toolBarV.getContentPane().setSize(new Dimension(preferredSize.width, 30));
        boardV.getContentPane().setPreferredSize(getBoardPaneDimension(mainFrame.getContentPane()));
        unitV.getContentPane().setPreferredSize(getInfoPaneDimension(mainFrame.getContentPane()));
        unitV.getContentPane().setMaximumSize(unitV.getContentPane().getPreferredSize());
        oobV.getContentPane().setPreferredSize(getOOBPaneDimension(mainFrame.getContentPane()));
        oobV.getContentPane().setMaximumSize(oobV.getContentPane().getPreferredSize());
        messagesV.getContentPane().setPreferredSize(getMessagesPaneDimension(mainFrame.getContentPane()));

        splitVert = ComponentFactory.verticalSplitPane(true, boardV.getContentPane(), messagesV.getContentPane(),1);    
        splitHoriz = ComponentFactory.horizontalSplitPane(true, unitV.getContentPane(), splitVert,0);
        splitHoriz2 = ComponentFactory.horizontalSplitPane(true, splitHoriz, oobV.getContentPane(), 1);

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

    private Dimension getOOBPaneDimension(Container container) {
        return new Dimension(OOB_WINDOW_WIDTH, container.getWidth());
    }

    private Dimension getInfoPaneDimension(Container container) {
        return new Dimension(INFO_WINDOW_WIDTH, container.getHeight());
    }

    private Dimension getBoardPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_WINDOW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE, container.getHeight()
                - MESSAGES_WINDOW_HEIGHT - ComponentFactory.SPLIT_DIVIDER_SIZE);
    }

    private Dimension getMessagesPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_WINDOW_WIDTH - ComponentFactory.SPLIT_DIVIDER_SIZE, MESSAGES_WINDOW_HEIGHT);
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
