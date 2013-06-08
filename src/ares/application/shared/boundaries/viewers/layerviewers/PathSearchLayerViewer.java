package ares.application.shared.boundaries.viewers.layerviewers;

import ares.platform.engine.algorithms.pathfinding.Node;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface PathSearchLayerViewer extends ImageLayerViewer {

    void updatePathSearch(Collection<Node> openSet, Collection<Node> closedSet);
}
