package ares.platform.scenario.forces;

import ares.platform.engine.movement.MovementType;
import ares.platform.scenario.Scenario;

import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitFactory {

    public static Unit createUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        UnitType type = UnitType.valueOf(unit.getType().name());
        Set<Capability> capabilities = type.getCapabilities();
        if (capabilities.contains(Capability.AIRCRAFT)) {
            return new AirUnit(unit, formation, force, scenario);
        }
        if (capabilities.contains(Capability.NAVAL)) {
            return new NavalUnit(unit, formation, force, scenario);
        }
        return new LandUnit(unit, formation, force, scenario);
    }
    
    public static Unit createTestUnit(MovementType type) {
        Unit unit;
        switch (type) {
            case AIRCRAFT:
                unit = new AirUnit();
                break;
            case NAVAL:
                unit = new NavalUnit();
                break;
            default:
                unit = new LandUnit();
        }
        unit.setMovement(type);
        return unit;
    }
    
    //    private ares.data.jaxb.Orders createTestOrders() {
//        ares.data.jaxb.Orders orders = new Orders();
//        orders.setActivates(0);
//        orders.setEmphasis(Emphasis.LIMIT_LOSSES);
//        orders.setFrontage(Frontage.WIDE);
//        orders.setOnlyPO(true);
//        orders.setOperationalStance(OperationalStance.OFFENSIVE);
//        orders.setSupportscope(SupportScope.ARMY_SUPPORT);
//        return orders;
//    }
}
