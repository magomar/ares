package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.application.gui.menu.WelcomeScreen;
import ares.platform.view.AbstractView;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WelcomeScreenView extends AbstractView<JPanel> implements ActionBarViewer<JButton> {

    @Override
    protected JPanel layout() {
        JPanel buttonsPanel = new WelcomeScreen();
        return buttonsPanel;
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
//    public void setActionEnabled(String name, boolean enabled) {
//        getButton(name).setEnabled(enabled);
//    }

    @Override
    public void addActionButton(JButton actionButton) {
        contentPane.add(actionButton);
    }

  
}
