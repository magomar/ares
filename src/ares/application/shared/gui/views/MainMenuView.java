package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.ActionBarViewer;
import ares.application.shared.gui.components.MainMenuPanel;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MainMenuView extends AbstractView<JPanel> implements ActionBarViewer<JPanel, JButton> {

    @Override
    protected JPanel layout() {
        JPanel buttonsPanel = new MainMenuPanel();
        return buttonsPanel;
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
