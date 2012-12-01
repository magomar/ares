package ares.engine.algorithms.routing;

import ares.application.models.board.TileModel;
import ares.scenario.board.Direction;
import java.util.EnumSet;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface PathFinder {
    
    void setPathType(int type);
    void setHeuristic(Heuristic h);
    void avoidEnemies(boolean b);
    Path getPath(TileModel o, TileModel d);
    EnumSet<Direction> getDirections(TileModel o, TileModel d);
}
