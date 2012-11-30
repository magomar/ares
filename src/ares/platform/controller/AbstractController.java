package ares.platform.controller;

import ares.platform.application.LookupService;
import ares.platform.model.AbstractBean;
import ares.platform.view.AbstractView;
import ares.platform.view.InternalFrameView;
import ares.platform.view.View;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JComponent;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractController implements PropertyChangeListener {
    //TODO Consider using ChangeListener as an alternative to PropertyChangeListener

    protected final LookupService<AbstractBean> models = new LookupService<>();

    /**
     * This is a convenience method that subclasses can call upon to fire property changes back to the models. This
     * method uses reflection to inspect each of the model classes to determine whether it is the owner of the property
     * in question. If it isn't, a NoSuchMethodException is thrown, which the method ignores.
     *
     * @param propertyName = The name of the property.
     * @param newValue = An object that represents the new value of the property.
     */
    protected final void setEntityProperty(String propertyName, Object newValue) {

        for (AbstractBean model : models.values()) {
            try {
                Method method = model.getClass().
                        getMethod("set" + propertyName, new Class[]{
                            newValue.getClass()
                        });
                method.invoke(model, newValue);

            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                //  Handle exception.
            }
        }
    }

    public final void addModel(Class<? extends AbstractBean> modelClass, AbstractBean model) {
        models.put(modelClass, model);
        model.addPropertyChangeListener(this);
    }

    public final void removeModel(Class<? extends AbstractBean> modelClass, AbstractBean model) {
        models.remove(modelClass);
        model.removePropertyChangeListener(this);
    }

    //    public final void addView(Class<? extends AbstractView<? extends JComponent>> viewClass, AbstractView<? extends JComponent> view) {
//        views.put(viewClass, view);
//    }
//
//    public final void removeView(Class<AbstractView<? extends JComponent>> viewClass) {
//        views.remove(viewClass);
//    }
    
//    public final <T extends AbstractView<? extends JComponent>> T getView(Class<T> viewClass) {
//        return views.get(viewClass);
//    }
//
//    @SuppressWarnings("unchecked")
//    public final <T extends AbstractView<? extends JComponent>> InternalFrameView<T> getInternalFrameView(Class<T> viewClass) {
//        return (InternalFrameView<T>) views.get(viewClass);
//    }
//
//    public final <T extends AbstractBean> T getModel(Class<T> modelClass) {
//        return (T) models.get(modelClass);
//    }
}
