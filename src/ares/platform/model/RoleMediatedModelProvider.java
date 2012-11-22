package ares.platform.model;

import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class RoleMediatedModelProvider<T> implements ModelProvider<T> {

    @Override
    public T getModel(UserRole role) {
        if (role.isGod()) {
            return getCompleteModel();
        } else {
            return getModel(role.getForce());
        }
    }

    public abstract T getModel(Force force);

    public abstract T getCompleteModel();
}
