package ares.application.analyser.benchmark;

import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmConfiguration {
    private Pathfinder pathfinder;
    private Heuristic heuristic;
    private CostFunction costFunction;

    public Pathfinder getPathfinder() {
        return pathfinder;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public CostFunction getCostFunction() {
        return costFunction;
    }

    public void setPathfinder(Pathfinder pathfinder) {
        this.pathfinder = pathfinder;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public void setCostFunction(CostFunction costFunction) {
        this.costFunction = costFunction;
    }
}
