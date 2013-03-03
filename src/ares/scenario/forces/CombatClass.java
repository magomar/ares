package ares.scenario.forces;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public enum CombatClass {

    /**
     * This class represents the staff and performs mainly a commanding role. They can have varying combat capabilities,
     * but in general they should be kept away from the combat line.
     */
    HQ,
    /**
     * This class is intended to provide first line combat capabilities, so they are able to defend themselves. These
     * units are the only ones capable of assaulting enemy-occupied positions by themselves.
     */
    LINE,
    /**
     * This class is intended to provide first line combat capabilities, so they are able to defend themselves, but
     * unlike line-units, these units are not able to perform assaults by the themselves, since they are conceived to
     * mainly provide support fire.
     */
    LINE_SUPPORT,
    /**
     * Units belonging to this class are neither able to perform assaults nor able to conduct direct attacks. They are only able to
     * provide fire support. This list excludes any non active units, that is reinforcements, destroyed/withdrawed units
     * or divided units.
     */
    SUPPORT,
    /**
     * Units belonging to this class are not fit for combat. These are intended to provide non combat services, such as loggistics,
     * communication, politics & moral support (including religion).
     *
     */
    SERVICE_SUPPORT,
    OTHER,
}

//        lineUnits = new ArrayList<>();
//        lineSupportUnits = new ArrayList<>();
//        supportUnits = new ArrayList<>();
//        serviceUnits = new ArrayList<>();
//        headquarters = new ArrayList<>();
//        otherUnits = new ArrayList<>();
//        for (Unit unit : formation.getActiveUnits()) {
//            switch (unit.getType().getCombatClass()) {
//                case LINE:
//                    lineUnits.add(unit);
//                    break;
//                case LINE_SUPPORT:
//                    lineSupportUnits.add(unit);
//                    break;
//                case SUPPORT:
//                    supportUnits.add(unit);
//                    break;
//                case HQ:
//                    headquarters.add(unit);
//                    break;
//                case SERVICE_SUPPORT:
//                    serviceUnits.add(unit);
//                    break;
//                default:
//                    Logger.getLogger(OperationalPlan.class.getName()).log(Level.SEVERE, "Formation " + formation + "has unit of OTHER type: ", unit);
//            }
//        }