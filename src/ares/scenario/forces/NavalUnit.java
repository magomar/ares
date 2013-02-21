package ares.scenario.forces;

import ares.scenario.assets.Asset;
import ares.scenario.assets.AssetType;
import ares.scenario.Scenario;
import ares.engine.movement.MovementType;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public class NavalUnit extends SurfaceUnit {

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
