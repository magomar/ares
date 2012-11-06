package ares.ai.planner.maneuver;

/**
 *
 * @author Saúl Esteban
 */
public enum ManeuverType {
    
    ENCIRCLEMENT(ManeuverEncirclement.class),
    FRONTAL_ATTACK(ManeuverFrontalAttack.class);
    
    private final Class maneuverClass;
    
    ManeuverType(final Class maneuverClass) {
        this.maneuverClass = maneuverClass;
    }
    
    public Class getManeuverClass() {
        return maneuverClass;
    }
}
