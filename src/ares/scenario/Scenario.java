package ares.scenario;

import ares.engine.time.Clock;
import ares.application.models.ScenarioModel;
import ares.application.graphics.BoardGraphicsModel;
import ares.data.jaxb.EquipmentDB;
import ares.data.jaxb.OOB;
import ares.platform.model.ModelProvider;
import ares.platform.model.UserRole;
import ares.scenario.assets.AssetTypes;
import ares.scenario.board.Board;
import ares.scenario.forces.Force;
import java.lang.ref.WeakReference;
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

    public WeakReference<AssetTypes> assetTypes;
    private String name;
    private Board board;
    private Force[] forces;
    private BoardGraphicsModel boardInfo;
    private Map<UserRole, ScenarioModel> models;

    public Scenario(ares.data.jaxb.Scenario scenario, EquipmentDB eqpDB) {
        name = scenario.getHeader().getName();
        Scale.INSTANCE.initialize((int) (scenario.getEnvironment().getScale() * 1000));
        Clock.INSTANCE.initialize(scenario.getCalendar());
        assetTypes = new WeakReference<>(new AssetTypes(eqpDB));
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

        boardInfo = new BoardGraphicsModel(board);
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

    public BoardGraphicsModel getBoardGraphicsModel() {
        return boardInfo;
    }

    public Force[] getForces() {
        return forces;
    }

    public String getName() {
        return name;
    }

    public AssetTypes getAssetTypes() {
        return assetTypes.get();
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
