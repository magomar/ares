package ares.application.shared.gui.views;

import java.awt.Dimension;
import javax.swing.JComponent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractView<C extends JComponent> implements View<C> {

    protected final C contentPane;

    public AbstractView() {
        this.contentPane = layout();
    }

    protected abstract C layout();

    @Override
    public C getContentPane() {
        return contentPane;
    }

    @Override
    public void setVisible(boolean visible) {
        contentPane.setVisible(visible);
    }

    @Override
    public boolean isVisible() {
        return contentPane.isVisible();
    }

    @Override
    public boolean isFocusable() {
        return contentPane.isFocusable() && contentPane.isDisplayable() && contentPane.isVisible() && contentPane.isEnabled();
    }

//    public Component getComponent(String componentName) {
//        return getComponent(componentName, contentPane);
//    }
//
//    private Component getComponent(String componentName, Component component) {
//        Component found = null;
//        if (component.getName() != null && component.getName().equals(componentName)) {
//            found = component;
//        } else {
//            for (Component child : ((Container) component).getComponents()) {
//                found = getComponent(componentName, child);
//                if (found != null) {
//                    break;
//                }
//            }
//        }
//        return found;
//    }

    @Override
    public void setPreferredSize(Dimension size) {
        contentPane.setPreferredSize(size);
    }
}
