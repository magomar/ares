package ares.application.analyser.benchmark;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfinderSolution {
    /**
     * Number of nodes in the solution path
     */
    private int solutionLenght;
    /**
     * Cost of the solution (G-cost or F-cost value of the last node in the solution path)
     */
    private int solutionCost;
    /**
     * Deviation of the solution cost from the optimal solution cost (@link #solutionCost} - optimal cost)
     */
    private int solutionError;
    /**
     * Computing time employed to obtain this solution
     */
    private long computingTime;
    /**
     * Number of nodes visited during the pathfinding process
     */
    private int nodesVisited;
    /**
     * Penetration is defined as the reason between the lenght of the solution and the number of nodes visited
     * That is: {@link #solutionLenght} / {@link #nodesVisited}
     */
    private double penetration;

    public int getSolutionLenght() {
        return solutionLenght;
    }

    public int getSolutionCost() {
        return solutionCost;
    }

    public int getSolutionError() {
        return solutionError;
    }

    public long getComputingTime() {
        return computingTime;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    public double getPenetration() {
        return penetration;
    }
}
