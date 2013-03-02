package ares.engine.command;

import ares.engine.algorithms.planning.Planner;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public abstract class OperationalPlan {

    protected OperationType type;
    protected OperationForm form;
    protected boolean hasPlan;
    protected Formation formation;
    private List<Objective> objectives;
//    /**
//     * List of available (active, on-board) line units. These units are intended to provide first line combat
//     * capabilities, so they are able to defend themselves. These units are the only ones capable of assaulting
//     * enemy-occupied positions by themselves. This list excludes any non active units, that is reinforcements,
//     * destroyed/withdrawed units or divided units.
//     */
//    protected List<Unit> lineUnits;
//    /**
//     * List of available (active, on-board) line-support units. These units are intended to provide first line combat
//     * capabilities, so they are able to defend themselves, but unlike line-units, these units are not able to perform
//     * assaults by the themselves, since they are conceived to mainly provide support fire. This list excludes any non
//     * active units, that is reinforcements, destroyed/withdrawed units or divided units.
//     */
//    protected List<Unit> lineSupportUnits;
//    /**
//     * List of available (active, on-board) support units. This units are neither able to perform assaults nor able to
//     * conduct direct attacks. They are only able to provide fire support. This list excludes any non active units, that
//     * is reinforcements, destroyed/withdrawed units or divided units.
//     */
//    protected List<Unit> supportUnits;
//    /**
//     * List of available (active, on-board) headquarters units. These units hold the staff and perform mainly a
//     * commanding role. They can have varying combat capabilities, but in general they should be kept away from the
//     * combat line.
//     *
//     */
//    protected List<Unit> headquarters;
//    /**
//     * List of available (active, on-board) service units. These units are not fit for combat. These are intended to
//     * provide non combat services, such as loggistics, communication, politics & moral support (including religion).
//     *
//     */
//    protected List<Unit> serviceUnits;
//    /**
//     * List of available (active, on-board) service units. These units are not fit for combat. These are intended to
//     * provide non combat services, such as loggistics, communication, politics & moral support (including religion).
//     *
//     */
//    protected List<Unit> otherUnits;
    private Map<Unit, TacticalMission> missions;

    public OperationalPlan(OperationType type, Formation formation, List<Objective> objectives) {
        this.type = type;
        this.formation = formation;
        this.objectives = objectives;
        hasPlan = false;
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
    }

    public void updateObjectives() {
        for (Objective objective : objectives) {
            if (objective.checkAchieved(formation.getForce())) {
                objective.setAchieved(true);
            }
        }
    }
    public Formation getFormation() {
        return formation;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }

    public OperationType getType() {
        return type;
    }

    public OperationForm getForm() {
        return form;
    }

    public Map<Unit, TacticalMission> getMissions() {
        return missions;
    }

}