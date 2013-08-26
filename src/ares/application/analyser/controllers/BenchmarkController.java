package ares.application.analyser.controllers;

import ares.application.analyser.boundaries.interactors.AlgorithmSelectionInteractor;
import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;
import ares.application.analyser.boundaries.interactors.ProblemGeneratorInteractor;
import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.platform.engine.algorithms.pathfinding.AStar;
import ares.platform.engine.algorithms.pathfinding.BeamSearch;
import ares.platform.engine.algorithms.pathfinding.BidirectionalSearch;
import ares.platform.engine.algorithms.pathfinding.Pathfinder;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.costfunctions.EstimatedCostFunctions;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.EnhancedMinimunDistance;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;

import java.util.logging.Logger;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class BenchmarkController implements AlgorithmSelectionInteractor, ProblemGeneratorInteractor {

    private static final Logger LOG = Logger.getLogger(BenchmarkController.class.getName());
    private final BenchmarkViewer benchmarkView;
    private final AlgorithmSelectionController algorithmSelectionController;
    private final ProblemGeneratorController problemGeneratorController;

    
    public BenchmarkController(PathfinderBenchmarkInteractor interactor, PathfinderToolsViewer mainView) {
        benchmarkView = interactor.getPathfinderBenchmarkView();

        Pathfinder[] allPathfinders = {new AStar(), new BeamSearch(), new BidirectionalSearch()};
        Heuristic[] allHeuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        CostFunction[] allCostFunctions = EstimatedCostFunctions.values();

        algorithmSelectionController = new AlgorithmSelectionController(this,allPathfinders, allHeuristics, allCostFunctions, allPathfinders[0]);
        problemGeneratorController = new ProblemGeneratorController();

    }


    @Override
    public AlgorithmSelectionViewer getAlgorithmSelectionView() {
        return benchmarkView.getAlgorithmSelectionView();
    }

    @Override
    public ProblemGeneratorViewer getProblemGeneratorSelectionView() {
        return benchmarkView.getProblemGeneratorView();
    }
}
