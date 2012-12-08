package ares.application.player;

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

    public static final int INFO_WINDOW_WIDTH = 250;
    public static final int MESSAGES_WINDOW_HEIGHT = 150;
    public static final int SPLIT_DIVIDER_SIZE = 5;
    private BoardView boardV;
    private UnitInfoView unitV;
    private MenuBarView menuV;
    private MessagesView messagesV;
    private JSplitPane splitHoriz;
    private JSplitPane splitVert;

    public AresPlayerGUI() {
        super(); // creates layout
        // Create controllers
        WeGoPlayerController mainController = new WeGoPlayerController(this, boardV, unitV, menuV, messagesV);
    }

    @Override
    protected JFrame layout() {
        menuV = new MenuBarView();
        unitV = new UnitInfoView();
        boardV = new BoardView();
        messagesV = new MessagesView();
        JFrame mainFrame = ComponentFactory.frame("Ares Player", menuV.getContentPane(), null);
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
        menuV.getContentPane().setPreferredSize(new Dimension(preferredSize.width,30));
        menuV.getContentPane().setSize(new Dimension(preferredSize.width,30));
        menuV.getContentPane().setLocation(0, -15);
        menuV.getContentPane().setVisible(false);
        boardV.getContentPane().setPreferredSize(getBoardPaneDimension(mainFrame.getContentPane()));
        unitV.getContentPane().setPreferredSize(getInfoPaneDimension(mainFrame.getContentPane()));
        unitV.getContentPane().setMaximumSize(unitV.getContentPane().getPreferredSize());
        messagesV.getContentPane().setPreferredSize(getMessagesPaneDimension(mainFrame.getContentPane()));

        splitVert = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, boardV.getContentPane(), messagesV.getContentPane());
        splitVert.setResizeWeight(1);
        splitHoriz = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, unitV.getContentPane(), splitVert);
        splitHoriz.setResizeWeight(0);
        splitVert.setDividerSize(SPLIT_DIVIDER_SIZE);
        splitHoriz.setDividerSize(SPLIT_DIVIDER_SIZE);
        mainFrame.add(splitHoriz);
        return mainFrame;
    }

    @Override
    public void setMainPaneVisible(boolean visible) {
        splitHoriz.setVisible(visible);
//        if (visible) {
//            splitHoriz.resetToPreferredSizes();
//            splitVert.resetToPreferredSizes();
//        }
        show();
//        SwingUtilities.updateComponentTreeUI(contentPane);
    }

    private Dimension getInfoPaneDimension(Container container) {
        return new Dimension(INFO_WINDOW_WIDTH, container.getHeight());
    }

    private Dimension getBoardPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_WINDOW_WIDTH - SPLIT_DIVIDER_SIZE, container.getHeight()
                - MESSAGES_WINDOW_HEIGHT - SPLIT_DIVIDER_SIZE);
    }

    private Dimension getMessagesPaneDimension(Container container) {
        return new Dimension(container.getWidth() - INFO_WINDOW_WIDTH - SPLIT_DIVIDER_SIZE, MESSAGES_WINDOW_HEIGHT);
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
