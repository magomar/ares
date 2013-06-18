package ares.platform.scenario.assets;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum AssetTrait {

    MINOR_FERRY(7.2),
    ARMORED,
    ACTIVE_DEFENDER,
    RECON(3.6),
    STATIC(0.0),
    ENGINEER(7.2),
    HORSES(4.0),
    FIXED(0.0),
    TRANSPORT(),
    SLOW(3.3),
    MOTORIZED(6.7),
    HELICOPTER_MOVEMENT(50),
    HIGH_AA,
    LONG_RANGE,
    LIGHT_SHIP(50),
    MEDIUM_SHIP(50),
    HEAVY_SHIP(50),
    AIRCRAFT_CARRIER(50),
    LOW_ALTITUDE_AIRCRAFT(50),
    HIGH_ALTITUDE_AIRCRAFT(50),
    AMPHIBIOUS,
    NAVAL_AIRCRAFT,
    RIVERINE(28.6),
    HIGH_LOW_AA,
    ALL_WEATHER,
    ANTI_SHIP,
    FAST_HORSES(4.8),
    MAJOR_FERRY(7.2),
    RAIL_ONLY(50),
    ANTI_SHIP_ONLY,
    SLOW_MOTORIZED(4.2),
    FAST_MOTORIZED(7.9),
    COMPOSITE_ARMOR,
    LAMINATE_ARMOR,
    NBC_PROTECTION,
    NUCLEAR,
    KINETIC_ANTI_ARMOR,
    PRECISSION_GUIDED_WEAPONS,
    IN_FLIGHT_REFUELLING,
    LIGHTWEIGHT,
    AIRBORNE_ALLOWED,
    TARGETTING_1,
    TARGETTING_2,
    TARGETTING_3,
    TARGETTING_4,
    LOGISTICS,
    COMMAND,
    SMOKE,
    REACTIVE_ARMOR,
    TRAFFIC_CONTROL(40.0),
    LIGHT_TRANSPORT_HELI,
    MEDIUM_TRANSPORT_HELI,
    HEAVY_TRANSPORT_HELI,
    AGILE,
    ROADBOUND(0.3),
    EXTENDED_RANGE,
    STANDOFF_WEAPONS,
    SHOCK_CAVALRY,
    RAIL_REPAIR(7.2),
    INFANTRY,
    POOR_GEOMETRY,
    FAIR_GEOMETRY,
    DPMX_ANTI_SHIP,
    TORPEDO;
    private final double factor;
    public static final Set<AssetTrait> AIRCRAFT = EnumSet.of(LOW_ALTITUDE_AIRCRAFT, HIGH_ALTITUDE_AIRCRAFT, NAVAL_AIRCRAFT);
    public static final Set<AssetTrait> SHIP = EnumSet.of(LIGHT_SHIP, MEDIUM_SHIP, HEAVY_SHIP);
    public static final Set<AssetTrait> MOVEMENT = EnumSet.of(FIXED, STATIC, SLOW,
            HORSES, FAST_HORSES, MOTORIZED, SLOW_MOTORIZED,
            FAST_MOTORIZED, HELICOPTER_MOVEMENT, HIGH_ALTITUDE_AIRCRAFT,
            LOW_ALTITUDE_AIRCRAFT, LIGHT_SHIP, MEDIUM_SHIP, HEAVY_SHIP,
            RAIL_ONLY, RIVERINE, AIRCRAFT_CARRIER);

    private AssetTrait() {
        factor = 1;
    }

    private AssetTrait(final double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }
}
