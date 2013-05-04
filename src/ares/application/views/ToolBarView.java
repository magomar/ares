package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
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

//    protected JButton getButton(String elementName) {
//        for (Component component : contentPane.getComponents()) {
//            if (component.getName().equals(elementName)) {
//                JButton button = (JButton) component;
//                return button;
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void setActionEnabled(String actionName, boolean enabled) {
//        getButton(actionName).setEnabled(enabled);
//
//    }

    @Override
    public void addActionButton(JButton actionButton) {
        contentPane.add(actionButton);
    }
}
