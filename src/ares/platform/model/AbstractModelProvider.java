package ares.platform.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractModelProvider<T> implements ModelProvider<T> {
    private T model;

    private PropertyChangeSupport propertyChangeSupport;

    public AbstractModelProvider() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    @Override
    public T getModel() {
        return model;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
}