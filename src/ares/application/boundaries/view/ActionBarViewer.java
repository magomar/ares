package ares.application.boundaries.view;

import ares.platform.view.View;
import java.awt.Component;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ActionBarViewer<T extends Component> extends View {

    void setActionEnabled(String actionName, boolean enabled);

    void addActionButton(T actionButton);
}
