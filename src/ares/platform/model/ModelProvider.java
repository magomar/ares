package ares.platform.model;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public interface ModelProvider<T> {
    public T getModel(UserRole userRole);
}
