package ares.application.analyser.benchmark;

import java.awt.*;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderProblem {
    /**
     * Origin coordinates of the path to be found
     */
    private Point origin;
    /**
     * Destination coordinates of the path to be found
     */
    private Point destination;
    /**
     * Distance in nodes between origin and destination
     */
    private int distance;
    /**
     * Name of the scenario
     */
    private String scenarioName;
    /**
     * Width of the scenario map (number of columns)
     */
    private int mapWidth;
    /**
     * Height of the scenario map (number of rows)
     */
    private int mapHeight;

    public Point getOrigin() {
        return origin;
    }

    public Point getDestination() {
        return destination;
    }

    public int getDistance() {
        return distance;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }
}
