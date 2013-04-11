package ares.application.views;

import ares.application.boundaries.view.CommandBarViewer;
import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.commands.ViewCommands;
import ares.application.gui.main.AresMenus;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.MenuElement;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MenuBarView extends AbstractView<JMenuBar> implements CommandBarViewer {

    protected Map<String, EventListenerList> actionListeners = new HashMap<>();

    @Override
    protected JMenuBar layout() {
        JMenu fileMenu = ComponentFactory.menu(AresMenus.FILE_MENU,
                ComponentFactory.menuItem(FileCommands.OPEN_SCENARIO, this),
                ComponentFactory.menuItem(FileCommands.CLOSE_SCENARIO, this, false),
                ComponentFactory.menuItem(FileCommands.EXIT, this));
        JMenu viewMenu = ComponentFactory.menu(AresMenus.VIEW_MENU, false,
                ComponentFactory.menuItem(ViewCommands.SHOW_GRID, this),
                ComponentFactory.menuItem(ViewCommands.HIDE_UNITS, this));
        JMenu engineMenu = ComponentFactory.menu(AresMenus.ENGINE_MENU, false,
//                ComponentFactory.menuItem(EngineCommands.RESUME, this, false),
                ComponentFactory.menuItem(EngineCommands.TURN, this, true),
                ComponentFactory.menuItem(EngineCommands.STEP, this, true),
                ComponentFactory.menuItem(EngineCommands.PAUSE, this, false));
        JMenuBar jmenuBar = ComponentFactory.menuBar(fileMenu, viewMenu, engineMenu);
        return jmenuBar;
    }

    protected MenuElement getMenuElement(String elementName) {
        return getMenuElement(elementName, contentPane);
    }

    private MenuElement getMenuElement(String elementName, MenuElement menuElement) {
        Component component = menuElement.getComponent();
        MenuElement found = null;
        if (elementName.equals(component.getName())) {
            found = menuElement;
        } else {
            for (MenuElement child : menuElement.getSubElements()) {
                found = getMenuElement(elementName, child);
                if (found != null) {
                    break;
                }
            }
        }
        return found;
    }

    @Override
    public void setCommandEnabled(String name, boolean enabled) {
        getMenuElement(name).getComponent().setEnabled(enabled);

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
