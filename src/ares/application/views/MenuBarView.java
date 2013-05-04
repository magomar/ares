package ares.application.views;

import ares.application.boundaries.view.ActionBarViewer;
import ares.platform.view.AbstractView;
import ares.platform.view.ComponentFactory;
import java.awt.Component;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.MenuElement;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class MenuBarView extends AbstractView<JMenuBar> implements ActionBarViewer<JMenu> {

    @Override
    protected JMenuBar layout() {
        JMenuBar jmenuBar = ComponentFactory.menuBar();
        return jmenuBar;
    }

//    protected MenuElement getMenuElement(String elementName) {
//        return getMenuElement(elementName, contentPane);
//    }
//
//    private MenuElement getMenuElement(String elementName, MenuElement menuElement) {
//        Component component = menuElement.getComponent();
//        if (elementName.equals(component.getName())) {
//            return menuElement;
//        } else {
//            for (MenuElement child : menuElement.getSubElements()) {
//                MenuElement found = getMenuElement(elementName, child);
//                if (found != null) {
//                    return found;
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public void setActionEnabled(String actionName, boolean enabled) {
//        getMenuElement(actionName).getComponent().setEnabled(enabled);
//    }

    @Override
    public void addActionButton(JMenu menu) {
        contentPane.add(menu);
    }
}
