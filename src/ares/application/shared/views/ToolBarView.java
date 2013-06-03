package ares.application.shared.views;

import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.gui.ComponentFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ToolBarView extends AbstractView<JToolBar> implements ActionBarViewer<JButton> {

    @Override
    protected JToolBar layout() {
        JToolBar toolBar = ComponentFactory.toolBar("Tools");
        return toolBar;
    }

    @Override
    public void addActionButton(JButton actionButton) {
        contentPane.add(actionButton);
    }

    @Override
    public void addActionButtons(JButton[] actionButton) {
        for (JButton jButton : actionButton) {
            contentPane.add(jButton);
        }
    }
}
