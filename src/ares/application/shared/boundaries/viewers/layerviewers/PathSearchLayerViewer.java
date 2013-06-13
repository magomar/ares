package ares.application.shared.boundaries.viewers.layerviewers;

import ares.platform.engine.algorithms.pathfinding.Node;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathSearchLayerViewer extends ImageLayerViewer {

    public static final String NAME = "PATH_SEARCH_LAYER";

    void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet);
}
