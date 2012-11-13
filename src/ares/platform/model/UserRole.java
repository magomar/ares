package ares.platform.model;

import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UserRole {
    private UserRoleType roleType;
    private Force force;

    public UserRole(UserRoleType roleType, Force force) {
        this.roleType = roleType;
        this.force = force;
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public UserRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(UserRoleType roleType) {
        this.roleType = roleType;
    }
    
}
