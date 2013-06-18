package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;

import javax.swing.*;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ActionBarViewer<C extends JComponent, T extends AbstractButton> extends View<C> {

    void addActionButton(T actionButton);

    void addActionButtons(T[] actionButton);
}
