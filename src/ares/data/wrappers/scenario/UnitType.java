
package ares.data.wrappers.scenario;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="UnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="HEADQUARTERS"/>
 *     &lt;enumeration value="KAMPFGRUPPE"/>
 *     &lt;enumeration value="TANK"/>
 *     &lt;enumeration value="MECHANIZED"/>
 *     &lt;enumeration value="ARMORED_CAVALRY"/>
 *     &lt;enumeration value="ASSAULT_GUN"/>
 *     &lt;enumeration value="ARMORED_ARTILLERY"/>
 *     &lt;enumeration value="ARMORED_ANTITANK"/>
 *     &lt;enumeration value="ARMORED_RECON"/>
 *     &lt;enumeration value="INFANTRY"/>
 *     &lt;enumeration value="INFANTRY_HVY_WPNS"/>
 *     &lt;enumeration value="MACHINE_GUN"/>
 *     &lt;enumeration value="CAVALRY"/>
 *     &lt;enumeration value="ANTITANK"/>
 *     &lt;enumeration value="ARTILLERY"/>
 *     &lt;enumeration value="HORSE_ARTILLERY"/>
 *     &lt;enumeration value="INF_ARTILLERY"/>
 *     &lt;enumeration value="ROCKET_ARTILLERY"/>
 *     &lt;enumeration value="ANTI_AIRCRAFT"/>
 *     &lt;enumeration value="MOTOR_INFANTRY"/>
 *     &lt;enumeration value="MOTOR_HVY_WPNS"/>
 *     &lt;enumeration value="MOTOR_CAVALRY"/>
 *     &lt;enumeration value="MOTORCYCLE"/>
 *     &lt;enumeration value="MOTOR_ANTITANK"/>
 *     &lt;enumeration value="MOTOR_ARTILLERY"/>
 *     &lt;enumeration value="MOTOR_ROCKET"/>
 *     &lt;enumeration value="MOTOR_ANTI_AIR"/>
 *     &lt;enumeration value="MOUNTAIN_INFANTRY"/>
 *     &lt;enumeration value="MOUNTAIN_HVY_WPNS"/>
 *     &lt;enumeration value="MOUNTAIN_CAV"/>
 *     &lt;enumeration value="MTN_CAV_HVY_WPNS"/>
 *     &lt;enumeration value="PARACHUTE"/>
 *     &lt;enumeration value="PARACHUTE_ANTITANK"/>
 *     &lt;enumeration value="PARACHUTE_INFANTRY"/>
 *     &lt;enumeration value="PARACHUTE_HVY_WPNS"/>
 *     &lt;enumeration value="AIRBORNE_RECON"/>
 *     &lt;enumeration value="AIRBORNE_ARTILLERY"/>
 *     &lt;enumeration value="AIR"/>
 *     &lt;enumeration value="FIGHTER"/>
 *     &lt;enumeration value="FIGHTER_BOMBER"/>
 *     &lt;enumeration value="LIGHT_BOMBER"/>
 *     &lt;enumeration value="MEDIUM_BOMBER"/>
 *     &lt;enumeration value="HEAVY_BOMBER"/>
 *     &lt;enumeration value="NAVAL_FIGHTER"/>
 *     &lt;enumeration value="NAVAL_ATTACK"/>
 *     &lt;enumeration value="NAVAL_BOMBER"/>
 *     &lt;enumeration value="JET_FIGHTER"/>
 *     &lt;enumeration value="JET_BOMBER"/>
 *     &lt;enumeration value="JET_HEAVY_BOMBER"/>
 *     &lt;enumeration value="GLIDER_TANK"/>
 *     &lt;enumeration value="GLIDER_INFANTRY"/>
 *     &lt;enumeration value="GLIDER_HVY_WPNS"/>
 *     &lt;enumeration value="GLIDER_ARTILLERY"/>
 *     &lt;enumeration value="GLIDER_RECON"/>
 *     &lt;enumeration value="GLIDER_ANTITANK"/>
 *     &lt;enumeration value="ENGINEER"/>
 *     &lt;enumeration value="AIRBORNE_ENGINEER"/>
 *     &lt;enumeration value="ARMORED_ENGINEER"/>
 *     &lt;enumeration value="AMPHIBIOUS_ARMOR"/>
 *     &lt;enumeration value="SPECIAL_FORCES"/>
 *     &lt;enumeration value="MARINE_INFANTRY"/>
 *     &lt;enumeration value="CHEMICAL_ARTILLERY"/>
 *     &lt;enumeration value="COASTAL_ARTILLERY"/>
 *     &lt;enumeration value="AMPHIBIOUS"/>
 *     &lt;enumeration value="NAVAL_TASK_FORCE"/>
 *     &lt;enumeration value="HEAVY_NAVAL"/>
 *     &lt;enumeration value="MEDIUM_NAVAL"/>
 *     &lt;enumeration value="LIGHT_NAVAL"/>
 *     &lt;enumeration value="RIVERINE"/>
 *     &lt;enumeration value="CARRIER_NAVAL"/>
 *     &lt;enumeration value="ARMORED_TRAIN"/>
 *     &lt;enumeration value="FIXED_ARTILLERY"/>
 *     &lt;enumeration value="RAIL_ARTILLERY"/>
 *     &lt;enumeration value="MILITARY_POLICE"/>
 *     &lt;enumeration value="TRANSPORT"/>
 *     &lt;enumeration value="IRREGULAR"/>
 *     &lt;enumeration value="FERRY_ENGINEER"/>
 *     &lt;enumeration value="AMPHIB_TRANSPORT"/>
 *     &lt;enumeration value="BICYCLE"/>
 *     &lt;enumeration value="SKI"/>
 *     &lt;enumeration value="COMBAT_COMMAND_A"/>
 *     &lt;enumeration value="COMBAT_COMMAND_B"/>
 *     &lt;enumeration value="COMBAT_COMMAND_C"/>
 *     &lt;enumeration value="COMBAT_COMMAND_R"/>
 *     &lt;enumeration value="TASK_FORCE"/>
 *     &lt;enumeration value="RAILROAD_REPAIR"/>
 *     &lt;enumeration value="HVY_ANTITANK"/>
 *     &lt;enumeration value="BORDER"/>
 *     &lt;enumeration value="BATTLEGROUP"/>
 *     &lt;enumeration value="SECURITY"/>
 *     &lt;enumeration value="HVY_ARMOR"/>
 *     &lt;enumeration value="RESERVE"/>
 *     &lt;enumeration value="TRANSPORT"/>
 *     &lt;enumeration value="GARRISON"/>
 *     &lt;enumeration value="MOTOR_MACHINEGUN"/>
 *     &lt;enumeration value="ARMORED_HVY_ARTY"/>
 *     &lt;enumeration value="MOTOR_ENGINEER"/>
 *     &lt;enumeration value="HVY_ARTILLERY"/>
 *     &lt;enumeration value="MORTAR"/>
 *     &lt;enumeration value="HVY_MORTAR"/>
 *     &lt;enumeration value="ATTACK_HELICOPTER"/>
 *     &lt;enumeration value="RECON_HELICOPTER"/>
 *     &lt;enumeration value="TRANS_HELICOPTER"/>
 *     &lt;enumeration value="GUERRILLA"/>
 *     &lt;enumeration value="AIRMOBILE"/>
 *     &lt;enumeration value="AIRMOBILE_ANTITANK"/>
 *     &lt;enumeration value="AIRMOBILE_INFANTRY"/>
 *     &lt;enumeration value="AIRMOBILE_HVY_WPN"/>
 *     &lt;enumeration value="AIRMOBILE_CAVALRY"/>
 *     &lt;enumeration value="AIRMOBILE_ARTY"/>
 *     &lt;enumeration value="AIRMOBILE_ENGINEER"/>
 *     &lt;enumeration value="AIRMOBILE_ANTI_AIR"/>
 *     &lt;enumeration value="PARACHUTE_ANTI_AIR"/>
 *     &lt;enumeration value="MISSILE_ARTILLERY"/>
 *     &lt;enumeration value="CIVILIAN"/>
 *     &lt;enumeration value="SUPPLY"/>
 *     &lt;enumeration value="EMBARKED_HELI"/>
 *     &lt;enumeration value="EMBARKED_NAVAL"/>
 *     &lt;enumeration value="EMBARKED_RAIL"/>
 *     &lt;enumeration value="EMBARKED_AIR"/>
 *     &lt;enumeration value="UNDEFINED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "UnitType", namespace = "ares")
@XmlEnum
public enum UnitType {

    HEADQUARTERS,
    KAMPFGRUPPE,
    TANK,
    MECHANIZED,
    ARMORED_CAVALRY,
    ASSAULT_GUN,
    ARMORED_ARTILLERY,
    ARMORED_ANTITANK,
    ARMORED_RECON,
    INFANTRY,
    INFANTRY_HVY_WPNS,
    MACHINE_GUN,
    CAVALRY,
    ANTITANK,
    ARTILLERY,
    HORSE_ARTILLERY,
    INF_ARTILLERY,
    ROCKET_ARTILLERY,
    ANTI_AIRCRAFT,
    MOTOR_INFANTRY,
    MOTOR_HVY_WPNS,
    MOTOR_CAVALRY,
    MOTORCYCLE,
    MOTOR_ANTITANK,
    MOTOR_ARTILLERY,
    MOTOR_ROCKET,
    MOTOR_ANTI_AIR,
    MOUNTAIN_INFANTRY,
    MOUNTAIN_HVY_WPNS,
    MOUNTAIN_CAV,
    MTN_CAV_HVY_WPNS,
    PARACHUTE,
    PARACHUTE_ANTITANK,
    PARACHUTE_INFANTRY,
    PARACHUTE_HVY_WPNS,
    AIRBORNE_RECON,
    AIRBORNE_ARTILLERY,
    AIR,
    FIGHTER,
    FIGHTER_BOMBER,
    LIGHT_BOMBER,
    MEDIUM_BOMBER,
    HEAVY_BOMBER,
    NAVAL_FIGHTER,
    NAVAL_ATTACK,
    NAVAL_BOMBER,
    JET_FIGHTER,
    JET_BOMBER,
    JET_HEAVY_BOMBER,
    GLIDER_TANK,
    GLIDER_INFANTRY,
    GLIDER_HVY_WPNS,
    GLIDER_ARTILLERY,
    GLIDER_RECON,
    GLIDER_ANTITANK,
    ENGINEER,
    AIRBORNE_ENGINEER,
    ARMORED_ENGINEER,
    AMPHIBIOUS_ARMOR,
    SPECIAL_FORCES,
    MARINE_INFANTRY,
    CHEMICAL_ARTILLERY,
    COASTAL_ARTILLERY,
    AMPHIBIOUS,
    NAVAL_TASK_FORCE,
    HEAVY_NAVAL,
    MEDIUM_NAVAL,
    LIGHT_NAVAL,
    RIVERINE,
    CARRIER_NAVAL,
    ARMORED_TRAIN,
    FIXED_ARTILLERY,
    RAIL_ARTILLERY,
    MILITARY_POLICE,
    TRANSPORT,
    IRREGULAR,
    FERRY_ENGINEER,
    AMPHIB_TRANSPORT,
    BICYCLE,
    SKI,
    COMBAT_COMMAND_A,
    COMBAT_COMMAND_B,
    COMBAT_COMMAND_C,
    COMBAT_COMMAND_R,
    TASK_FORCE,
    RAILROAD_REPAIR,
    HVY_ANTITANK,
    BORDER,
    BATTLEGROUP,
    SECURITY,
    HVY_ARMOR,
    RESERVE,
    GARRISON,
    MOTOR_MACHINEGUN,
    ARMORED_HVY_ARTY,
    MOTOR_ENGINEER,
    HVY_ARTILLERY,
    MORTAR,
    HVY_MORTAR,
    ATTACK_HELICOPTER,
    RECON_HELICOPTER,
    TRANS_HELICOPTER,
    GUERRILLA,
    AIRMOBILE,
    AIRMOBILE_ANTITANK,
    AIRMOBILE_INFANTRY,
    AIRMOBILE_HVY_WPN,
    AIRMOBILE_CAVALRY,
    AIRMOBILE_ARTY,
    AIRMOBILE_ENGINEER,
    AIRMOBILE_ANTI_AIR,
    PARACHUTE_ANTI_AIR,
    MISSILE_ARTILLERY,
    CIVILIAN,
    SUPPLY,
    EMBARKED_HELI,
    EMBARKED_NAVAL,
    EMBARKED_RAIL,
    EMBARKED_AIR,
    UNDEFINED;

    public String value() {
        return name();
    }

    public static UnitType fromValue(String v) {
        return valueOf(v);
    }

}
