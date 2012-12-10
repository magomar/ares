package ares.application.boundaries.view;

import ares.platform.view.View;
import java.awt.event.ActionListener;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface CommandBarViewer extends View, ActionListener {

    public void setCommandEnabled(String name, boolean enabled);

    /**
     * Method to add a listener for a specific action command. This method is used by a controller to be notified of
     * user actions. Each controller has to register a single action listener for each command it wants to listen to.
     * However, many controllers can be listening to the very same command.
     *
     * @param actionCommand
     * @param actionListener
     */
    public void addActionListener(String actionCommand, ActionListener listener);

    public void removeActionListener(String actionCommand, ActionListener listener);
}
