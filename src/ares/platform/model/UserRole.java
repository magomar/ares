package ares.platform.model;

import ares.scenario.forces.Force;

/**
 * This class represents the type of user interacting with the system.
 * Actually, the user role may be either GOD, which has full access to the 
 * applicatio models, or FORCE, in which case the role has to be identified by 
 * the Force which is being assigned to the user.
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
