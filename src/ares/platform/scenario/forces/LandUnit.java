package ares.platform.scenario.forces;

import ares.platform.engine.movement.MovementType;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.assets.Asset;
import ares.platform.scenario.assets.AssetTrait;
import ares.platform.scenario.assets.AssetType;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public final class LandUnit extends SurfaceUnit {

    protected int numVehicles;
    protected int numHorses;
    protected int transportNeeds;
    protected int transportCapacity;
    protected boolean roadbound;
    protected int numActiveDefenders;
    protected int engineering;
    protected int majorFerry;
    protected int minorFerry;
    protected int trafficControl;
    protected int logistics;
    protected int command;
    protected int railRepair;

    public LandUnit(ares.data.jaxb.Unit unit, Formation formation, Force force, Scenario scenario) {
        super(unit, formation, force, scenario);
        int transportSpeed = Integer.MAX_VALUE;
        int nonTransportSpeed = Integer.MAX_VALUE;
        int numStatic = (traits.containsKey(AssetTrait.STATIC) ? traits.get(AssetTrait.STATIC) : 0);
        int numSlow = (traits.containsKey(AssetTrait.SLOW) ? traits.get(AssetTrait.SLOW) : 0);
        if (traits.containsKey(AssetTrait.ACTIVE_DEFENDER)) {
            numActiveDefenders = traits.get(AssetTrait.ACTIVE_DEFENDER);
        }
        if (traits.containsKey(AssetTrait.RECON)) {
            reconnaissance = (int) (traits.get(AssetTrait.RECON) * AssetTrait.RECON.getFactor());
        }
        if (traits.containsKey(AssetTrait.ENGINEER)) {
            engineering = (int) (traits.get(AssetTrait.ENGINEER) * AssetTrait.ENGINEER.getFactor());
        }
        if (traits.containsKey(AssetTrait.MAJOR_FERRY)) {
            majorFerry = (int) (traits.get(AssetTrait.MAJOR_FERRY) * AssetTrait.MAJOR_FERRY.getFactor());
        }
        if (traits.containsKey(AssetTrait.MINOR_FERRY)) {
            minorFerry = (int) (traits.get(AssetTrait.MINOR_FERRY) * AssetTrait.MINOR_FERRY.getFactor());
        }
        if (traits.containsKey(AssetTrait.ENGINEER)) {
            minorFerry += (int) (traits.get(AssetTrait.ENGINEER) * AssetTrait.MINOR_FERRY.getFactor() / 2);
        }
        if (traits.containsKey(AssetTrait.TRAFFIC_CONTROL)) {
            trafficControl = (int) (traits.get(AssetTrait.TRAFFIC_CONTROL) * AssetTrait.TRAFFIC_CONTROL.getFactor());
        }
        if (traits.containsKey(AssetTrait.LOGISTICS)) {
            logistics = (int) (traits.get(AssetTrait.LOGISTICS) * AssetTrait.LOGISTICS.getFactor());
        }
        if (traits.containsKey(AssetTrait.COMMAND)) {
            command = (int) (traits.get(AssetTrait.COMMAND) * AssetTrait.COMMAND.getFactor());
        }
        if (traits.containsKey(AssetTrait.RAIL_REPAIR)) {
            railRepair = (int) (traits.get(AssetTrait.RAIL_REPAIR) * AssetTrait.RAIL_REPAIR.getFactor());
        }
        roadbound = traits.containsKey(AssetTrait.ROADBOUND);

        for (Asset asset : assets.values()) {
            AssetType assetType = asset.getType();
            int amount = asset.getNumber();
            if (amount > 0) {
                Set<AssetTrait> assetTraits = assetType.getTraits();
                for (AssetTrait t : assetTraits) {
                    switch (t) {
                        case SLOW:
                            nonTransportSpeed = Math.min(nonTransportSpeed, assetType.getSpeed());
                            break;
                        case TRANSPORT:
                            transportCapacity += amount;
                            transportSpeed = Math.min(transportSpeed, assetType.getSpeed());
                            break;
                        case MOTORIZED:
                        case SLOW_MOTORIZED:
                        case FAST_MOTORIZED:
                            numVehicles += amount;
                            if (!assetTraits.contains(AssetTrait.TRANSPORT)) {
                                nonTransportSpeed = Math.min(nonTransportSpeed, assetType.getSpeed());
                            }
                            break;
                        case HORSES:
                        case FAST_HORSES:
                            numHorses += amount;
                            if (!assetTraits.contains(AssetTrait.TRANSPORT)) {
                                nonTransportSpeed = Math.min(nonTransportSpeed, assetType.getSpeed());
                            }
                            break;
                        case FIXED:
                            movement = MovementType.FIXED;
                            nonTransportSpeed = 0;
                            break;
                        case RAIL_ONLY:
                            numVehicles += amount;
                            movement = MovementType.RAIL;
                            nonTransportSpeed = Math.min(nonTransportSpeed, assetType.getSpeed());
                            break;
                        case RIVERINE:
                            numVehicles += amount;
                            movement = MovementType.RIVERINE;
                            if (!assetTraits.contains(AssetTrait.TRANSPORT)) {
                                nonTransportSpeed = Math.min(nonTransportSpeed, assetType.getSpeed());
                            }
                            break;
                        // TODO airmobile movement
                    }
                }
            }
        }
        transportNeeds = numSlow + numStatic;
        if (type.getCapabilities().contains(Capability.COASTAL_DEFENSE)) {
            movement = MovementType.FIXED;
            speed = 0;
        } else if (movement == null) {
            if (type.getCapabilities().contains(Capability.AMPHIBIOUS)) {
                movement = MovementType.AMPHIBIOUS;
                speed = Math.min(transportSpeed, nonTransportSpeed);
            } else if (transportNeeds == 0) {
                speed = Math.min(transportSpeed, nonTransportSpeed);
                if (numVehicles > 2 * numHorses) {
                    movement = MovementType.MOTORIZED;
                } else if (numVehicles < numHorses) {
                    movement = MovementType.FOOT;
                } else {
                    movement = MovementType.MIXED;
                }
            } else if (transportCapacity > 0) {
                double tr = 2.0 * transportCapacity / transportNeeds;
                if (tr >= 1 && numVehicles > numHorses) {
                    movement = MovementType.MOTORIZED;
                    speed = transportSpeed;
                } else if (tr >= 0.5 && numVehicles > numHorses) {
                    movement = MovementType.MIXED;
                    if (2 * transportCapacity >= numStatic) {
                        speed = (int) Math.max(nonTransportSpeed, tr * transportSpeed);
                    } else {
                        speed = (int) (tr * transportSpeed);
                    }
                } else {
                    movement = MovementType.FOOT;
                    if (2 * transportCapacity >= numStatic) {
                        speed = (int) Math.max(nonTransportSpeed, tr * transportSpeed);
                    } else {
                        speed = (int) (tr * transportSpeed);
                    }
                }
            } else {
                movement = MovementType.FOOT;
                if (nonTransportSpeed == Double.MAX_VALUE) {
                    speed = 0;
                } else {
                    speed = nonTransportSpeed;
                }
            }
        } else {
            speed = Math.min(nonTransportSpeed, transportSpeed);
        }
        speed = (int) (speed * echelon.getSpeedModifier());
    }

    public int getNumVehiclesAndHorses() {
        return numVehicles + numHorses;
    }

    public int getCommand() {
        return command;
    }

    public int getEngineering() {
        return engineering;
    }

    public int getLogistics() {
        return logistics;
    }

    public int getMajorFerry() {
        return majorFerry;
    }

    public int getMinorFerry() {
        return minorFerry;
    }

    public int getNumActiveDefenders() {
        return numActiveDefenders;
    }

    public int getNumHorses() {
        return numHorses;
    }

    public int getNumVehicles() {
        return numVehicles;
    }

    public int getRailRepair() {
        return railRepair;
    }

    public boolean isRoadbound() {
        return roadbound;
    }

    public int getTrafficControl() {
        return trafficControl;
    }

    public int getTransportCapacity() {
        return transportCapacity;
    }

    public int getTransportNeeds() {
        return transportNeeds;
    }
}
