package ares.engine.command;

import ares.scenario.board.Tile;
import ares.scenario.forces.Force;

/**
 *
 * @author Mario Gómez Martínez <margomez at dsic.upv.es>
 */
public class Objective implements Comparable {
    private Tile location;
    private int priority;
    private boolean achieved = false;

    public Objective(Tile location, int priority) {
        this.location = location;
        this.priority = priority;
    }

    public boolean isAchieved() {
        return achieved;
    }

    public void setAchieved(boolean achieved) {
        this.achieved = achieved;
    }

    public Tile getLocation() {
        return location;
    }

    public int getPriority() {
        return priority;
    }
    
    public boolean checkAchieved(Force force) {
        return location.isAlliedTerritory(force);
    }

    @Override
    public int compareTo(Object o) {
        Objective objective = (Objective) o;
        return priority - objective.priority;
    }


}
