package ares.model.forces;

import ares.model.scenario.Scenario;
import ares.engine.realtime.Clock;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class SurfaceUnit extends Unit {

    public SurfaceUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        super(unit, formation, force, scenario);
    }

}
