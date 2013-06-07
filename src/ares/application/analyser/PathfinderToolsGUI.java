package ares.application.analyser;

import ares.application.analyser.controllers.PathfinderToolsController;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.analyser.views.PathfinderComparatorView;
import ares.application.player.AresPlayerGUI;
import ares.application.shared.gui.ComponentFactory;
import ares.application.shared.gui.WindowUtil;
import ares.application.shared.gui.views.MainMenuView;
import ares.application.shared.gui.views.MenuBarView;
import ares.application.shared.gui.views.ToolBarView;
import ares.application.shared.gui.views.AbstractView;
import ares.application.shared.boundaries.viewers.ActionBarViewer;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderToolsGUI extends AbstractView<JFrame> implements PathfinderToolsViewer {

    private JPanel cards;
    private MainMenuView mainMenuV;
    private MenuBarView menuV;
    private ToolBarView toolBarV;
    private PathfinderComparatorView comparatorV;

    @Override
    protected JFrame layout() {
        mainMenuV = new MainMenuView();
        menuV = new MenuBarView();
        toolBarV = new ToolBarView();
        comparatorV = new PathfinderComparatorView();

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
//        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        mainFrame.setUndecorated(true);

        comparatorV.setPreferredSize(preferredSize);

        cards = new JPanel(new CardLayout());
        cards.add(mainMenuV.getContentPane(), PathfinderToolsViewer.MAIN_MENU_PERSPECTIVE);
        cards.add(comparatorV.getContentPane(), PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
        mainFrame.add(cards);
//        switchPerspective(PathfinderToolsViewer.MAIN_MENU_PERSPECTIVE);
        switchPerspective(PathfinderToolsViewer.COMPARATOR_PERSPECTIVE);
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
            case PathfinderToolsViewer.MAIN_MENU_PERSPECTIVE:
                menuV.setVisible(false);
                toolBarV.setVisible(false);
                break;
            case PathfinderToolsViewer.COMPARATOR_PERSPECTIVE:
                menuV.setVisible(true);
                toolBarV.setVisible(true);
                break;
            case PathfinderToolsViewer.ANALYSER_PERSPECTIVE:
                menuV.setVisible(true);
                toolBarV.setVisible(true);
                break;
        }
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
                PathfinderToolsGUI mainView = new PathfinderToolsGUI();
                PathfinderToolsController mainController = new PathfinderToolsController(mainView);
                mainView.show();
            }
        });
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
    public ActionBarViewer<JButton> getMainMenuView() {
        return mainMenuV;
    }

    @Override
    public PathfinderComparatorView getComparatorView() {
        return comparatorV;
    }

}
