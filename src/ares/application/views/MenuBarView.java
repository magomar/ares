package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.platform.view.AbstractView;
import ares.application.gui.ComponentFactory;
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
}
