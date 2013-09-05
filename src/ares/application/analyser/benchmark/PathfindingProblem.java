package ares.application.analyser.benchmark;

import java.awt.*;
import java.nio.file.Path;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfindingProblem {
    /**
     * Origin coordinates of the path to be found
     */
    private final Point origin;
    /**
     * Destination coordinates of the path to be found
     */
    private final Point destination;
    /**
     * Distance in nodes between origin and destination
     */
    private final int distance;
    /**
     * Name of the scenario
     */
    private final String scenarioName;
    /**
     * Path of the folder containing the scenario file
     */
    private final Path scenarioFilePath;
    /**
     * Width of the scenario map (number of columns)
     */
    private final int mapWidth;
    /**
     * Height of the scenario map (number of rows)
     */
    private final int mapHeight;

    public PathfindingProblem(Point origin, Point destination, int distance, String scenarioName, Path scenarioFilePath, int mapWidth, int mapHeight) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.scenarioName = scenarioName;
        this.scenarioFilePath = scenarioFilePath;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

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

    public Path getScenarioFilePath() {
        return scenarioFilePath;
    }

    @Override
    public String toString() {
        return "PathfindingProblem{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", distance=" + distance +
                ", scenarioName='" + scenarioName + '\'' +
                ", mapWidth=" + mapWidth +
                ", mapHeight=" + mapHeight +
                '}';
    }
}
