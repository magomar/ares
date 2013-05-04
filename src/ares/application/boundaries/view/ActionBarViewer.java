package ares.application.boundaries.view;

import ares.platform.view.View;
import javax.swing.AbstractButton;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ActionBarViewer<T extends AbstractButton> extends View {

//    void setActionEnabled(String actionName, boolean enabled);

    void addActionButton(T actionButton);
}
