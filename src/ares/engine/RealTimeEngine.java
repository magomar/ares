package ares.engine;

import ares.application.models.ScenarioModel;
import ares.engine.actors.FormationActor;
import ares.engine.actors.UnitActor;
import ares.engine.algorithms.routing.AStar;
import ares.engine.algorithms.routing.PathFinder;
import ares.platform.model.AbstractBean;
import ares.platform.model.UserRole;
import ares.scenario.Clock;
import ares.scenario.Scenario;
import ares.scenario.board.Tile;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngine extends AbstractBean {

    public static final String SCENARIO_PROPERTY = "Scenario";
    public static final String CLOCK_EVENT_PROPERTY = "ClockEvent";
    private Scenario scenario;
    private Phase phase;
    private List<UnitActor> unitActors;
    private List<FormationActor> formationActors;
    private ClockEvent clockEvent;
    private boolean running;
    private PathFinder pathFinder;
    private ExecutorService executor;
    private static final Logger LOG = Logger.getLogger(RealTimeEngine.class.getName());

    public RealTimeEngine() {
        unitActors = new ArrayList<>();
        formationActors = new ArrayList<>();
        phase = Phase.SCHEDULE;
        running = false;
        executor = Executors.newCachedThreadPool();
    }

    public void setScenario(Scenario scenario) {
        Scenario oldValue = this.scenario;
        this.scenario = scenario;
        pathFinder = new AStar(scenario.getBoard().getWidth() * scenario.getBoard().getHeight());
        if (scenario != null) {
            Clock.INSTANCE.setEngine(this);
            for (Force force : scenario.getForces()) {
                for (Formation formation : force.getFormations()) {
                    FormationActor formationActor = new FormationActor(formation, this);
                    formationActors.add(formationActor);
                    unitActors.addAll(formationActor.getUnitActors());
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

        if (clockEventTypes.contains(ClockEventType.TURN)) {
            LOG.log(Level.INFO, "++++++++++ New Turn: {0}", Clock.INSTANCE.getTurn());
            running = false;
            for (FormationActor formationActor : formationActors) {
                formationActor.plan();
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

//    void activate() {
//        LOG.log(Level.INFO, "Activate");
//    }
    protected void act() {
//        LOG.log(Level.INFO, "Act");
        for (UnitActor unitActor : unitActors) {
            unitActor.act(clockEvent);
        }

    }

    protected void schedule() {
//        LOG.log(Level.INFO, "Schedule");
        for (UnitActor unitActor : unitActors) {
            unitActor.schedule(clockEvent);
        }
    }

    protected void perceive() {
//        LOG.log(Level.INFO, "Perceive");
        Tile[][] map = scenario.getBoard().getMap();
        for (int i = 0; i < map.length; i++) {
            Tile[] tiles = map[i];
            for (int j = 0; j < tiles.length; j++) {
                Tile tile = tiles[j];
                tile.updateKnowledge();
            }
        }

        for (UnitActor unitActor : unitActors) {
            unitActor.perceive();
        }
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }
}
