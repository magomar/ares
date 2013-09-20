package ares.application.analyser.benchmark;

import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.movement.MovementType;

import java.util.List;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class Benchmark {
    // TODO use this class inside BenchmarkController, and move here the logic, add locig to store in file
    private List<MovementType> movementTypes;
    private List<Pathfinder> algorithms;
    private List<PathfindingProblem> problems;
    private PathfindingProblemGenerator pathfinderProblemGenerator;

    public void runBenchmark(){

    }
}
