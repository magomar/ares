package ares.engine.realtime;

import ares.engine.Engine;
import ares.engine.actors.FormationActor;
import ares.engine.actors.UnitActor;
import ares.scenario.forces.Force;
import ares.scenario.forces.Formation;
import ares.scenario.forces.Unit;
import ares.scenario.Scenario;
import ares.platform.model.AbstractModel;
import java.util.*;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class RealTimeEngine extends AbstractModel<Engine> implements Engine {

    public static final String SCENARIO_PROPERTY = "Scenario";
    private Scenario scenario;
    private Phase phase;
    private List<UnitActor> unitActors;
//    private List<FormationActor> formationActors;
    private Clock clock;

    public RealTimeEngine() {
        unitActors = new ArrayList<>();
        phase = Phase.ACTIVATE;
    }

    public void initDefault() {
    }

    public void setScenario(Scenario scenario) {
        Scenario oldValue = this.scenario;
        this.scenario = scenario;
        if (scenario != null) {
            clock = new Clock(scenario.getCalendar(), this);
            activate();
        }
        
        firePropertyChange(SCENARIO_PROPERTY, oldValue, scenario);
    }

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
        phase = phase.getNext();
        phase.run(this);
        //Do something depending on the clock event type (tick, turn, etc.)
        if (phase == Phase.ACT) {
            clock.tick();
        }
    }
// TODO activate units (its actors)

    protected void activate() {
        for (Force force : scenario.getForces()) {
            for (Formation formation : force.getFormations()) {
                for (Unit unit : formation.getActiveUnits()) {
                    unit.activate();
                    unitActors.add(new UnitActor(unit));
                }
            }
        }
    }

    protected void act() {
        // TODO act
    }

    protected void schedule() {
        //TODO schedule
    }
}
