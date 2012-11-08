package ares.engine.realtime;

import ares.engine.Engine;
import ares.engine.actors.FormationActor;
import ares.engine.actors.UnitActor;
import ares.platform.model.AbstractModel;
import ares.scenario.Scenario;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngine extends AbstractModel<Engine> implements Engine, Runnable {

    public static final String SCENARIO_PROPERTY = "Scenario";
    public static final String CLOCK_EVENT_PROPERTY = "ClockEvent";
    private Scenario scenario;
    private Phase phase;
    private List<UnitActor> unitActors;
    private List<FormationActor> formationActors;
    private Clock clock;
    private ClockEvent clockEvent;
    private static final Logger LOG = Logger.getLogger(RealTimeEngine.class.getName());

    public RealTimeEngine() {
        unitActors = new ArrayList<>();
        formationActors = new ArrayList<>();
        phase = Phase.SCHEDULE;
    }

    public void initDefault() {
    }

    public void setScenario(Scenario scenario) {
        Scenario oldValue = this.scenario;
        this.scenario = scenario;
        if (scenario != null) {
            clock = new Clock(scenario.getCalendar(), this);
            for (Force force : scenario.getForces()) {
                for (Formation formation : force.getFormations()) {
                    for (Unit unit : formation.getActiveUnits()) {
                        unit.activate();
                        unitActors.add(new UnitActor(unit));
                    }
                    formationActors.add(new FormationActor(formation, unitActors, this));
                }
            }
        }
        firePropertyChange(SCENARIO_PROPERTY, oldValue, scenario);
    }

    @Override
    public Scenario getScenario() {
        return scenario;
    }

    @Override
    public void start() {
        clock.start();
    }

    @Override
    public void stop() {
        clock.stop();
    }

    @Override
    public void update(ClockEvent clockEvent) {
        do {
            phase.run(this);
            phase = phase.getNext();
        } while (phase != Phase.ACT);
        ClockEvent oldValue = this.clockEvent;
        clockEvent = clockEvent;
        firePropertyChange(CLOCK_EVENT_PROPERTY, oldValue, clockEvent);
        Set<ClockEventType> clockEventTypes = clockEvent.getEventTypes();
        if (clockEventTypes.contains(ClockEventType.TURN)) {
            LOG.log(Level.INFO, "Turn: {0} - Time: {1}", new Object[]{clock.getTurn(), toString()});
            for (FormationActor formationActor : formationActors) {
                formationActor.plan(clock);
            }
        }
        if (clockEvent.getEventTypes().contains(ClockEventType.FINISHED)) {
            LOG.log(Level.INFO, "Scenario ended !!");
        } else {
            clock.tick();
        }
    }

    void activate() {
        LOG.log(Level.INFO, "Activate");
    }

    protected void act() {
        LOG.log(Level.INFO, "Act");
        for (UnitActor unitActor : unitActors) {
            unitActor.act(clock);
        }
        
    }

    protected void schedule() {
        LOG.log(Level.INFO, "Schedule");
        for (UnitActor unitActor : unitActors) {
            unitActor.schedule(clock);
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
