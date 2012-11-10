package ares.model.scenario;

/**
 *
 * @author Mario Gomez <margomez at dsic.upv.es>
 */
public class Scale {
    /**
     * Distance between (the centers of) two tiles measured in meters
     */
    private int distance;
    /**
     * Area of an hexagon, in square meters
     */
    private int area;
    /**
     * Minimun number of horses and vehicles in a tile at which units start
     * having movement and combat enalties
     */
    private int criticalDensity;
    /**
     * Constant used to compute #criticalDensity
     */
    private static final double DENSITY_FACTOR = 5 / 2;
    private static final int BASE_DENSITY = 50;

    public Scale(int distance) {
        this.distance = distance;
        double apotema = distance / 2;
        area = (int) (2*apotema*apotema * Math.sqrt(3.0) / 1000);
        criticalDensity = (int) (BASE_DENSITY + DENSITY_FACTOR * area/1000);
    }
   
    public double getArea() {
        return area;
    }

    public int getCriticalDensity() {
        return criticalDensity;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Scale{" + "distance=" + distance + ", area=" + area + ", criticalDensity=" + criticalDensity + '}';
    }

    
}
