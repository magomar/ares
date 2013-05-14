package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
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
}
