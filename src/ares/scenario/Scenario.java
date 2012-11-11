package ares.scenario;

import ares.data.jaxb.EquipmentDB;
import ares.data.jaxb.OOB;
import ares.scenario.assets.AssetTypes;
import ares.scenario.board.Board;
import ares.scenario.board.BoardInfo;
import ares.scenario.forces.Force;
import ares.scenario.forces.Unit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * Class containing all the state of a scenario
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public final class Scenario {

    private final String name;
    private Board board;
    private Scale scale;
    private Force[] forces;
    private AresCalendar calendar;
    private BoardInfo boardInfo;
    public final AssetTypes assetTypes;

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
            forces[force.getId() - 1] = new Force(force, this);
            //TODO modify ToawToAres to make force indexes in [0.. n-1] instead of substracting 1 here
        }

        board.initialize(scenario,
                this, forces);

        System.out.println(
                "Scenario loaded: " + toString());

        boardInfo = new BoardInfo(board);
    }

    public Board getBoard() {
        return board;
    }

    public BoardInfo getBoardInfo() {
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
}
