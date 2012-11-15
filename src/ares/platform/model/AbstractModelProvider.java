package ares.platform.model;

import ares.scenario.board.InformationLevel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EnumMap;
import java.util.Map;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class AbstractModelProvider<T> implements ModelProvider<T> {

    protected Map<InformationLevel, T> models;
    private PropertyChangeSupport propertyChangeSupport;

    public AbstractModelProvider() {
        models = new EnumMap<>(InformationLevel.class);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public T getCompleteModel() {
        return getModel(InformationLevel.COMPLETE);
    }

    protected T getModel(InformationLevel infoLevel) {
        return models.get(infoLevel);
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