package ares.platform.model;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class FilteredModelProvider<T> extends AbstractModelProvider<T> {

    private final UserRole userRole;

    public FilteredModelProvider(UserRole userRole) {
        this.userRole = userRole;
    }

    protected UserRole getUserRole() {
        return userRole;
    }
}
