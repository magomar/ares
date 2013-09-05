package ares.application.analyser.boundaries.viewers;

import ares.application.shared.boundaries.viewers.layerviewers.ImageLayerViewer;
import ares.platform.engine.algorithms.pathfinding.Node;

import java.util.Collection;

/**
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathfindingLayerViewer extends ImageLayerViewer {

    static final String NAME = "PATH_SEARCH_LAYER";

    static final int SHOW_G_COST = 0;
    static final int SHOW_H_COST = 1;
    static final int SHOW_F_COST = 2;
    
    public enum ShowCostType {
        SHOW_G_COST(0),
        SHOW_H_COST(1),
        SHOW_F_COST(2);
        private final int value;
        
        private ShowCostType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**
     * Shows the result of a path search process visually in terms of the costs associated to open and closed nodes
     * explored during the search process
     *
     * @param openSet
     * @param closedSet
     * @param costToShow     indicates the kind of cost to show, it could take the following values:
     * {@link #SHOW_F_COST},{@link #SHOW_G_COST}, {@link #SHOW_H_COST}
     */
    void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet, int costToShow);
}
