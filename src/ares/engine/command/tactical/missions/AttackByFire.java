package ares.engine.command.tactical.missions;

import ares.engine.algorithms.routing.PathFinder;
import ares.engine.command.tactical.TacticalMission;
import ares.engine.command.tactical.TacticalMissionType;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class AttackByFire extends TacticalMission {

    public AttackByFire(TacticalMissionType type, Unit unit, Tile target) {
        super(type, unit, target);
    }


    @Override
    public void plan(PathFinder pathFinder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
