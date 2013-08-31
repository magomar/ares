package ares.application.analyser.benchmark;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public class PathfindingSolution {
    /**
     * Number of nodes in the solution path
     */
    private int pathLength;
    /**
     * Cost of the solution (G-cost or F-cost value of the last node in the solution path)
     */
    private double pathCost;
    /**
     * Deviation of the solution cost from the optimal solution cost (@link #pathCost} - optimal cost)
     */
    private double solutionError;
    /**
     * Computing time employed to obtain this solution
     */
    private long computingTime;
    /**
     * Number of nodes visited during the pathfinding process
     */
    private int nodesVisited;
    /**
     * Penetration is defined as the reason between the length of the solution and the number of nodes visited
     * That is: {@link #pathLength} / {@link #nodesVisited}
     */
    private double penetration;

    public PathfindingSolution(int pathLength, double pathCost, int nodesVisited, long computingTime) {
        this.pathLength = pathLength;
        this.pathCost = pathCost;
        this.nodesVisited = nodesVisited;
        this.computingTime = computingTime;
        penetration = (double) pathLength / nodesVisited;
    }

    public int getPathLength() {
        return pathLength;
    }

    public double getPathCost() {
        return pathCost;
    }

    public double getSolutionError() {
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

    @Override
    public String toString() {
        return "PathfindingSolution{" +
                "pathLength=" + pathLength +
                ", pathCost=" + pathCost +
                ", solutionError=" + solutionError +
                ", computingTime=" + computingTime +
                ", nodesVisited=" + nodesVisited +
                ", penetration=" + penetration +
                '}';
    }
}
