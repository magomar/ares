package ares.application.views;

import ares.application.commands.EngineCommands;
import ares.application.commands.FileCommands;
import ares.application.commands.ViewCommands;
import ares.application.player.AresMenus;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.MenuElement;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MenuBarView extends AbstractView<JMenuBar> {

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
                ComponentFactory.menuItem(EngineCommands.START, this),
                ComponentFactory.menuItem(EngineCommands.NEXT, this),
                ComponentFactory.menuItem(EngineCommands.STOP, this));
        JMenuBar jmenuBar = ComponentFactory.menuBar(fileMenu, viewMenu, engineMenu);
        return jmenuBar;
    }
    
    public MenuElement getMenuElement(String elementName) {
        return getMenuElement(elementName, getContentPane());
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
    public void modelPropertyChange(PropertyChangeEvent evt) {
//        Logger.getLogger(MenuBarView.class.getName()).log(Level.INFO, evt.toString());
    }
}
