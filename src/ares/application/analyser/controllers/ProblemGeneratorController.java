package ares.application.analyser.controllers;

import ares.application.analyser.benchmark.PathfindingProblem;
import ares.application.analyser.boundaries.interactors.ProblemGeneratorInteractor;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;
import ares.data.wrappers.equipment.EquipmentDB;
import ares.platform.engine.algorithms.pathfinding.heuristics.DistanceCalculator;
import ares.platform.io.AresFileType;
import ares.platform.io.FileIO;
import ares.platform.io.ResourcePath;
import ares.platform.scenario.Scenario;
import ares.platform.scenario.board.Board;
import ares.platform.scenario.board.Tile;
import temp.RandomGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class ProblemGeneratorController {
    private final ProblemGeneratorViewer problemGeneratorView;
    private final ProblemGeneratorInteractor problemGeneratorInteractor;
    private final Map<String, List<PathfindingProblem>> problems;

    public ProblemGeneratorController(ProblemGeneratorInteractor interactor, Path defaultScenarioPath) {
        this.problemGeneratorInteractor = interactor;
        problemGeneratorView = interactor.getProblemGeneratorView();
        problems = new HashMap<>();
        problemGeneratorView.updateScenarioPath(defaultScenarioPath.toString());
        File folder = defaultScenarioPath.toFile();
        if (!folder.isDirectory()) folder = ResourcePath.SCENARIOS.getFolderPath().toFile();
        List<File> files = FileIO.listFiles(folder, AresFileType.SCENARIO.getFileTypeFilter(), true);
        DefaultListModel<String> scenarioListModel = new DefaultListModel<>();
        for (File file : files) {
            scenarioListModel.addElement(file.getName());
        }
        problemGeneratorView.setScenarioListModel(scenarioListModel);
        problemGeneratorView.setSelectedScenarioListModel(new DefaultListModel<String>());
        problemGeneratorView.setScenarioSelectionModel(new DefaultListSelectionModel());
        problemGeneratorView.setSelectedScenarioSelectionModel(new DefaultListSelectionModel());
        problemGeneratorView.updateNumProblems(100);

        problemGeneratorView.addAddScenarioActionListener(new AddScenarioActionListener());
        problemGeneratorView.addRemoveScenarioActionListener(new RemoveScenarioActionListener());
        problemGeneratorView.addGenerateProblemsActionListener(new GenerateProblemsActionListener());
    }

    private class AddScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> scenarioListModel = (DefaultListModel<String>) problemGeneratorView.getScenarioListModel();
            DefaultListModel<String> selectedScenarioListModel = (DefaultListModel<String>) problemGeneratorView.getSelectedScenarioListModel();
            DefaultListSelectionModel scenarioSelectionModel = (DefaultListSelectionModel) problemGeneratorView.getScenarioSelectionModel();
            for (int i = scenarioSelectionModel.getMinSelectionIndex(); i < scenarioSelectionModel.getMaxSelectionIndex() + 1; i++) {
                if (scenarioSelectionModel.isSelectedIndex(i)) {
                    selectedScenarioListModel.addElement(scenarioListModel.elementAt(i));
                }
            }

        }
    }


    private class RemoveScenarioActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> selectedScenarioListModel = (DefaultListModel<String>) problemGeneratorView.getSelectedScenarioListModel();
            DefaultListSelectionModel selectedScenarioSelectionModel = (DefaultListSelectionModel) problemGeneratorView.getSelectedScenarioSelectionModel();
            for (int i = selectedScenarioSelectionModel.getMinSelectionIndex(); i < selectedScenarioSelectionModel.getMaxSelectionIndex() + 1; i++) {
                if (selectedScenarioSelectionModel.isSelectedIndex(i)) selectedScenarioListModel.remove(i);
            }
        }
    }

    private class GenerateProblemsActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultListModel<String> selectedScenarioListModel = (DefaultListModel<String>) problemGeneratorView.getSelectedScenarioListModel();
            String folder = problemGeneratorView.getScenarioPath();
            int numScenarios = selectedScenarioListModel.getSize();
            int numProblems = problemGeneratorView.getNumProblems();
            int totalScenarioSize = 0;
            int[] scenarioSize = new int[numScenarios];
            File equipmentFile = ResourcePath.EQUIPMENT.getFile("ToawEquipment" + AresFileType.EQUIPMENT.getFileExtension());
            EquipmentDB equipmentDB = FileIO.unmarshallJson(equipmentFile, EquipmentDB.class);
            for (int i = 0; i < numScenarios; i++) {
                String scenarioName = selectedScenarioListModel.get(i);
                Path filePath = FileSystems.getDefault().getPath(folder, scenarioName);
                Scenario scenario = FileIO.loadScenario(filePath.toFile(), equipmentDB);
                Board board = scenario.getBoard();
                int area = board.getWidth() * board.getHeight();
                scenarioSize[i] = area;
                totalScenarioSize += area;
            }
            for (int i = 0; i < numScenarios; i++) {
                String scenarioName = selectedScenarioListModel.get(i);
                Path filePath = FileSystems.getDefault().getPath(folder, scenarioName);
                Scenario scenario = FileIO.loadScenario(filePath.toFile(), equipmentDB);
                int numScenarioProblems = numProblems * scenarioSize[i] / totalScenarioSize;
                System.out.println(String.format("Scenario %s , area = %d, numProblems = %d", scenario.getName(), scenarioSize[i], numScenarioProblems));
                problems.put(scenarioName, generateProblems(scenario, numScenarioProblems, filePath));
            }
            problemGeneratorInteractor.setProblems(problems);
        }
    }

    private List<PathfindingProblem> generateProblems(Scenario scenario, int numProblems, Path scenarioFilePath) {
        List<PathfindingProblem> problems = new ArrayList<>();
        int remaining = numProblems;
        while (remaining > 0) {
            Point origin = getRandomPlayableLocation(scenario);
            Point destination = getRandomPlayableLocation(scenario);
            int distance = DistanceCalculator.DELTA.getCost(origin, destination);
            Board board = scenario.getBoard();
            PathfindingProblem problem = new PathfindingProblem(origin, destination, distance, scenario.getName(), scenarioFilePath, board.getWidth(), board.getHeight());
            problems.add(problem);
            remaining--;
        }
        return problems;
    }

    private Point getRandomPlayableLocation(Scenario scenario) {
        RandomGenerator rg = RandomGenerator.getInstance();
        int width = scenario.getBoard().getWidth();
        int height = scenario.getBoard().getHeight();
        Tile location;
        do {
            location = scenario.getBoard().getTile(rg.nextInt(width), rg.nextInt(height));
        } while (!location.isPlayable());
        return location.getCoordinates();
    }
}
