package ares.scenario;

import ares.application.models.ScenarioModel;
import ares.data.jaxb.EquipmentDB;
import ares.data.jaxb.OOB;
import ares.platform.model.UserRole;
import ares.scenario.assets.AssetTypes;
import ares.scenario.board.Board;
import ares.application.models.board.BoardGraphicsModel;
import ares.platform.model.ModelProvider;
import ares.scenario.forces.Force;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Class containing all the state of a scenario
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Scenario implements ModelProvider<ScenarioModel> {

    public final AssetTypes assetTypes;
    private final String name;
    private Board board;
    private Scale scale;
    private Force[] forces;
    private AresCalendar calendar;
    private BoardGraphicsModel boardInfo;
    private Map<UserRole, ScenarioModel> models;

    public Scenario(ares.data.jaxb.Scenario scenario, EquipmentDB eqpDB) {
        name = scenario.getHeader().getName();
        scale = new Scale((int) (scenario.getEnvironment().getScale() * 1000));
        calendar = new AresCalendar(scenario.getCalendar());
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

        System.out.println(
                "Scenario loaded: " + toString());

        boardInfo = new BoardGraphicsModel(board);
        models = new HashMap<>();
        models.put(UserRole.GOD, new ScenarioModel(this, UserRole.GOD));
        for (Force force : forces) {
            models.put(UserRole.getForceRole(force), new ScenarioModel(this, UserRole.getForceRole(force)));
        }

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

    public List<Unit> getActiveUnits() {
        List<Unit> activeUnits = new ArrayList<>();
        for (Force force : forces) {
            activeUnits.addAll(force.getActiveUnits());
        }
        return activeUnits;
    }

    public Scale getScale() {
        return scale;
    }

    public String getName() {
        return name;
    }

    public AresCalendar getCalendar() {
        return calendar;
    }

    public AssetTypes getAssetTypes() {
        return assetTypes;
    }

    @Override
    public String toString() {
        return "Scenario{" + "Scale=" + scale + ", calendar=" + calendar + '}';
    }

    @Override
    public ScenarioModel getModel(UserRole role) {
        return models.get(role);
    }
}
