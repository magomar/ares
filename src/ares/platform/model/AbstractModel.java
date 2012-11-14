package ares.platform.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractModel<M> {
    private M model;
    private UserRole role;

    private PropertyChangeSupport propertyChangeSupport;

    public AbstractModel(UserRole userRole) {
        this.role = userRole;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public M getModel() {
        return model;
    }

    protected UserRole getRole() {
        return role;
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