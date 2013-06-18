package ares.platform.model;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface ModelProvider<T> {

    public T getModel(UserRole role);
}
