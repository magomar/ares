package ares.platform.scenario.forces;

import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class SurfaceUnit extends Unit {
    
    protected SurfaceUnit() {
        super();
    }

    public SurfaceUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        super(unit, formation, force, scenario);
    }
}
