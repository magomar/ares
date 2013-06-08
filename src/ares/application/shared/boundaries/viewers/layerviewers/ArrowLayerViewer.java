package ares.application.shared.boundaries.viewers.layerviewers;

import ares.platform.engine.algorithms.pathfinding.Path;
import java.util.Collection;

/**
 *
 * @author Mario Gómez Martínez <magomar@gmail.com>
 */
public interface ArrowLayerViewer extends ImageLayerViewer {

    void updateCurrentOrders(Path path);

    void updateLastOrders(Path path);

    void updatePlannedOrders(Path selectedUnitPath, Collection<Path> formationPaths, Collection<Path> forcePaths);
}
