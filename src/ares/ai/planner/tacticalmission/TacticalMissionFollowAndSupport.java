package ares.ai.planner.tacticalmission;

import ares.scenario.Scenario;
import ares.engine.action.Action;
import ares.ai.planner.UnitTaskNode;
import ares.scenario.board.Tile;
import java.util.LinkedList;

/**
 *
 * @author Saúl Esteban
 */
public class TacticalMissionFollowAndSupport extends AbstractTacticalMission {

    public TacticalMissionFollowAndSupport(Scenario scenario) {
        super(scenario);
    }

    @Override
    public int plan(UnitTaskNode taskNode) {

        Tile position, allyPosition = null;
        LinkedList<Tile> possiblePositions = new LinkedList<>();
        Action[] mainMission = new Action[taskNode.getUnitForSupport().getPendingActions().size()];
        taskNode.getUnitForSupport().getPendingActions().toArray(mainMission);


        return 0;
    }
}
