package ares.application.shared.boundaries.viewers;

import ares.application.shared.gui.views.View;
import javax.swing.AbstractButton;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ActionBarViewer<T extends AbstractButton> extends View {

//    void setActionEnabled(String actionName, boolean enabled);

    void addActionButton(T actionButton);
    
    void addActionButtons(T[] actionButton);
}
