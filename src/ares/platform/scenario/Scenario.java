package ares.platform.scenario;

import ares.platform.engine.time.Clock;
import ares.application.shared.models.ScenarioModel;
import ares.data.jaxb.EquipmentDB;
import ares.data.jaxb.OOB;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.platform.scenario.assets.AssetTypes;
import ares.platform.scenario.board.Board;
import ares.platform.scenario.forces.Force;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Class containing all the state of a scenario
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Scenario implements ModelProvider<ScenarioModel> {

    private AssetTypes assetTypes;
    private String name;
    private Board board;
    private Force[] forces;
    private Map<UserRole, ScenarioModel> models;
    private String description;

    public Scenario(ares.data.jaxb.Scenario scenario, EquipmentDB eqpDB) {
        name = scenario.getHeader().getName();
        description = scenario.getHeader().getDescription();
        Scale.INSTANCE.initialize((int) (scenario.getEnvironment().getScale() * 1000));
        Clock.INSTANCE.initialize(scenario.getCalendar());
        assetTypes = new AssetTypes(eqpDB);
        board = new Board(scenario);
        OOB oob = scenario.getOOB();
        Collection<ares.data.jaxb.Force> scenForces = oob.getForce();
        forces = new Force[scenForces.size()];
        for (ares.data.jaxb.Force force : oob.getForce()) {
            forces[force.getId()] = new Force(force, this);
        }
        for (Force force : forces) {
            force.initialize(forces);
        }
        board.initialize(scenario, this, forces);

        Logger.getLogger(Scenario.class.getName()).log(Level.FINE, "Scenario loaded: {0}", toString());
        models = new HashMap<>();
        models.put(UserRole.GOD, new ScenarioModel(this, UserRole.GOD));
        for (Force force : forces) {
            models.put(UserRole.getForceRole(force), new ScenarioModel(this, UserRole.getForceRole(force)));
        }
        assetTypes = null;
    }

    public Board getBoard() {
        return board;
    }

    public Force[] getForces() {
        return forces;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AssetTypes getAssetTypes() {
        return assetTypes;
    }

    @Override
    public String toString() {
        return "Scenario{" + "Scale=" + Scale.INSTANCE + ", calendar=" + Clock.INSTANCE + '}';
    }

    @Override
    public ScenarioModel getModel(UserRole role) {
        return models.get(role);
    }

}
