package ares.application.analyser.benchmark;

import java.io.File;
import java.util.List;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfindingProblemGenerator {
    private List<File> scenarioFiles;
    /**
     * Number of locations to use per scenario
     */
    private int locationsPerScenario;
    /**
     * Minimum accepted size of the problems generated, where the size is computed as the distance in hexagons between the
     * origin and the destination locations (alternatively size could be defined as the lenght or cost of the optimal solution)
     */
    private int minimumProblemSize;
    private ProblemGeneratorMode problemGeneratorMode;

    public PathfindingProblemGenerator(List<File> scenarioFiles, int locationsPerScenario, int minimumProblemSize, ProblemGeneratorMode problemGeneratorMode) {
        this.scenarioFiles = scenarioFiles;
        this.locationsPerScenario = locationsPerScenario;
        this.minimumProblemSize = minimumProblemSize;
        this.problemGeneratorMode = problemGeneratorMode;
    }


    public List<File> getScenarioFiles() {
        return scenarioFiles;
    }

    public int getLocationsPerScenario() {
        return locationsPerScenario;
    }

    public int getMinimumProblemSize() {
        return minimumProblemSize;
    }

    public ProblemGeneratorMode getProblemGeneratorMode() {
        return problemGeneratorMode;
    }

    private enum ProblemGeneratorMode {
        /**
         * Locations distributed randomly
         */
        RANDOM,
        /**
         * Locations distributed systematically along the map perimeter
         */
        PERIMETER,
        /**
         * Locations distributed systematically across the entire map
         */
        MATRIX;
    }
}
