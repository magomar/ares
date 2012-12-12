package ares.engine.algorithms.routing;

import ares.scenario.board.Tile;

/**
 *
 * @author Heine <heisncfr@inf.upv.es>
 */
public abstract class AbstractPathFinder implements PathFinder{
    
    //Path type
    public final static int FASTEST = 0;
    public final static int SHORTEST = 1;
    
    private static boolean AVOID_ENEMIES = false;
    
    protected int pathType;
    protected int algorithm;
    protected Heuristic heuristic = new DistanceCalculator(DistanceCalculator.DELTA);

    public AbstractPathFinder(){
        this(FASTEST);
    }
    public AbstractPathFinder(int type) {
        this.pathType = type;
    }
    
    /**
     * Set if paths should void enemies
     * @param b 
     */
    @Override
    public void avoidEnemies(boolean b){
        AVOID_ENEMIES = b;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public boolean avoidingEnemies(){
        return AVOID_ENEMIES;
    }
    
    @Override
    public void setPathType(int type) {
        this.pathType = type;
    }
    
    @Override
    public int getPathType(){
        return pathType;
    }
    
    @Override
    public void setHeuristic(Heuristic h){
        this.heuristic = h;
    }
    
    @Override
    abstract public Path getPath(Tile o, Tile d);
}
