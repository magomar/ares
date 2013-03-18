package ares.engine;

import ares.engine.time.Phase;
import ares.engine.time.ClockEventType;
import ares.engine.time.ClockEvent;
import static ares.engine.RealTimeEngine.CLOCK_EVENT_PROPERTY;
import static ares.engine.RealTimeEngine.SCENARIO_PROPERTY;
import ares.engine.action.ActionSpace;
import ares.engine.algorithms.planning.BasicPlanner;
import ares.engine.algorithms.planning.Planner;
import ares.engine.algorithms.routing.AStar;
import ares.engine.algorithms.routing.PathFinder;
import ares.platform.model.AbstractBean;
import ares.scenario.Clock;
import ares.scenario.Scenario;
import ares.scenario.board.Tile;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngine extends AbstractBean {

    public static final String SCENARIO_PROPERTY = "Scenario";
    public static final String CLOCK_EVENT_PROPERTY = "ClockEvent";
    private static final Logger LOG = Logger.getLogger(RealTimeEngine.class.getName());
    private Scenario scenario;
    private Phase phase;
    /**
     * List of active units *
     */
    private List<Unit> units;
    /**
     * List of active formations
     */
    private List<Formation> formations;
    /**
     * The last clock event received
     */
    private ClockEvent clockEvent;
    /**
     * Whether the scenario is evolving or not.
     */
    private boolean running;
    /**
     * Pathfinder algorithm
     */
    private PathFinder pathFinder;
    /**
     * Planning algorithm
     */
    private Planner planner;
    /**
     * Executor service to perform tasks concurrently (eg. planning)
     */
    private ExecutorService executor;
    /**
     * Action space is used to co-locate actions to find a solve interactions, eg. when several units participate in a
     * single combat.
     */
    private ActionSpace actionSpace;

    public RealTimeEngine() {
        units = new ArrayList<>();
        formations = new ArrayList<>();
        phase = Phase.SCHEDULE;
        running = false;
//        executor = Executors.newCachedThreadPool();
        planner = new BasicPlanner(this);
        actionSpace = new ActionSpace();
        Clock.INSTANCE.setEngine(this);
    }

    public void setScenario(Scenario scenario) {
        Scenario oldValue = this.scenario;
        this.scenario = scenario;
        if (scenario != null) {
            pathFinder = new AStar(scenario.getBoard().getWidth() * scenario.getBoard().getHeight());
            for (Force force : scenario.getForces()) {
                for (Formation formation : force.getFormations()) {
                    formations.add(formation);
                    units.addAll(formation.getActiveUnits());
                    formation.activate(planner);
                }
            }
        }
        firePropertyChange(SCENARIO_PROPERTY, oldValue, scenario);
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void start() {
        LOG.log(Level.INFO, "*** Clock Started {0}", Clock.INSTANCE);
        running = true;
        Clock.INSTANCE.tick();
    }

    public void stop() {
        LOG.log(Level.INFO, "********** Clock Stopped {0}", Clock.INSTANCE);
        running = false;
    }

    public void update(ClockEvent clockEvent) {
//        LOG.log(Level.INFO, "+++++ New Time:  {0}", clock);
        ClockEvent oldValue = this.clockEvent;
        this.clockEvent = clockEvent;
        Set<ClockEventType> clockEventTypes = clockEvent.getEventTypes();

        do {
            phase.run(this);
            phase = phase.getNext();
        } while (phase != Phase.ACT);

        if (clockEventTypes.contains(ClockEventType.DAY)) {
            LOG.log(Level.INFO, "++++++++++ New Day: {0}", Clock.INSTANCE.getTurn());
            for (Unit unit : units) {
                unit.updateMaxValues();
            }
        }
        if (clockEventTypes.contains(ClockEventType.TURN)) {
            LOG.log(Level.INFO, "++++++++++ New Turn: {0}", Clock.INSTANCE.getTurn());
            running = false;
            for (Formation formation : formations) {
                formation.plan(planner);
            }
        }

        if (clockEvent.getEventTypes().contains(ClockEventType.FINISHED)) {
            LOG.log(Level.INFO, "********** Scenario Ended at  {0}", Clock.INSTANCE);
            return;
        }

        firePropertyChange(CLOCK_EVENT_PROPERTY, oldValue, clockEvent);
        if (running) {
            Clock.INSTANCE.tick();
        }
    }

    public void act() {
//        LOG.log(Level.INFO, "Act");
        actionSpace.resolveInteractions();
        for (Unit unit : units) {
            unit.act();
        }
    }

    public void schedule() {
//        LOG.log(Level.INFO, "Schedule");
        for (Unit unit : units) {
            unit.schedule();
            unit.commit(actionSpace);
        }
    }

    public void perceive() {
//        LOG.log(Level.INFO, "Perceive");
        Tile[][] map = scenario.getBoard().getMap();
        for (int i = 0; i < map.length; i++) {
            Tile[] tiles = map[i];
            for (int j = 0; j < tiles.length; j++) {
                Tile tile = tiles[j];
                tile.updateKnowledge();
            }
        }

        for (Unit unit : units) {
            unit.perceive();
        }
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    public Planner getPlanner() {
        return planner;
    }
}
