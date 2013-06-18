package ares.application.shared.gui.views;

import ares.application.shared.boundaries.viewers.PanelMenuViewer;
import ares.application.shared.gui.components.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MainMenuView extends AbstractView<JPanel> implements PanelMenuViewer {

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
    public void addActionButtons(JButton[] actionButtons) {
        for (JButton jButton : actionButtons) {
            contentPane.add(jButton);
            contentPane.add(Box.createRigidArea(new Dimension(0,5)));
        }
    }
}
