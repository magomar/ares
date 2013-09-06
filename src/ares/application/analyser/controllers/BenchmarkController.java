package ares.application.analyser.controllers;

import ares.application.analyser.benchmark.AlgorithmResults;
import ares.application.analyser.benchmark.PathfindingProblem;
import ares.application.analyser.benchmark.PathfindingSolution;
import ares.application.analyser.boundaries.interactors.AlgorithmSelectionInteractor;
import ares.application.analyser.boundaries.interactors.PathfinderBenchmarkInteractor;
import ares.application.analyser.boundaries.interactors.ProblemGeneratorInteractor;
import ares.application.analyser.boundaries.viewers.AlgorithmSelectionViewer;
import ares.application.analyser.boundaries.viewers.BenchmarkViewer;
import ares.application.analyser.boundaries.viewers.PathfinderToolsViewer;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.data.wrappers.equipment.EquipmentDB;
import ares.platform.engine.algorithms.pathfinding.*;
import ares.platform.engine.algorithms.pathfinding.costfunctions.CostFunction;
import ares.platform.engine.algorithms.pathfinding.costfunctions.EstimatedCostFunctions;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.engine.algorithms.pathfinding.heuristics.EnhancedMinimunDistance;
import ares.platform.engine.algorithms.pathfinding.heuristics.Heuristic;
import ares.platform.engine.algorithms.pathfinding.heuristics.MinimunDistance;
import ares.platform.engine.movement.MovementType;
import ares.platform.io.AresFileType;
import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Board;
import ares.platform.scenario.forces.Unit;
import ares.platform.scenario.forces.UnitFactory;
import ares.platform.util.SingleThreadStopwatch;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public class BenchmarkController implements AlgorithmSelectionInteractor, ProblemGeneratorInteractor {
    private final BenchmarkViewer benchmarkView;
    private final AlgorithmSelectionController algorithmSelectionController;
    private final ProblemGeneratorController problemGeneratorController;
    private Unit testUnit;
    private Map<String, List<PathfindingProblem>> problems;
    private Map<Pathfinder, List<PathfindingSolution>> solutions;


    public BenchmarkController(PathfinderBenchmarkInteractor interactor, PathfinderToolsViewer mainView) {
        benchmarkView = interactor.getPathfinderBenchmarkView();

        Pathfinder[] allPathfinders = {new AStar(), new BeamSearch(), new BidirectionalSearch()};
        Heuristic[] allHeuristics = {MinimunDistance.create(DistanceCalculator.DELTA), EnhancedMinimunDistance.create(DistanceCalculator.DELTA)};
        CostFunction[] allCostFunctions = EstimatedCostFunctions.values();

        algorithmSelectionController = new AlgorithmSelectionController(this, allPathfinders, allHeuristics, allCostFunctions, allPathfinders[0]);
        problemGeneratorController = new ProblemGeneratorController(this, ResourcePath.SCENARIOS.getSubPath("Classic TOAW"));

        ComboBoxModel<MovementType> movementTypeComboModel = new DefaultComboBoxModel<>(MovementType.values());
        benchmarkView.setMovementTypeComboBoxModel(movementTypeComboModel);
        benchmarkView.addMovementTypeActionListener(new ChangeMovementTypeActionListener());
        movementTypeComboModel.setSelectedItem(MovementType.MOTORIZED);

        benchmarkView.addExecuteBenchmarkActionListener(new ExecuteBenchmarkActionListener());
    }


    @Override
    public AlgorithmSelectionViewer getAlgorithmSelectionView() {
        return benchmarkView.getAlgorithmSelectionView();
    }

    @Override
    public ProblemGeneratorViewer getProblemGeneratorView() {
        return benchmarkView.getProblemGeneratorView();
    }

    @Override
    public void setProblems(Map<String, List<PathfindingProblem>> problems) {
        this.problems = problems;
    }


    private class ChangeMovementTypeActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox source = (JComboBox) e.getSource();
            testUnit = UnitFactory.createTestUnit((MovementType) source.getSelectedItem());
        }
    }


    private class ExecuteBenchmarkActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (problems == null) return;
            AlgorithmSelectionViewer algorithmSelectionView = benchmarkView.getAlgorithmSelectionView();
            File equipmentFile = ResourcePath.EQUIPMENT.getFile("ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension());
            EquipmentDB equipmentDB = FileIO.unmarshallJson(equipmentFile, EquipmentDB.class);
            ListModel<Pathfinder> pathfinderListModel = algorithmSelectionView.getSelectedAlgorithmsListModel();
            Pathfinder[] pathfinders = new Pathfinder[pathfinderListModel.getSize()];
            AlgorithmResults[] algorithmResults = new AlgorithmResults[pathfinders.length];
            for (int i = 0; i < pathfinderListModel.getSize(); i++) {
                pathfinders[i] = pathfinderListModel.getElementAt(i);
                algorithmResults[i] = new AlgorithmResults(pathfinders[i].toStringVerbose());
            }
            for (List<PathfindingProblem> scenarioProblems : problems.values()) {
                File file = scenarioProblems.get(0).getScenarioFilePath().toFile();
                Scenario scenario = FileIO.loadScenario(file, equipmentDB);
                for (int i = 0; i < pathfinders.length; i++) {
                    Pathfinder pathfinder = pathfinders[i];
                    List<PathfindingSolution> solutions = solvePathfindingProblem(scenario, scenarioProblems, pathfinder);
                    System.out.println(String.format("Solutions found for %s (%d/%d) by %s", scenario.getName(), solutions.size(), scenarioProblems.size(), pathfinder));
                    algorithmResults[i].addSolutions(solutions, scenarioProblems.size());
                }
            }
            for (int i = 0; i < pathfinders.length; i++) {
                algorithmResults[i].computeStatistics();
//                benchmarkView.log(algorithmResults[i]);
                System.out.println(algorithmResults[i]);
            }
        }
    }

    private List<PathfindingSolution> solvePathfindingProblem(Scenario scenario, List<PathfindingProblem> problems, Pathfinder pathfinder) {
        List<PathfindingSolution> solutions = new ArrayList<>();
        Board board = scenario.getBoard();
        for (PathfindingProblem problem : problems) {
            SingleThreadStopwatch stopwatch= new SingleThreadStopwatch();
            stopwatch.start();
            ExtendedPath extendedPath = pathfinder.getExtendedPath(board.getTile(problem.getOrigin()), board.getTile(problem.getDestination()), testUnit);
            stopwatch.stop();
            if (extendedPath == null) continue;
            PathfindingSolution solution = new PathfindingSolution(extendedPath.size(), extendedPath.getLast().getG(), extendedPath.getNumNodesVisited(), stopwatch.getTotalTime());
            solutions.add(solution);
//            System.out.println(solution.toString());
        }
        return solutions;
    }
}
