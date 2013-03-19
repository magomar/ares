package ares.engine.command.tactical;

import ares.engine.algorithms.routing.PathFinder;
import ares.scenario.board.Tile;
import ares.scenario.forces.Unit;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public interface TacticalMissionFactory {
    TacticalMission getNewTacticalMission(Unit unit, Tile target, PathFinder pathFinder);
}
