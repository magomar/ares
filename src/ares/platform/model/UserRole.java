package ares.platform.model;

import ares.platform.scenario.forces.Force;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the unitType of user interacting with the system. Actually, the user role may be either GOD, which
 * has full access to the applicatio models, or FORCE, in which case the role has to be identified by the Force which is
 * being assigned to the user.
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UserRole {

    private UserRoleType roleType;
    private Force force;
    public final static UserRole GOD = new UserRole();
    private final static Map<Force, UserRole> forceRoles = new HashMap<>();

    public static UserRole getForceRole(Force force) {
        if (forceRoles.containsKey(force)) {
            return forceRoles.get(force);
        } else {
            UserRole userRole = new UserRole(force);
            forceRoles.put(force, userRole);
            return userRole;
        }
    }

    private UserRole() {
        this.roleType = UserRoleType.GOD;
    }

    private UserRole(Force force) {
        this.roleType = UserRoleType.FORCE;
        this.force = force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public void setRoleType(UserRoleType roleType) {
        this.roleType = roleType;
    }

    public Force getForce() {
        return force;
    }

    public UserRoleType getRoleType() {
        return roleType;
    }

    public boolean isGod() {
        return UserRoleType.GOD.equals(roleType);
    }

    @Override
    public String toString() {
        if (isGod()) {
            return "GOD";
        } else {
            return force.getName();
        }
    }
}
