package ares.ai.planner;

import ares.engine.command.TacticalMissionType;
import ares.scenario.board.Tile;

/**
 *
 * @author Saúl Esteban
 */
public class UnitTask extends Task{
    
    private Tile position;
    private TacticalMissionType tacticalMissionType;
    
    public UnitTask(TacticalMissionType tm, Tile p, Tile g) {
        super(g);
        position = p;
        tacticalMissionType = tm;
    }
    
    public Tile getPosition() {
        return position;
    }
    
    public TacticalMissionType getTacticalMissionType() {
        return tacticalMissionType;
    }
}
