package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.platform.engine.algorithms.pathfinding.Node;

import java.util.Collection;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfindingLayerViewer extends ImageLayerViewer {

    static final String NAME = "PATH_SEARCH_LAYER";

    /**
     * Shows the result of the path search process visually, in terms of the costs associated to the open and closed nodes
     * visited during the search process
     *
     * @param openSet    the set of open nodes
     * @param closedSet  the the set of closed nodes
     * @param costToShow indicates the kind of cost to show  (G, H or F)
     */
    void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet, ShowCostType costToShow);

    public enum ShowCostType {
        SHOW_G_COST,
        SHOW_H_COST,
        SHOW_F_COST;
    }
}
