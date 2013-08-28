package ares.application.analyser.boundaries.interactors;

import ares.application.analyser.benchmark.PathfindingProblem;
import ares.application.analyser.boundaries.viewers.ProblemGeneratorViewer;

import java.util.List;
import java.util.Map;

/**
 * Author: Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ProblemGeneratorInteractor {
    ProblemGeneratorViewer getProblemGeneratorView();

    void setProblems(Map<String, List<PathfindingProblem>> problems);
}
