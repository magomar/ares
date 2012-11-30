package ares.application.boundaries.view;

import ares.platform.view.View;
import java.awt.event.ActionListener;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface MenuBarViewer extends View, ActionListener {

    public void setMenuElementEnabled(String name, boolean enabled);
    public void addActionListener(String actionCommand, ActionListener listener);

}
