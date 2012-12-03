package ares.engine.algorithms.routing;

import ares.application.models.board.TileModel;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public interface PathFinder {
    
    void setPathType(int type);
    public int getPathType();
    void setHeuristic(Heuristic h);
    void avoidEnemies(boolean b);
    boolean avoidingEnemies();
    Path getPath(TileModel o, TileModel d);
}
