package ares.application.views;

import ares.application.boundaries.view.CommandBarViewer;
import ares.application.commands.FileCommands;
import ares.application.graphics.menu.WelcomeScreen;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class WelcomeScreenView extends AbstractView<JPanel> implements CommandBarViewer {

    protected Map<String, EventListenerList> actionListeners = new HashMap<>();

    @Override
    protected JPanel layout() {
        JPanel buttonsPanel = new WelcomeScreen();
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.OPEN_SCENARIO, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.LOAD_SCENARIO, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.SETTINGS, this));
        buttonsPanel.add(ComponentFactory.translucidButton(FileCommands.EXIT, this));
        return buttonsPanel;
    }

    protected Component getMenuElement(String elementName) {
        for (Component button : contentPane.getComponents()) {
            if (elementName.equals(button.getName())) {
                return button;
            }
        }
        return null;
    }

    @Override
    public void setCommandEnabled(String name, boolean enabled) {
        getMenuElement(name).setEnabled(enabled);

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        ActionListener commandListeners[] = actionListeners.get(ae.getActionCommand()).getListeners(ActionListener.class);
        for (ActionListener actionListener : commandListeners) {
            actionListener.actionPerformed(ae);
        }
    }

    @Override
    public void addActionListener(String actionCommand, ActionListener actionListener) {
        if (actionListeners.containsKey(actionCommand)) {
            actionListeners.get(actionCommand).add(ActionListener.class, actionListener);
        } else {
            EventListenerList newEventListenerList = new EventListenerList();
            newEventListenerList.add(ActionListener.class, actionListener);
            actionListeners.put(actionCommand, newEventListenerList);
        }
    }

    @Override
    public void removeActionListener(String actionCommand, ActionListener actionListener) {
        if (actionListeners.containsKey(actionCommand)) {
            actionListeners.get(actionCommand).remove(ActionListener.class, actionListener);
        }
    }
}
