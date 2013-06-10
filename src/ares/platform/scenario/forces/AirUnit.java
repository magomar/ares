package ares.platform.scenario.forces;

import ares.platform.engine.movement.MovementType;
import ares.platform.scenario.assets.Asset;
import ares.platform.scenario.assets.AssetType;
import ares.platform.scenario.Scenario;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public class AirUnit extends Unit {

    protected AirUnit() {
    }

    public AirUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        super(unit, formation, force, scenario);
        movement = MovementType.AIRCRAFT;
        speed = Integer.MAX_VALUE;
        for (Asset asset : assets.values()) {
            AssetType assetType = asset.getType();
            int amount = asset.getNumber();
            if (amount > 0) {
                speed = Math.min(speed, assetType.getSpeed());
            }
        }
    }
}
