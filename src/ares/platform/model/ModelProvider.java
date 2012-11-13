package ares.platform.model;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
interface ModelProvider<T> {
    public T getModel(UserRole userRole);
}
