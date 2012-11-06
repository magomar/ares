package ares.ai.planner;

import ares.scenario.board.Tile;

/**
 *
 * @author Saúl Esteban
 */
public abstract class Task {
    
    private Tile goal;
    
    public Task(Tile g) {
        goal = g;
    }
    
    public void setGoal(Tile g) {
        goal = g;
    }
    
    public Tile getGoal() {
        return goal;
    }
}
