package ares.platform.model;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public abstract class RoleMediatedModel<T> {

    protected final UserRole userRole;

    public RoleMediatedModel(UserRole userRole) {
        this.userRole = userRole;
    }

//    public UserRole getUserRole() {
//        return userRole;
//    }
}
