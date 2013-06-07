package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.gui.ComponentFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MenuBarView extends AbstractView<JMenuBar> implements ActionBarViewer<JMenu> {

    @Override
    protected JMenuBar layout() {
        JMenuBar jmenuBar = ComponentFactory.menuBar();
        return jmenuBar;
    }

    @Override
    public void addActionButton(JMenu menu) {
        contentPane.add(menu);
    }

    @Override
    public void addActionButtons(JMenu[] actionButton) {
        for (JMenu jMenu : actionButton) {
            contentPane.add(jMenu);
        }
    }
}
