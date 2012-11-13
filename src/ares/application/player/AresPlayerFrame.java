package ares.application.player;

import ares.application.controllers.FileIOController;
import ares.application.controllers.RealTimeEngineController;
import ares.application.views.BoardView;
import ares.application.views.MenuBarView;
import ares.application.views.MessagesView;
import ares.application.views.UnitInfoView;
import ares.engine.realtime.RealTimeEngine;
import ares.platform.application.AbstractAresApplication;
import ares.platform.controller.AbstractController;
import ares.platform.model.AbstractModel;
import ares.platform.view.ComponentFactory;
import ares.platform.view.InternalFrameView;
import ares.platform.view.WindowUtil;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class AresPlayerFrame extends AbstractAresApplication {

    private static JDesktopPane desktopPane;
    public static final int INFO_WINDOW_WIDTH = 250;
    public static final int MESSAGES_WINDOW_HEIGHT = 150;


    @Override
    protected void configureMVC() {
        desktopPane = new JDesktopPane();

        // Create Views
        addView(MenuBarView.class, new MenuBarView());
        addView(MessagesView.class, new InternalFrameView(new MessagesView(), desktopPane));
        addView(UnitInfoView.class, new InternalFrameView(new UnitInfoView(), desktopPane));
        addView(BoardView.class, new InternalFrameView( new BoardView(), desktopPane));

        // Create controllers
        AbstractController engineController = new RealTimeEngineController();
        AbstractController fileController = new FileIOController(this);

        //  add views to controllers
        engineController.addView(MenuBarView.class, getView(MenuBarView.class));
        engineController.addView(MessagesView.class, getView(MessagesView.class));
        engineController.addView(UnitInfoView.class, getView(UnitInfoView.class));
        engineController.addView(BoardView.class, getView(BoardView.class));
        fileController.addView(MenuBarView.class, getView(MenuBarView.class));
        fileController.addView(MessagesView.class, getView(MessagesView.class));
        fileController.addView(UnitInfoView.class, getView(UnitInfoView.class));
        fileController.addView(BoardView.class, getView(BoardView.class));

        // create and add models to controllers
        AbstractModel engineModel = new RealTimeEngine();
        engineController.addModel(RealTimeEngine.class, engineModel);
        fileController.addModel(RealTimeEngine.class, engineModel);

        // Initialize controllers [controllers register listeners to be notified about views and model events]
        engineController.initialize();
        fileController.initialize();


    }

    @Override
    protected JFrame layout() {
        JFrame mainFrame = ComponentFactory.frame("Ares Player", desktopPane, getView(MenuBarView.class).getContentPane(), null);
        Dimension maxSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
        Dimension defaultSize = new Dimension(1280, 800);
        Dimension minSize = new Dimension(
                (maxSize.width > defaultSize.width ? defaultSize.width : maxSize.width),
                (maxSize.height > defaultSize.height ? defaultSize.height : maxSize.height));
        Dimension preferredSize = new Dimension(
                (maxSize.width < defaultSize.width ? defaultSize.width : maxSize.width),
                (maxSize.height < defaultSize.height ? defaultSize.height : maxSize.height));
        mainFrame.setMinimumSize(minSize);
        mainFrame.setPreferredSize(minSize);
        mainFrame.setMaximumSize(maxSize);
        mainFrame.setMaximizedBounds(new Rectangle(preferredSize));

        // We have to show the main frame before setting the preferred size & bounds of its internal frames
        // because these depend on the desktopPane, whose size is set when the main frame is shown
        WindowUtil.showFrame(mainFrame); 

        // information frame
        InternalFrameView<UnitInfoView> infoFrameView = getInternalFrameView(UnitInfoView.class);
        JInternalFrame infoFrame = infoFrameView.getInternalFrame();
        infoFrame.setPreferredSize(getDefaultInfoFrameBounds().getSize());
        infoFrame.setBounds(getDefaultInfoFrameBounds());
        infoFrame.setTitle("Info");

        // messages frame
        InternalFrameView<MessagesView> messagesFrameView = getInternalFrameView(MessagesView.class);
        JInternalFrame messagesFrame = messagesFrameView.getInternalFrame();
        messagesFrame.setPreferredSize(getDefaultMessagesFrameBounds().getSize());
        messagesFrame.setBounds(getDefaultMessagesFrameBounds());
        messagesFrame.setTitle("Messages");

        //  board frame
        InternalFrameView<BoardView> boardFrameView = getInternalFrameView(BoardView.class);
        JInternalFrame boardFrame = boardFrameView.getInternalFrame();
        boardFrame.setPreferredSize(getDefaultBoardFrameBounds().getSize());
        boardFrame.setBounds(getDefaultBoardFrameBounds());
        boardFrame.setTitle("Board");

        return mainFrame;
    }

//    public Dimension getDesktopSize() {
//        return desktopPane.getSize();
//    }
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AresPlayerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AresPlayerFrame().show();
            }
        });
    }

    private Rectangle getDefaultBoardFrameBounds() {
        Dimension d = desktopPane.getSize();
        Rectangle bounds = new Rectangle(0, 0, d.width - AresPlayerFrame.INFO_WINDOW_WIDTH, d.height - AresPlayerFrame.MESSAGES_WINDOW_HEIGHT);
        return bounds;
    }

    private Rectangle getDefaultMessagesFrameBounds() {
        Dimension d = desktopPane.getSize();
        Rectangle bounds = new Rectangle(0, d.height - AresPlayerFrame.MESSAGES_WINDOW_HEIGHT, d.width - AresPlayerFrame.INFO_WINDOW_WIDTH, AresPlayerFrame.MESSAGES_WINDOW_HEIGHT);
        return bounds;
    }

    private Rectangle getDefaultInfoFrameBounds() {
        Dimension d = desktopPane.getSize();
        Rectangle bounds = new Rectangle(d.width - AresPlayerFrame.INFO_WINDOW_WIDTH, 0, AresPlayerFrame.INFO_WINDOW_WIDTH, d.height);
        return bounds;
    }
}
