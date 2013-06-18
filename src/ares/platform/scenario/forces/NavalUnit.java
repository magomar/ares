package ares.platform.scenario.forces;

import ares.platform.engine.movement.MovementType;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.assets.Asset;
import ares.platform.scenario.assets.AssetType;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public class NavalUnit extends SurfaceUnit {

    protected NavalUnit() {
    }

    public NavalUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        super(unit, formation, force, scenario);
        movement = MovementType.NAVAL;
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
