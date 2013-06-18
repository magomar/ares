package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.ToolBarViewer;
import ares.application.shared.gui.ComponentFactory;

import javax.swing.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class ToolBarView extends AbstractView<JToolBar> implements ToolBarViewer {

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
