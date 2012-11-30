package ares.platform.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractView<C extends JComponent> implements View {

    protected final C contentPane;
    protected Map<String, EventListenerList> actionListeners;

    public AbstractView() {
        actionListeners = new HashMap<>();
        this.contentPane = layout();
    }

    protected abstract C layout();

    public C getContentPane() {
        return contentPane;
    }

    protected void repaint() {
        contentPane.repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        contentPane.setVisible(visible);
    }

    public boolean isFocusable() {
        return contentPane.isFocusable() && contentPane.isDisplayable() && contentPane.isVisible() && contentPane.isEnabled();
    }

//    public abstract void modelPropertyChange(PropertyChangeEvent evt);
    public Component getComponent(String componentName) {
        return getComponent(componentName, contentPane);
    }

    private Component getComponent(String componentName, Component component) {
        Component found = null;
        if (component.getName() != null && component.getName().equals(componentName)) {
            found = component;
        } else {
            for (Component child : ((Container) component).getComponents()) {
                found = getComponent(componentName, child);
                if (found != null) {
                    break;
                }
            }
        }
        return found;
    }
    //    /**
//     * Generic method to add a component listener to a component
//     *
//     * @param componentName
//     * @param listener
//     * @throws ComponentNotFoundException
//     */
//    public void addComponentListener(String componentName, ComponentListener listener) throws ComponentNotFoundException {
//        Container container = getContentPane();
//        Component[] components = container.getComponents();
//        for (Component component : components) {
//            if (componentName.equals(component.getName())) {
//                component.addComponentListener(listener);
//                return;
//            }
//        }
//        throw new ComponentNotFoundException(componentName);
//    }
//
//    /**
//     * Generic method to add an action listener to a component (AbstractButton) Only components subclassing the
//     * {@link  AbstractButton} class can hold an action listener. Swing provides three direct subclasses:
//     * {@link JMenuItem},  {@link JButton} and {@link JToggleButton}
//     * {@link JMenu} is also a subclass (it extends JMenuItem)
//     *
//     * @param componentName
//     * @param listener
//     * @throws ComponentNotFoundException
//     */
//    public void addActionListener(String componentName, ActionListener listener) throws ComponentNotFoundException {
//        Container container = getContentPane();
//        Component[] components = container.getComponents();
//        for (Component component : components) {
//            if (component instanceof AbstractButton && componentName.equals(component.getName())) {
//                ((AbstractButton) component).addActionListener(listener);
//                return;
//            }
//        }
//        throw new ComponentNotFoundException(componentName);
//    }
}
