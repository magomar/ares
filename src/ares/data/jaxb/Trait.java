
package ares.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Trait.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Trait">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="EQUIPMENT_CATEGORY"/>
 *     &lt;enumeration value="ARMORED"/>
 *     &lt;enumeration value="ACTIVE_DEFENDER"/>
 *     &lt;enumeration value="RECON"/>
 *     &lt;enumeration value="STATIC"/>
 *     &lt;enumeration value="ENGINEER"/>
 *     &lt;enumeration value="HORSES"/>
 *     &lt;enumeration value="FIXED"/>
 *     &lt;enumeration value="TRANSPORT"/>
 *     &lt;enumeration value="SLOW"/>
 *     &lt;enumeration value="MOTORIZED"/>
 *     &lt;enumeration value="HELICOPTER_MOVEMENT"/>
 *     &lt;enumeration value="HIGH_AA"/>
 *     &lt;enumeration value="LONG_RANGE"/>
 *     &lt;enumeration value="LIGHT_SHIP"/>
 *     &lt;enumeration value="MEDIUM_SHIP"/>
 *     &lt;enumeration value="HEAVY_SHIP"/>
 *     &lt;enumeration value="AIRCRAFT_CARRIER"/>
 *     &lt;enumeration value="LOW_ALTITUDE_AIRCRAFT"/>
 *     &lt;enumeration value="HIGH_ALTITUDE_AIRCRAFT"/>
 *     &lt;enumeration value="AMPHIBIOUS"/>
 *     &lt;enumeration value="NAVAL_AIRCRAFT"/>
 *     &lt;enumeration value="RIVERINE"/>
 *     &lt;enumeration value="HIGH_LOW_AA"/>
 *     &lt;enumeration value="ALL_WEATHER"/>
 *     &lt;enumeration value="ANTI_SHIP"/>
 *     &lt;enumeration value="FAST_HORSES"/>
 *     &lt;enumeration value="MAJOR_FERRY"/>
 *     &lt;enumeration value="RAIL_ONLY"/>
 *     &lt;enumeration value="ANTI_SHIP_ONLY"/>
 *     &lt;enumeration value="SLOW_MOTORIZED"/>
 *     &lt;enumeration value="FAST_MOTORIZED"/>
 *     &lt;enumeration value="COMPOSITE_ARMOR"/>
 *     &lt;enumeration value="LAMINATE_ARMOR"/>
 *     &lt;enumeration value="NBC_PROTECTION"/>
 *     &lt;enumeration value="NUCLEAR"/>
 *     &lt;enumeration value="KINETIC_ANTI_ARMOR"/>
 *     &lt;enumeration value="PRECISSION_GUIDED_WEAPONS"/>
 *     &lt;enumeration value="IN_FLIGHT_REFUELLING"/>
 *     &lt;enumeration value="LIGHTWEIGHT"/>
 *     &lt;enumeration value="AIRBORNE_ALLOWED"/>
 *     &lt;enumeration value="TARGETTING_1"/>
 *     &lt;enumeration value="TARGETTING_2"/>
 *     &lt;enumeration value="TARGETTING_3"/>
 *     &lt;enumeration value="TARGETTING_4"/>
 *     &lt;enumeration value="LOGISTICS"/>
 *     &lt;enumeration value="COMMAND"/>
 *     &lt;enumeration value="SMOKE"/>
 *     &lt;enumeration value="REACTIVE_ARMOR"/>
 *     &lt;enumeration value="TRAFFIC_CONTROL"/>
 *     &lt;enumeration value="LIGHT_TRANSPORT_HELI"/>
 *     &lt;enumeration value="MEDIUM_TRANSPORT_HELI"/>
 *     &lt;enumeration value="HEAVY_TRANSPORT_HELI"/>
 *     &lt;enumeration value="AGILE"/>
 *     &lt;enumeration value="ROADBOUND"/>
 *     &lt;enumeration value="EXTENDED_RANGE"/>
 *     &lt;enumeration value="STANDOFF_WEAPONS"/>
 *     &lt;enumeration value="SHOCK_CAVALRY"/>
 *     &lt;enumeration value="RAIL_REPAIR"/>
 *     &lt;enumeration value="INFANTRY"/>
 *     &lt;enumeration value="POOR_GEOMETRY"/>
 *     &lt;enumeration value="FAIR_GEOMETRY"/>
 *     &lt;enumeration value="DPMX_ANTI_SHIP"/>
 *     &lt;enumeration value="TORPEDO"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Trait", namespace = "ares")
@XmlEnum
public enum Trait {

    EQUIPMENT_CATEGORY,
    ARMORED,
    ACTIVE_DEFENDER,
    RECON,
    STATIC,
    ENGINEER,
    HORSES,
    FIXED,
    TRANSPORT,
    SLOW,
    MOTORIZED,
    HELICOPTER_MOVEMENT,
    HIGH_AA,
    LONG_RANGE,
    LIGHT_SHIP,
    MEDIUM_SHIP,
    HEAVY_SHIP,
    AIRCRAFT_CARRIER,
    LOW_ALTITUDE_AIRCRAFT,
    HIGH_ALTITUDE_AIRCRAFT,
    AMPHIBIOUS,
    NAVAL_AIRCRAFT,
    RIVERINE,
    HIGH_LOW_AA,
    ALL_WEATHER,
    ANTI_SHIP,
    FAST_HORSES,
    MAJOR_FERRY,
    RAIL_ONLY,
    ANTI_SHIP_ONLY,
    SLOW_MOTORIZED,
    FAST_MOTORIZED,
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
    TRAFFIC_CONTROL,
    LIGHT_TRANSPORT_HELI,
    MEDIUM_TRANSPORT_HELI,
    HEAVY_TRANSPORT_HELI,
    AGILE,
    ROADBOUND,
    EXTENDED_RANGE,
    STANDOFF_WEAPONS,
    SHOCK_CAVALRY,
    RAIL_REPAIR,
    INFANTRY,
    POOR_GEOMETRY,
    FAIR_GEOMETRY,
    DPMX_ANTI_SHIP,
    TORPEDO;

    public String value() {
        return name();
    }

    public static Trait fromValue(String v) {
        return valueOf(v);
    }

}
