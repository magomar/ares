package ares.platform.engine.command.tactical.missions;

import ares.platform.engine.action.ActionSpace;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.command.tactical.TacticalMission;
import ares.platform.engine.command.tactical.TacticalMissionType;
import ares.platform.scenario.board.Tile;
import ares.platform.scenario.forces.Unit;

/**
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class AttackByFire extends TacticalMission {

    public AttackByFire(TacticalMissionType type, Unit unit, Tile target, ActionSpace actionSpace) {
        super(type, unit, target, actionSpace);
    }


    @Override
    public void plan(Pathfinder pathFinder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
