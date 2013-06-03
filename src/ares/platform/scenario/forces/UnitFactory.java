package ares.platform.scenario.forces;

import ares.platform.scenario.Scenario;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class UnitFactory {

    public static Unit getUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
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
}
