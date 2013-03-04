package ares.scenario.forces;

import ares.application.models.forces.FormationModel;
import ares.data.jaxb.Emphasis;
import ares.data.jaxb.Formation.Track;
import ares.data.jaxb.SupportScope;
import ares.engine.algorithms.planning.Planner;
import ares.engine.command.Objective;
import ares.engine.command.OperationType;
import ares.engine.command.OperationalPlan;
import ares.engine.command.OperationalPlanFactory;
import ares.engine.command.OperationalStance;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.scenario.Scenario;
import ares.scenario.board.Tile;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez antiTank dsic.upv.es>
 */
public class Formation implements ModelProvider<FormationModel> {

    private int id;
    private String name;
    private Echelon echelon;
    private Force force;
    private String commander;
    private String details;
    private int proficiency;
    private int supply;
    private Emphasis emphasis;
    private SupportScope supportscope;
    /**
     * List of available (on-board) units. This collection excludes reinforcements, destroyed/withdrawed units and
     * divided units.
     */
    private List<Unit> activeUnits;
    /**
     * List of scheduled reinforcement units, stored in a queue
     */
    private Queue<Unit> scheduledReinforcements;
    /**
     * List of units that could be received as reinforcements, conditioned to certain events
     */
    private List<Unit> conditionalReinforcements;
    private Formation superior;
    private List<Formation> subordinates;
    private OperationalPlan operationalPlan;

    // TODO each turn check for reinforcements and put them into the right unit collection
    public Formation(ares.data.jaxb.Formation formation, Force force, Scenario scenario) {
        id = formation.getId();
        name = formation.getName();
        echelon = Echelon.valueOf(formation.getEchelon().name());
        this.force = force;
        commander = formation.getCommander();
        details = formation.getDetails();
        proficiency = formation.getProficiency();
        supply = formation.getSupply();

        emphasis = formation.getEmphasis();
        supportscope = formation.getSupportscope();

        activeUnits = new ArrayList<>();

        scheduledReinforcements = new PriorityQueue<>(2, Unit.UNIT_ENTRY_COMPARATOR);
        conditionalReinforcements = new ArrayList<>();
        subordinates = new ArrayList<>();
        Map<Integer, Unit> allUnits = new HashMap<>();
        for (ares.data.jaxb.Unit unit : formation.getUnit()) {
            Unit u = UnitFactory.getUnit(unit, this, force, scenario);
            allUnits.put(unit.getId(), u);
            switch (u.getAvailability()) {
                case DIVIDED: // divided units are not added to list of units
                    break;
                case TURN:
                    scheduledReinforcements.add(u);
                    break;
                case EVENT:
                    conditionalReinforcements.add(u);
                    break;
                default:
                    activeUnits.add(u);
            }
        }
        // Set parents for units resulting of division
        for (ares.data.jaxb.Unit unit : formation.getUnit()) {
            if (unit.getParent() != null) {
                allUnits.get(unit.getId()).setParent(allUnits.get(unit.getParent()));
            }
        }

        List<Objective> objectives = new ArrayList<>();
        List<Track> tracks = formation.getTrack();
        Tile[][] tile = scenario.getBoard().getMap();
        OperationType operationType = Enum.valueOf(OperationType.class, formation.getOrders().name());
        OperationalStance stance = operationType.getStance();
        boolean inversePriority = false;
        if (stance == OperationalStance.DEFENSIVE
                || stance == OperationalStance.SECURITY
                || stance == OperationalStance.RESERVE) {
            inversePriority = true;
        }
        int index = 0;
        int lastObjective = objectives.size() - 1;
        if (tracks.size() > 0) {
            for (ares.data.jaxb.Formation.Track.Objective obj : tracks.get(0).getObjective()) {
                Tile location = tile[obj.getX()][obj.getY()];
                Objective newObjective = (inversePriority
                        ? new Objective(location, lastObjective - index++)
                        : new Objective(location, index++));
                objectives.add(newObjective);
            }
        }

        operationalPlan = OperationalPlanFactory.getOperationalPlan(operationType, this, objectives);
    }

    /**
     * This method makes the formation active, which implies a planing step
     *
     */
    public void activate(Planner planner) {
        for (Unit unit : activeUnits) {
            unit.activate();
        }
        planner.plan(this);
    }

    public boolean isActive() {
        return !activeUnits.isEmpty();
    }

    public void setSuperior(Formation superior) {
        this.superior = superior;
    }

    public void setSubordinates(List<Formation> subordinates) {
        this.subordinates = subordinates;
    }

    public Formation getSuperior() {
        return superior;
    }

    public List<Formation> getSubordinates() {
        return subordinates;
    }

    public String getCommander() {
        return commander;
    }

    public List<Unit> getConditionalReinforcements() {
        return conditionalReinforcements;
    }

    public String getDetails() {
        return details;
    }

    public Echelon getEchelon() {
        return echelon;
    }

    public Emphasis getEmphasis() {
        return emphasis;
    }

    public Force getForce() {
        return force;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public OperationalPlan getOperationalPlan() {
        return operationalPlan;
    }

    public int getProficiency() {
        return proficiency;
    }

    public Queue<Unit> getScheduledReinforcements() {
        return scheduledReinforcements;
    }

    public int getSupply() {
        return supply;
    }

    public SupportScope getSupportscope() {
        return supportscope;
    }

    public List<Unit> getActiveUnits() {
        return activeUnits;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id;
        hash = 61 * hash + Objects.hashCode(this.force);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Formation other = (Formation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.force, other.force)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public FormationModel getModel(UserRole role) {
        return new FormationModel(this, role);
    }

    public void plan(Planner planner) {
        planner.plan(this);
    }
}
