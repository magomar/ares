package ares.application.shared.boundaries.viewers.layerviewers;

import ares.platform.engine.algorithms.pathfinding.Node;
import java.util.Collection;

/**
 *
 * @author Saúl Esteban <saesmar1@ei.upv.es>
 */
public interface SearchCostsLayerViewer {
    
    public static final String NAME = "SEARCH_COSTS_LAYER";

    void updateSearchCosts(Collection<Node> openSet, Collection<Node> closedSet);
}
