package ares.engine;

import ares.application.gui.ProgressMonitor;
import ares.engine.time.Phase;
import ares.engine.time.ClockEventType;
import ares.engine.time.ClockEvent;
import static ares.engine.RealTimeEngine.CLOCK_EVENT_PROPERTY;
import static ares.engine.RealTimeEngine.SCENARIO_PROPERTY;
import ares.engine.action.ActionSpace;
import ares.engine.algorithms.pathfinding.AStar;
import ares.engine.algorithms.pathfinding.PathFinder;
import ares.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.model.AbstractBean;
import ares.engine.time.Clock;
import ares.scenario.Scenario;
import ares.scenario.board.Tile;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import ares.test.Stopwatch;
import java.util.*;
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
     * List of available (on-board) units *
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
//    /**
//     * Executor service to perform tasks concurrently (eg. planning)
//     */
//    private ExecutorService executor;
    /**
     * Action space is used to co-locate actions to find and solve interactions, eg. when several units participate in a
     * single combat.
     */
    private ActionSpace actionSpace;
    ProgressMonitor monitor;

    public RealTimeEngine() {
        units = new ArrayList<>();
        formations = new ArrayList<>();
        phase = Phase.PERCEIVE;
        running = false;
//        executor = Executors.newCachedThreadPool();
        actionSpace = new ActionSpace();
        monitor = new ProgressMonitor(null, "Busy", "Loading scenario", ProgressMonitor.Options.SHOW_PERCENT_COMPLETE);
    }

    /**
     * Sets a new scenario and initializes it
     *
     * @param scenario
     */
    public void setScenario(Scenario scenario) {
        monitor.show();
        Clock.INSTANCE.setEngine(this);
        Scenario oldValue = this.scenario;
        this.scenario = scenario;
        if (scenario != null) {
            pathFinder = new AStar(new MinimunDistance(DistanceCalculator.DELTA));
            for (Force force : scenario.getForces()) {
                for (Formation formation : force.getFormations()) {
                    formations.add(formation);
                    units.addAll(formation.getAvailableUnits());
                    formation.initialize();
                }
            }

        }
        monitor.hide();
        firePropertyChange(SCENARIO_PROPERTY, oldValue, scenario);
    }

    /**
     * Initial activation of an scenario to ensure all active formations have operational plans ready for execution.
     * This behavior is separated from method {@code setScenario()} because planning can be costly in some scenarios.
     * That way the board can be rendered before actually computing plans.
     */
    public void activate() {
        Stopwatch watch = new Stopwatch();
        watch.start();

        if (Clock.INSTANCE.getTurn() == 0) {
            startNewTurn();
            schedule();
        }
        watch.stop();
        System.out.println("Activation " + watch);
    }

    private void startNewTurn() {
        monitor.setStatus("Starting new turn");
        int formationsChecked = 0;
        monitor.setProgress(0, formationsChecked, formations.size());
        monitor.show();
        int turn = Clock.INSTANCE.getTurn();
        LOG.log(Level.INFO, "++++++++++ New Turn: {0}", turn);
        running = false;
        // Check for activation
        for (Formation formation : formations) {
            if (!formation.isActive() && formation.mustBeActivated()) {
                formation.activate();
                formation.plan(pathFinder);
            }
            monitor.setProgress(0, formationsChecked++, formations.size());
        }
        monitor.hide();
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void resume() {
        LOG.log(Level.INFO, "*** Engine started {0}", Clock.INSTANCE);
        running = true;
        Clock.INSTANCE.tick();
    }

    public void step() {
        LOG.log(Level.INFO, "*** Engine started for one time tick {0}", Clock.INSTANCE);
        Clock.INSTANCE.tick();
    }

    public void pause() {
        LOG.log(Level.INFO, "*** Engine stopped {0}", Clock.INSTANCE);
        running = false;
    }

    public void update(ClockEvent clockEvent) {
//        LOG.log(Level.INFO, "+++++ New Time:  {0}", clock);
        ClockEvent oldValue = this.clockEvent;
        this.clockEvent = clockEvent;
        Set<ClockEventType> clockEventTypes = clockEvent.getEventTypes();

        if (clockEventTypes.contains(ClockEventType.TURN)) {
            startNewTurn();
        }
        do {
            phase.run(this);
            phase = phase.getNext();
        } while (phase != Phase.PERCEIVE);

        if (clockEventTypes.contains(ClockEventType.DAY)) {
            LOG.log(Level.INFO, "++++++++++ New Day: {0}", Clock.INSTANCE.getTurn());
            for (Unit unit : units) {
                unit.updateMaxValues();
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
}
