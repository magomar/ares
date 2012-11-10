package ares.model.forces;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum UnitType {
// TODO ¿air an naval units, which combat class are they?

    HEADQUARTERS(CombatClass.HQ, TargetClass.SOFT, Capability.HEADQUARTERS, Capability.BOMBARDMENT),
    KAMPFGRUPPE(CombatClass.LINE, TargetClass.SOFT),
    TANK(CombatClass.LINE, TargetClass.HARD),
    MECHANIZED(CombatClass.LINE, TargetClass.HARD),
    ARMORED_CAVALRY(CombatClass.LINE, TargetClass.HARD),
    ASSAULT_GUN(CombatClass.LINE, TargetClass.HARD),
    ARMORED_ARTILLERY(CombatClass.SUPPORT, TargetClass.HARD, Capability.BOMBARDMENT),
    ARMORED_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.HARD),
    ARMORED_RECON(CombatClass.LINE, TargetClass.HARD),
    INFANTRY(CombatClass.LINE, TargetClass.SOFT),
    INFANTRY_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    MACHINE_GUN(CombatClass.LINE, TargetClass.SOFT),
    CAVALRY(CombatClass.LINE, TargetClass.SOFT),
    ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    HORSE_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    INF_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    ROCKET_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    ANTI_AIRCRAFT(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    MOTOR_INFANTRY(CombatClass.LINE, TargetClass.SOFT),
    MOTOR_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    MOTOR_CAVALRY(CombatClass.LINE, TargetClass.SOFT),
    MOTORCYCLE(CombatClass.LINE, TargetClass.SOFT),
    MOTOR_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    MOTOR_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    MOTOR_ROCKET(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    MOTOR_ANTI_AIR(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    MOUNTAIN_INFANTRY(CombatClass.LINE, TargetClass.SOFT, Capability.MOUNTAIN),
    MOUNTAIN_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.MOUNTAIN),
    MOUNTAIN_CAV(CombatClass.LINE, TargetClass.SOFT, Capability.MOUNTAIN),
    MTN_CAV_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.MOUNTAIN),
    PARACHUTE(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    PARACHUTE_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRBORNE),
    PARACHUTE_INFANTRY(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    PARACHUTE_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRBORNE),
    AIRBORNE_RECON(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    AIRBORNE_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.AIRBORNE, Capability.BOMBARDMENT),
    AIR(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    FIGHTER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    FIGHTER_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    LIGHT_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    MEDIUM_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    HEAVY_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    NAVAL_FIGHTER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    NAVAL_ATTACK(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    NAVAL_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    GLIDER_TANK(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    GLIDER_INFANTRY(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    GLIDER_HVY_WPNS(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRBORNE),
    GLIDER_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.AIRBORNE, Capability.BOMBARDMENT),
    GLIDER_RECON(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    GLIDER_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRBORNE),
    ENGINEER(CombatClass.LINE, TargetClass.SOFT),
    AIRBORNE_ENGINEER(CombatClass.LINE, TargetClass.SOFT, Capability.AIRBORNE),
    ARMORED_ENGINEER(CombatClass.LINE, TargetClass.HARD),
    AMPHIBIOUS_ARMOR(CombatClass.LINE, TargetClass.HARD, Capability.AMPHIBIOUS),
    SPECIAL_FORCES(CombatClass.LINE, TargetClass.SOFT),
    MARINE_INFANTRY(CombatClass.LINE, TargetClass.SOFT),
    CHEMICAL_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    COASTAL_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT, Capability.COASTAL_DEFENSE),
    AMPHIBIOUS(CombatClass.LINE, TargetClass.SOFT, Capability.AMPHIBIOUS),
    NAVAL_TASK_FORCE(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL, Capability.BOMBARDMENT),
    JET_FIGHTER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    JET_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    JET_HEAVY_BOMBER(CombatClass.OTHER, TargetClass.SOFT, Capability.AIRCRAFT),
    HEAVY_NAVAL(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL, Capability.BOMBARDMENT),
    MEDIUM_NAVAL(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL, Capability.BOMBARDMENT),
    LIGHT_NAVAL(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL, Capability.BOMBARDMENT),
    RIVERINE(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL, Capability.BOMBARDMENT),
    CARRIER_NAVAL(CombatClass.OTHER, TargetClass.SOFT, Capability.NAVAL),
    ARMORED_TRAIN(CombatClass.LINE, TargetClass.HARD),
    FIXED_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    RAIL_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    MILITARY_POLICE(CombatClass.LINE, TargetClass.SOFT),
    TRANSPORT(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    IRREGULAR(CombatClass.LINE, TargetClass.SOFT),
    FERRY_ENGINEER(CombatClass.SUPPORT, TargetClass.SOFT),
    AMPHIB_TRANSPORT(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT, Capability.AMPHIBIOUS),
    BICYCLE(CombatClass.LINE, TargetClass.SOFT),
    SKI(CombatClass.LINE, TargetClass.SOFT),
    COMBAT_COMMAND_A(CombatClass.LINE, TargetClass.SOFT),
    COMBAT_COMMAND_B(CombatClass.LINE, TargetClass.SOFT),
    COMBAT_COMMAND_C(CombatClass.LINE, TargetClass.SOFT),
    COMBAT_COMMAND_R(CombatClass.LINE, TargetClass.SOFT),
    TASK_FORCE(CombatClass.LINE, TargetClass.SOFT),
    RAILROAD_REPAIR(CombatClass.SUPPORT, TargetClass.SOFT),
    HVY_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT),
    BORDER(CombatClass.LINE, TargetClass.SOFT),
    BATTLEGROUP(CombatClass.LINE, TargetClass.SOFT),
    SECURITY(CombatClass.LINE, TargetClass.SOFT),
    HVY_ARMOR(CombatClass.LINE, TargetClass.HARD),
    RESERVE(CombatClass.LINE, TargetClass.SOFT),
    GARRISON(CombatClass.LINE, TargetClass.SOFT),
    MOTOR_MACHINEGUN(CombatClass.LINE, TargetClass.SOFT),
    ARMORED_HVY_ARTY(CombatClass.SUPPORT, TargetClass.HARD, Capability.BOMBARDMENT),
    MOTOR_ENGINEER(CombatClass.LINE, TargetClass.SOFT),
    HVY_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    MORTAR(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    HVY_MORTAR(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    ATTACK_HELICOPTER(CombatClass.OTHER, TargetClass.SOFT, Capability.BOMBARDMENT),
    RECON_HELICOPTER(CombatClass.OTHER, TargetClass.SOFT),
    TRANS_HELICOPTER(CombatClass.OTHER, TargetClass.SOFT),
    GUERRILLA(CombatClass.LINE, TargetClass.SOFT, Capability.GUERRILLA),
    AIRMOBILE(CombatClass.LINE, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_ANTITANK(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_INFANTRY(CombatClass.LINE, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_HVY_WPN(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_CAVALRY(CombatClass.LINE, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_ARTY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.AIRMOBILE, Capability.BOMBARDMENT),
    AIRMOBILE_ENGINEER(CombatClass.LINE, TargetClass.SOFT, Capability.AIRMOBILE),
    AIRMOBILE_ANTI_AIR(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRMOBILE),
    PARACHUTE_ANTI_AIR(CombatClass.LINE_SUPPORT, TargetClass.SOFT, Capability.AIRBORNE),
    MISSILE_ARTILLERY(CombatClass.SUPPORT, TargetClass.SOFT, Capability.BOMBARDMENT),
    CIVILIAN(CombatClass.LINE, TargetClass.SOFT),
    SUPPLY(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    EMBARKED_HELI(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    EMBARKED_NAVAL(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    EMBARKED_RAIL(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    EMBARKED_AIR(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT),
    UNDEFINED(CombatClass.SERVICE_SUPPORT, TargetClass.SOFT);
    private final Set<Capability> capabilities;
    private final CombatClass combatClass;
    private final TargetClass targetClass;

    private UnitType(final CombatClass role, final TargetClass targetClass, final Capability... capabilities) {
        this.combatClass = role;
        this.targetClass = targetClass;
        this.capabilities = EnumSet.noneOf(Capability.class);
        this.capabilities.addAll(Arrays.asList(capabilities));
    }

    public Set<Capability> getCapabilities() {
        return capabilities;
    }

    public CombatClass getCombatClass() {
        return combatClass;
    }

    public TargetClass getTargetClass() {
        return targetClass;
    }
}
