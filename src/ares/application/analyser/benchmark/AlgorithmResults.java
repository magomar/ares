package ares.application.analyser.benchmark;

import ares.platform.util.Statistics;
import ares.platform.util.SummaryStatistics;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class AlgorithmResults {
    private final String algorithm;
    private int numProblems;
    private int numSolutions;
    private SummaryStatistics pathLength;
    private SummaryStatistics pathCost;
    private SummaryStatistics computingTime;
    private SummaryStatistics nodesVisited;
    private List<PathfindingSolution> solutions;


    public AlgorithmResults(String algorithm) {
        this.algorithm = algorithm;
        solutions = new ArrayList<>();
    }

    public void addSolutions(List<PathfindingSolution> solutions, int numProblems) {
        this.solutions.addAll(solutions);
        numSolutions += solutions.size();
        this.numProblems += numProblems;
    }

    public void computeStatistics() {
        double[] pathLengthData = new double[solutions.size()];
        double[] pathCostData = new double[solutions.size()];
        double[] computingTimeData = new double[solutions.size()];
        double[] nodesVisitedData = new double[solutions.size()];
        for (int i = 0; i < solutions.size(); i++) {
            PathfindingSolution solution = solutions.get(i);
            pathLengthData[i] = solution.getPathLength();
            pathCostData[i] = solution.getPathCost();
            computingTimeData[i] = solution.getComputingTime();
            nodesVisitedData[i] = solution.getNodesVisited();
        }
        this.pathLength = Statistics.computeSummaryStatistics(pathLengthData);
        this.pathCost = Statistics.computeSummaryStatistics(pathCostData);
        this.computingTime = Statistics.computeSummaryStatistics(computingTimeData);
        this.nodesVisited = Statistics.computeSummaryStatistics(nodesVisitedData);
    }

    @Override
    public String toString() {
        return "*** Results for " + algorithm + ":\n" +
                "   numProblems=" + numProblems +  '\n' +
                "   numSolutions=" + numSolutions + '\n' +
                "   pathLength=" + pathLength + '\n' +
                "   pathCost=" + pathCost +  '\n' +
                "   computingTime=" + computingTime + '\n' +
                "   nodesVisited=" + nodesVisited + '\n';
    }
}
