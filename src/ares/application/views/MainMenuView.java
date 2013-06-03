package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.application.gui.components.WelcomeScreen;
import ares.platform.view.AbstractView;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MainMenuView extends AbstractView<JPanel> implements ActionBarViewer<JButton> {

    @Override
    protected JPanel layout() {
        JPanel buttonsPanel = new WelcomeScreen();
        return buttonsPanel;
    }

    @Override
    public void addActionButton(JButton actionButton) {
        contentPane.add(actionButton);
    }

  
}
